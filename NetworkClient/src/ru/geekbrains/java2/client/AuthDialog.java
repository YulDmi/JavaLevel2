package ru.geekbrains.java2.client;

import javax.swing.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class AuthDialog extends JDialog {
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
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        pack();
        setVisible(true);
    }

    private void onOK() {
        String login = textField1.getText().trim();
        System.out.println(login);
        String pass = new String(passwordField1.getPassword()).trim();
        System.out.println(pass);
        try {
            String tt = String.format("/auth %s %s", login, pass);
            out.writeUTF(tt);
            String text = in.readUTF();
            System.out.println(text);
            if (text.startsWith("/auth")) {
                new Client(socket, in, out);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Ошибка при попытки аутентификации");
        }
        dispose();
    }

    private void onCancel() {
        System.exit(0);
       dispose();
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
