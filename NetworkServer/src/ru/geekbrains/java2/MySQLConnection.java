package ru.geekbrains.java2;

import java.sql.*;

public class MySQLConnection {

    private static Connection conn;
    private PreparedStatement ps;
    private static ResultSet rs;

    public MySQLConnection() {
        createConnection();
//        createTable();
//        writeTable();
    }

    private void createConnection() {
        try {
            conn = null;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\ASUS\\Desktop\\SQLite\\MYBD.s3db");
            System.out.println("Base connection");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
            System.out.println("Ошибка закрытия соединения с базой");
            e.printStackTrace();
        }
    }

    public  void changeNick(String nick, int id) throws SQLException {
        ps = conn.prepareStatement("UPDATE users SET name = ? WHERE id = ? ");
        ps.setString(1, nick);
        ps.setInt(2, id);
        ps.execute();
    }


//    private void createTable() {
//        try {
//            statement = conn.createStatement();
//            statement.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'login' text, `pass` text);");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void writeTable() throws SQLException {
//
//        statement.execute("INSERT INTO 'users' ('name', 'login', 'pass') VALUES ('nick1',  'login1', 'password1'); ");
//        statement.execute("INSERT INTO 'users' ('name', 'login', 'pass') VALUES ('nick2',  'login2', 'password2'); ");
//        statement.execute("INSERT INTO 'users' ('name', 'login', 'pass') VALUES ('nick3',  'login3', 'password3'); ");
//
//    }


}

