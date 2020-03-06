package lesson3;

import java.util.HashMap;
import java.util.Map;

public class PhoneBook {

    Map<String, String> phoneBooks = new HashMap<>();

    public void add(String name, String phone) {
        if (name != null && phone != null) {
            if (phoneBooks.containsKey(phone)) {
                System.out.printf("Такой номер телефона %s уже есть в справочнике. %n", phone);
            } else {
                phoneBooks.put(phone, name);
            }
        }else System.out.println("Данные не корректны.");
    }

    public void get(String name) {

        if (phoneBooks.isEmpty() || !phoneBooks.containsValue(name)) {
            System.out.printf("Человека с фамилией - %S нет в справочнике.%n", name);
        } else {
            for (Map.Entry<String, String> entry : phoneBooks.entrySet()) {
                if (entry.getValue().equals(name)) {
                    System.out.printf("%s - %s %n", name, entry.getKey());
                }
            }
        }
    }
}
