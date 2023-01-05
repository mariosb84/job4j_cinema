package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SessionDbStore {

    private final static String FIND_ALL_SESSIONS = "SELECT * FROM sessions ORDER BY id";

    private final static String ADD_SESSION = "INSERT INTO sessions(name) VALUES (?)";

    private final static String UPDATE_SESSION = "UPDATE sessions SET name = ? WHERE id = ?";

    private final static String FIND_BY_ID_SESSION = "SELECT * FROM sessions WHERE id = ?";

    private final static String DELETE_SESSION = "DELETE FROM sessions WHERE id = ?";

    private static final Logger LOG_SESSION = LoggerFactory.getLogger(SessionDbStore.class.getName());

    private final BasicDataSource pool;

    public SessionDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_ALL_SESSIONS)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    sessions.add(addSession(it));
                }
            }
        } catch (Exception e) {
            LOG_SESSION.error("Exception in  findAll() method", e);
        }
        return sessions;
    }


    public Session add(Session session) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(ADD_SESSION,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, session.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    session.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG_SESSION.error("Exception in  add() method", e);
        }
        return session;
    }

    public void update(Session session) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(UPDATE_SESSION)) {
            ps.setString(1, session.getName());
            ps.setInt(2, session.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG_SESSION.error("Exception in  update() method", e);
        }
    }

    public Session findById(int id) {
        Session result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_BY_ID_SESSION)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = addSession(it);
                }
            }
        } catch (Exception e) {
            LOG_SESSION.error("Exception in  findById() method", e);
        }
        return result;
    }

    public void delete(Session session) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(DELETE_SESSION)) {
            ps.setInt(1, session.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG_SESSION.error("Exception in  delete() method", e);
        }
    }

    private Session addSession(ResultSet resultset) throws SQLException {
        return new Session(
                resultset.getInt("id"),
                resultset.getString("name"));
    }
}
