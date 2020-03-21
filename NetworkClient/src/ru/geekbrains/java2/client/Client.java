package ru.geekbrains.java2.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame {
    private Socket socket;
    private DataInputStream reader;
    private DataOutputStream writer;

    private JTextField field;
    private JTextArea chatArea;

    public Client(Socket socket, DataInputStream in, DataOutputStream out) {
        this.socket = socket;
        this.reader = in;
        this.writer = out;
        showWindowGUI();
        readMessage();
    }

    private void showWindowGUI() {
        setBounds(400, 400, 400, 400);
        setTitle("Клиент");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        JButton button = new JButton("Отправить");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        field = new JTextField();
        field.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        panel.add(field, BorderLayout.CENTER);
        panel.add(button, BorderLayout.EAST);
        add(panel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    writer.writeUTF("/end");
                    closeConnection();
                } catch (IOException ex) {
                    System.out.println("Ошибка закрытия");
                }
            }

        });
        setVisible(true);
    }

    private void closeConnection() {
        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Ошибка закрытия соединения");
        }
    }

    private void sendMessage() {
        String text = field.getText();
        if (!text.trim().isEmpty()) {
            try {
                writer.writeUTF(text);
                if (text.equalsIgnoreCase("/end")) {
                    JOptionPane.showMessageDialog(null, "Вы разорвали соединение с сервером.");
                    closeConnection();
                }
                chatArea.append(String.format("Я: %s %n", text));
                field.setText("");
                field.grabFocus();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Ошибка отправки сообщения. Нет соединения с сервером.");
            }
        }
    }

    public void readMessage() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String serverText = reader.readUTF();
                        chatArea.append(serverText);
                        if (serverText.equalsIgnoreCase("/end")) {
                            JOptionPane.showMessageDialog(null, "Соединение с сервером разорвано");
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error");
                } finally {
                    closeConnection();
                }
            }
        }).start();
    }
}
