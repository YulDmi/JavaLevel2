package ru.geekbrains.java2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class BaseAuthService implements AuthService {
    private static final Logger LOGGER = LogManager.getLogger(BaseAuthService.class);
    private static MySQLConnection msql;
    public BaseAuthService() {
    msql = new MySQLConnection();
    }

    @Override
    public void start() {
       LOGGER.info("Сервис аутентификации запущен");

    }

    @Override
    public int getIDByLoginPass(String login, String pass) {

     int id = 0;
        try {
             id = msql.getID(login, pass);
        } catch (SQLException e) {
           LOGGER.error(e.getMessage());
        }
        return id;
    }

    @Override
    public void stop() {
        LOGGER.info("Сервис аутентификации остановлен");
        msql.close();
    }

    @Override
    public void changeNick(String nick, int ID) {
        try {
            msql.changeNick(nick, ID);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public String getNickById(int id) {
       String name = null;
        try {
           name =  msql.getNick(id);
        } catch (SQLException e) {
           LOGGER.error(e.getMessage());
        }
        return name;
    }

}
