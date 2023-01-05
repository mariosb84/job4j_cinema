package ru.job4j.cinema.utilites;

import java.util.HashSet;

public final class Any {

    private Any() {

    }

    public static boolean any(String uri) {
        HashSet<String> set = new HashSet<>();
        set.add("loginPage");
        set.add("login");
        set.add("formAddUser");
        set.add("addUser");
        set.add("registration");
        set.add("cinemaList");
        return set.stream().anyMatch(uri::endsWith);
    }

}
