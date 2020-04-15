package ru.geekbrains.java2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class MySQLConnection {

    private static Connection conn;
    private PreparedStatement ps;
    private static ResultSet rs;
    private static final Logger LOGGER = LogManager.getLogger(MySQLConnection.class);
    public MySQLConnection() {
        createConnection();
    }

    private void createConnection() {
        try {
            conn = null;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\ASUS\\Desktop\\SQLite\\MYBD.s3db");
            LOGGER.info("Base connection");
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public int getID(String login, String pass) throws SQLException {
       ps = conn.prepareStatement("SELECT * FROM users WHERE login = ? AND pass = ?");
        ps.setString(1, login);
        ps.setString(2, pass);
        rs = ps.executeQuery();
       int id = 0;
        while (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }

    public String getNick (int id) throws SQLException {
        ps = conn.prepareStatement("SELECT * FROM users WHERE id = ? ");
        ps.setInt(1, id);
        rs = ps.executeQuery();
        String name = null;
        while (rs.next()) {
            name = rs.getString("name");
        }
        return name;
    }

    public void close() {

        try {
            rs.close();
            conn.close();
        } catch (SQLException e) {
           LOGGER.error("Ошибка закрытия соединения с базой");

        }
    }

    public  void changeNick(String nick, int id) throws SQLException {
        ps = conn.prepareStatement("UPDATE users SET name = ? WHERE id = ? ");
        ps.setString(1, nick);
        ps.setInt(2, id);
        ps.execute();
    }

}

