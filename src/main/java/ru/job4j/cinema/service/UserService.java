package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.Optional;

@ThreadSafe
@Service
public class UserService {

    private final UserRepository store;

    public UserService(UserRepository store) {
        this.store = store;
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findUserByNameEmailPhone(String username, String email, String phone) {
        return store.findUserByNameEmailPhone(username, email, phone);
    }

}
