package ru.job4j.cinema.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcSessionRepository implements SessionRepository {

    private final static String FIND_ALL_SESSIONS = "SELECT * FROM sessions ORDER BY id";

    private final static String ADD_SESSION = "INSERT INTO sessions(name) VALUES (?)";

    private final static String UPDATE_SESSION = "UPDATE sessions SET name = ? WHERE id = ?";

    private final static String FIND_BY_ID_SESSION = "SELECT * FROM sessions WHERE id = ?";

    private static final Logger LOG_SESSION = LoggerFactory.getLogger(JdbcSessionRepository.class.getName());

    private final DataSource dataSource;

    public JdbcSessionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (Connection cn = dataSource.getConnection();
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
        try (Connection cn = dataSource.getConnection();
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
        try (Connection cn = dataSource.getConnection();
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
        try (Connection cn = dataSource.getConnection();
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

    private Session addSession(ResultSet resultset) throws SQLException {
        return new Session(
                resultset.getInt("id"),
                resultset.getString("name"));
    }
}
