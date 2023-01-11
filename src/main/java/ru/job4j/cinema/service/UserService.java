package ru.job4j.cinema.service;

import ru.job4j.cinema.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> add(User user);

    Optional<User> findUserByNameEmailPhone(String username, String email, String phone);
}
