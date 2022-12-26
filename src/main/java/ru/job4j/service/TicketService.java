package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.Ticket;
import ru.job4j.store.TicketDbStore;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class TicketService {

        private final TicketDbStore store;

        public TicketService(TicketDbStore store) {
            this.store = store;
        }


        public Optional<Ticket> add(Ticket ticket) {
        return store.add(ticket);
    }

        public Optional<Ticket> findById(int id) {
        return store.findById(id);
    }

        public List<Optional<Ticket>> findAll() {
        return store.findAll();
    }

}
