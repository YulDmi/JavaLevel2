package lesson8;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StartTest {

    static TreeMap<Integer, List<Method>> map = new TreeMap<>();

    public static void main(String[] args) {
        Class clazz = CalculatorTest.class;
        start(clazz);
    }
    public static void start(Class testClass) {
        Object ob;
        try {
            ob = testClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return;
        }
        Method[] methods = testClass.getDeclaredMethods();
        Method last = null;
        boolean beforeSuiteCount = false;
        boolean afterSuiteCount = false;

        for (Method o : methods) {
            if (o.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeSuiteCount) {
                    throw new RuntimeException();
                }
                beforeSuiteCount = true;
                try {
                    o.invoke(ob);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else if (o.isAnnotationPresent(Test.class)) {
                Integer testann = o.getAnnotation(Test.class).value();
                map.putIfAbsent(testann, new ArrayList<>());
                map.get(testann).add(o);
            } else if (o.isAnnotationPresent(AfterSuite.class)) {
                if(afterSuiteCount) throw new RuntimeException();
                afterSuiteCount =true;
                last = o;
            }
        }
        for (Map.Entry<Integer, List<Method>> pair : map.entrySet()) {
            for (Method method : pair.getValue()) {
                try {
                    System.out.println(method.getName());
                    method.invoke(ob);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        if (last != null) {
            try {
                last.invoke(ob);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}

