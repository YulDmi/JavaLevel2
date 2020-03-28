package ru.geekbrains.java2;

import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {
    private List<Entry> entries;

    public BaseAuthService() {
        this.entries = new ArrayList<>();
        entries.add(new Entry("login1", "password1", "nick1"));
        entries.add(new Entry("login2", "password2", "nick2"));
        entries.add(new Entry("login3", "password3", "nick3"));
    }

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");

    }

    @Override
    public String getNickByLoginPass(String login, String pass) {
        for (Entry en : entries) {
            if (en.login.equals(login) && en.password.equals(pass)) return en.nick;
        }
        return null;
    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен");
    }

    private class Entry{
        private String login;
        private String password;
        private String nick;

        public Entry(String login, String password, String nick) {
            this.login = login;
            this.password = password;
            this.nick = nick;
        }
    }
}
