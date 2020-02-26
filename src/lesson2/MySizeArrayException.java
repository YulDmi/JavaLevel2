package lesson2;

public class MySizeArrayException extends IllegalArgumentException {

    public void log() {
        System.out.println("Размер массива не верно задан");
    }
}

