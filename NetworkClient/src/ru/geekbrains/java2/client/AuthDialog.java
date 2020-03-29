package ru.geekbrains.java2.client;

import javax.swing.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class AuthDialog extends JFrame {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JPasswordField passwordField1;


    public AuthDialog() {
        connection();
        Thread thread = new Thread(() -> {
            try {
                timing();
            } catch (InterruptedException e) {
                System.out.println("Попытка авторизации");
            }
        });
        thread.setDaemon(true);
        thread.start();

        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                thread.interrupt();
            }

            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        pack();
        setVisible(true);
    }

    private void timing() throws InterruptedException {
        Thread.sleep(120000);
        System.out.println("попытки авторизации не было, закрываемся");
        onCancel();
    }

    private void onOK() {
        String login = textField1.getText().trim();
        System.out.println(login);
        String pass = new String(passwordField1.getPassword()).trim();
        System.out.println(pass);
        try {
            if (!login.isEmpty() && !pass.isEmpty()) {
                String tt = String.format("/auth %s %s", login, pass);
                out.writeUTF(tt);
                String text = in.readUTF();
                System.out.println(text);
                if (text.startsWith("/auth")) {
                    String name = text.split("\\s+", 2)[1];
                    new Client(socket, in, out, name);
                    dispose();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Неверные логин/пароль");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Ошибка при попытки аутентификации");
        }
    }

    private void onCancel() {
        dispose();
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Ошибка закрытия соединения");
            e.printStackTrace();
        }
    }

    private void connection() {
        try {
            socket = new Socket("localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
