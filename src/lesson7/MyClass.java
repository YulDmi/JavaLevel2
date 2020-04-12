package lesson7;

import java.util.Arrays;

public class MyClass {
    private static final int COUNT = 4;

    public  int[] change(int[] arr) {
        int[] copyArr = new int[0];
        if (arr[arr.length-1] == COUNT) return copyArr;

        for (int i = arr.length - 2; i >= 0; i--) {
            if (arr[i] == COUNT) {
                copyArr = Arrays.copyOfRange(arr, i + 1, arr.length);
                break;
            }
        }
        if (copyArr.length == 0) {
            throw new RuntimeException();
        }
        return copyArr;
    }
    public  boolean check (int[] arr) {
        boolean check1 = false;
        boolean check4 = false;
        for (int i : arr) {
            if (i == 1) {
                check1 = true;
            }else if (i == 4) {
                check4 = true;
            }else return false;
        }
        return check1 && check4;
    }

    public static void main(String[] args) {
        int[] a = {4, 1, 4, 3, 2};
        MyClass myClass = new MyClass();
        System.out.println(myClass.check(a));
    }
}
