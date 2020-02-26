package lesson2;

import java.util.Random;

public class MyTest {


    public static void main(String[] args) {
        String[][] strings = initial();
        strings[2][3] = "five";

        try {
            int arraySumma = summa(strings);
            System.out.println("Сумма чисел массива = " + arraySumma);
        } catch (MyArrayDataException e) {
            System.out.println(e.getMessage());
        } catch (MySizeArrayException e) {
            e.log();
        }
    }

    public static String[][] initial() {
        int len = 4;
        String[][] str = new String[len][len];
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                str[i][j] = Integer.toString(random.nextInt(100));
            }
        }
        return str;
    }

    public static int summa(String[][] str) throws MyArrayDataException, MySizeArrayException {

        int myCorrectSize = 4;
        int mySumma = 0;
        if (str == null || str.length != myCorrectSize) {
            throw new MySizeArrayException();
        }

            for (int i = 0; i < str.length; i++) {
                if (str[i].length != myCorrectSize) {
                    throw new MySizeArrayException();
                }
                for (int j = 0; j < str[i].length; j++) {
                    try {
                        int n = Integer.parseInt(str[i][j]);
                        mySumma += n;
                    } catch (NumberFormatException e) {
                        throw new MyArrayDataException(String.format("В ячейке [%d][%d] находится не числовое значение %n", i, j));
                    }
                }
            }

        return mySumma;
    }
}
