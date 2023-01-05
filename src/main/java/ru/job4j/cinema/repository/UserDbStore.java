package ru.job4j.cinema.repository;


import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserDbStore {

    private final static String ADD_USER = "INSERT INTO users(username, email, phone) VALUES (?, ?, ?)";

    private final static String FIND_BY_USERNAME_AND_EMAIL_AND_PHONE_USER  = "SELECT * FROM users WHERE username = ? AND email = ? AND phone = ?";

    private static final Logger LOG_USER = LoggerFactory.getLogger(SessionDbStore.class.getName());

    private final BasicDataSource pool;

    public UserDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(ADD_USER,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
            result = Optional.of(user);
        } catch (Exception e) {
            LOG_USER.error("Exception in  add() method", e);
        }
        return result;
    }

    public Optional<User> findUserByNameEmailPhone(String username, String email, String phone) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_BY_USERNAME_AND_EMAIL_AND_PHONE_USER)) {
            ps.setString(1, username);
                    ps.setString(2, email);
            ps.setString(3, phone);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = Optional.of(addUser(it));
                }
            }
        } catch (Exception e) {
            LOG_USER.error("Exception in  findUserByEmailAndPassword() method", e);
        }
        return result;
    }

    private User addUser(ResultSet resultset) throws SQLException {
        return new User(
                resultset.getInt("id"),
                resultset.getString("username"),
                resultset.getString("email"),
                resultset.getString("phone")
        );
    }

}
