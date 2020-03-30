package ru.geekbrains.java2;

import client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
    private static final int PORT = 8189;
    private final List<ClientHandler> clients = new ArrayList<>();
    private final AuthService authService = new BaseAuthService();


    public MyServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен, ожидаем соединения...");
            authService.start();
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Клиент подключен.");
                new ClientHandler(this, client);
            }
        } catch (IOException e) {
            System.out.println("Ошибка работы сервера");
            e.printStackTrace();
        } finally {
            authService.stop();
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public synchronized boolean isBusyID(int id) {
        for (ClientHandler o : clients) {
            if (o.getID() == id) return true;
        }
        return false;
    }

    public synchronized void broadcastMsg(String msg, String name) {
        for (ClientHandler o : clients) {
            if (!o.getName().equals(name)) {
                o.sendMessage(String.format("От %s : %s %n", name, msg));
            }
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        broadcastMsg("Клиент покинул беседу.", clientHandler.getName());
        System.out.println("Клиент " + clientHandler.getName() + " покинул беседу.");
        clients.remove(clientHandler);
    }

    public synchronized ClientHandler privateClientHandler(String name) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(name)) {
                return o;
            }
        } return null;
    }


}

