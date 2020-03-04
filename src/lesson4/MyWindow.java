package lesson4;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyWindow extends JFrame {
   private JTextArea textArea;
   private JTextField textField;
   private String name = "Неизвестный пользователь";
   static  String[] names = {"Pet", "Bob", "Tom", "Felipe", "Mike", "Pet", "Bob", "Tom", "Felipe", "Mike"};

    public MyWindow() {
        setTitle("Новое окно");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 400, 400);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        textField = new JTextField(25);
        textField.addActionListener(new MyActionListener());
        JButton button = new JButton("Отправить");
        button.addActionListener(new MyActionListener());
        add(button, BorderLayout.NORTH);
        panel.add(textField);
        panel.add(button);


        JList <String > list = new JList<>(names);
        JScrollPane scrollPane1 = new JScrollPane(list);
        scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        list.setVisibleRowCount(6);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Object element = list.getSelectedValue();
                name = element.toString();
            }
        });
        add(scrollPane1, BorderLayout.WEST);
        add(panel, BorderLayout.SOUTH);
        textField.requestFocus();
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        MyWindow myWindow = new MyWindow();
    }

    class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.append(name + ": \n");
            textArea.append(textField.getText() + "\n");
            textField.setText("");
            textField.requestFocus();
        }
    }
}
