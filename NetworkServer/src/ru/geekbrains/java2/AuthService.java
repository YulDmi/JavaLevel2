package ru.geekbrains.java2;

public interface AuthService {
    void start();
    int getIDByLoginPass(String login, String pass);
    void stop();


    void changeNick(String nick, int ID);

    String getNickById(int id);
}
