package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    List<Optional<Ticket>> findAll();

    Optional<Ticket> add(Ticket ticket);

    Optional<Ticket> findById(int id);
}
