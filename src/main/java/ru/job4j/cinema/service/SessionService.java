package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.SessionRepository;

import java.util.Collection;

@ThreadSafe
@Service
public class SessionService {

        private final SessionRepository store;

        public SessionService(SessionRepository store) {
            this.store = store;
        }

        public Collection<Session> findAll() {
            return store.findAll();
        }

        public void add(Session session) {
            store.add(session);
        }

        public Session findById(int id) {
            return store.findById(id);
        }

        public void update(Session session) {
            store.update(session);
        }
}
