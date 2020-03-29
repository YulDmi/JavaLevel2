package ru.geekbrains.java2;

import java.sql.SQLException;

public class BaseAuthService implements AuthService {
   // private List<Entry> entries;
    private static MySQLConnection msql;
    public BaseAuthService() {
    msql = new MySQLConnection();
//        this.entries = new ArrayList<>();
//        entries.add(new Entry("login1", "password1", "nick1"));
//        entries.add(new Entry("login2", "password2", "nick2"));
//        entries.add(new Entry("login3", "password3", "nick3"));
    }

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");

    }

    @Override
    public int getIDByLoginPass(String login, String pass) {
//        for (Entry en : entries) {
//            if (en.login.equals(login) && en.password.equals(pass)) return en.nick;
//        }
     int id = 0;
        try {
             id = msql.getID(login, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен");
        msql.close();
    }

    //    private class Entry{
//        private String login;
//        private String password;
//        private String nick;
//
//        public Entry(String login, String password, String nick) {
//            this.login = login;
//            this.password = password;
//            this.nick = nick;
//        }
//    }
    @Override
    public void changeNick(String nick, int ID) {
        try {
            msql.changeNick(nick, ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getNickById(int id) {
       String name = null;
        try {
           name =  msql.getNick(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

}
