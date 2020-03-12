package lesson5;

import java.util.Arrays;

public class MyClass {

    static final int size = 10000000;
    static final int h = size / 2;

    public static void main(String[] args) {
        float[] arr = initial();
        timeCheck1(arr);
        timeCheck2(arr);
        timeCheck3(arr);

    }

    public static float[] initial() {
        float[] arr = new float[size];
        Arrays.fill(arr, 1f);
        return arr;
    }

    public static void timeCheck1(float[] arr) {

        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println(System.currentTimeMillis() - a);
        //  System.out.println(Arrays.toString(arr));
    }

    public static void timeCheck2(float[] arr) {

        long a = System.currentTimeMillis();
        float[] a1 = new float[h];
        float[] a2 = new float[h];

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a1.length; i++) {
                    a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a2.length; i++) {
                    a2[i] = (float) (a2[i] * Math.sin(0.2f + (i + h) / 5) * Math.cos(0.2f + (i + h) / 5) * Math.cos(0.4f + (i + h) / 2));

                }
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        System.out.println(System.currentTimeMillis() - a);
        // System.out.println(Arrays.toString(arr));
    }

    public static void timeCheck3(float[] arr) {

        long a = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < h; i++) {
                    arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        thread.start();
        for (int i = h; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + (i) / 5) * Math.cos(0.2f + (i) / 5) * Math.cos(0.4f + (i) / 2));
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - a);
        // System.out.println(Arrays.toString(arr));
    }
}

