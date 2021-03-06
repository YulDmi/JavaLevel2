package client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.geekbrains.java2.MyServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler {
    private MyServer myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;
    private int ID;
    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);


    public ClientHandler(MyServer serverSocket, Socket client) {
        try {
            this.myServer = serverSocket;
            this.socket = client;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            this.ID = 0;
            ExecutorService service = Executors.newFixedThreadPool(1);
            service.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        authentication();
                        readMessage();
                    } catch (IOException e) {
                        LOGGER.error("Ошибка аутентификации или соединения");
                    } finally {
                        closeConnection();
                    }
                }
            });
            service.shutdown();
        } catch (IOException e) {
           LOGGER.error(e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    private void authentication() throws IOException {
        while (true) {
            String text = in.readUTF();
            if (text.startsWith("/auth")) {
                String[] dataClient = text.split(" ");

                int id = myServer.getAuthService().getIDByLoginPass(dataClient[1], dataClient[2]);

                if (id != 0) {
                    if (!myServer.isBusyID(id)) {
                        getAuthentication(id);
                        return;
                    } else sendMessage("Клиент с таким ником уже есть в чате.");
                } else sendMessage("Неверный логин/пароль");
            }
        }
    }

    private void getAuthentication(int id) {
        ID = id;
        name = myServer.getAuthService().getNickById(ID);
        sendMessage("/auth " + name);
        sendMessage(myServer.getMh().getStringMassage());
        myServer.broadcastMsg("К чату присоединился " + name, name);
        myServer.subscribe(this);
    }

    private void readMessage() throws IOException {
        while (true) {
            String message = in.readUTF();
            String text = String.format("От %s : %s", name, message);
            LOGGER.info(text);
            if (message.equalsIgnoreCase("/end")) {
                myServer.unsubscribe(this);
                return;
            } else if (message.startsWith("/w ")) {
                privateMessage(message);
            } else if (message.startsWith("/cn ")) {
                changeField(message);
            } else {
                myServer.getMh().writeMassage(text);
                myServer.broadcastMsg(message, name);
            }
        }
    }

    private void changeField(String message) {

        String[] fields = message.split("\\s+", 2);

        if (!fields[1].isEmpty()) {
            myServer.getAuthService().changeNick(fields[1], ID);
            privateMessage(String.format("/w %s Вы сменили свой никНайм на %s%n." +
                    "Обновленные данные будут отображаться со следующего сеанса.%n", name, fields[1]));
        }
    }

    private void privateMessage(String massage) {
        String[] words = massage.split("\\s+", 3);
        String nameNick = words[1];
        ClientHandler privateClientHandler = myServer.privateClientHandler(nameNick);
        if (privateClientHandler != null) {
            privateClientHandler.sendMessage(String.format("Приватное сообщение от %s : %s%n", name, words[2]));
        } else this.sendMessage(String.format("Клиент %s  в данный момент не в чате. %n", nameNick));
    }

    public void sendMessage(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
           LOGGER.error(e.getMessage());
        }
    }

    private void closeConnection() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
           LOGGER.error("Ошибка закрытия соединения");

        }
    }
}
