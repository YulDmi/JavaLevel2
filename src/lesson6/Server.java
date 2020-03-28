package lesson6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    static DataInputStream br;
    static DataOutputStream bw;


    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(4004);
            System.out.println("Server started.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                readerTextClient();
            }
        }).start();

        sendMessage();
    }

    private static void readerTextClient() {
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Соединение с клиентом установлно.");
            br = new DataInputStream(clientSocket.getInputStream());
            bw = new DataOutputStream(clientSocket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        String text;
        try {
            while (!(text = br.readUTF()).equalsIgnoreCase("end")) {
                System.out.println("Client: " + text);
            }
                closeClientSocket();

        } catch (IOException e) {
            System.out.println("Ошибка соединения с клиентом");
        } finally {
            try {
                serverSocket.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sendMessage() {
        String serverText;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));) {
             do{
                 serverText = reader.readLine();
                if (clientSocket == null) {
                    System.out.println("Нет подключенных клиентов.");
                }else  bw.writeUTF(serverText);
            }while (!serverText.equalsIgnoreCase("end"));
            closeClientSocket();
        } catch (IOException e) {
            System.out.println("Ошибка отправки сообщения");
        }
    }

    private static void closeClientSocket() throws IOException {
            clientSocket.close();
            System.out.println("Соединение с клиентом завершено");
    }
}

