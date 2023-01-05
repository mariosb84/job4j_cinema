package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketDbStore {

    private final static String FIND_ALL = "SELECT * FROM ticket ORDER BY id";

    private final static String ADD = "INSERT INTO ticket(session_id, pos_row, cell, user_id) VALUES (?, ?, ?, ?)";

    private final static String FIND_BY_ID = "SELECT * FROM ticket WHERE id = ?";

    private static final Logger LOG = LoggerFactory.getLogger(TicketDbStore.class.getName());

    private final BasicDataSource pool;

    public TicketDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Optional<Ticket>> findAll() {
        List<Optional<Ticket>> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(Optional.of(addTicket(it)));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in  findAll() method", e);
        }
        return tickets;
    }


    public Optional<Ticket> add(Ticket ticket) {
        Optional<Ticket> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(ADD,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getSessionId());
            ps.setInt(2, ticket.getPosRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUserId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                }
            }
            result = Optional.of(ticket);
        } catch (SQLException se) {
            do {
                System.out.println("SQL STATE: " + se.getSQLState());
                System.out.println("ERROR CODE: " + se.getErrorCode());
                System.out.println("MESSAGE: " + se.getMessage());
                System.out.println();
                se = se.getNextException();
                LOG.error("Exception in  add() method", se);
            }
            while (se != null);
    } catch (Exception e) {
            LOG.error("Exception in  add() method", e);
        }
        return result;
    }



    public Optional<Ticket> findById(int id) {
        Optional<Ticket> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = Optional.of(addTicket(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in  findById() method", e);
        }
        return result;
    }

    private Ticket addTicket(ResultSet resultset) throws SQLException {
        return new Ticket(
                resultset.getInt("id"),
                resultset.getInt("session_id"),
                resultset.getInt("pos_row"),
                resultset.getInt("cell"),
                resultset.getInt("user_id"));
             }

}
