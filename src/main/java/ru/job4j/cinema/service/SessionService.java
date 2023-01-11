package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Session;

import java.util.Collection;

public interface SessionService {

    Collection<Session> findAll();

    void add(Session session);

    Session findById(int id);

    void update(Session session);

}
