package lesson3;

public class TestPhoneBook {
    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Ivanov", "5555555");
        phoneBook.add("Ivanov", "5555555");
        phoneBook.add("Smirnov", "1111111");
        phoneBook.add("Smirnov", "555-55-55");
        phoneBook.add("Smirnov", "33333333");
        phoneBook.add("Ivanov", "5551555");

        phoneBook.get("Smirnov");
        phoneBook.get("Ivanov");
        phoneBook.get("Smu");

    }
}

