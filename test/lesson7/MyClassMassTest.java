package lesson7;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MyClassMassTest {
    private int[] a;
    private int[] result1;
    private boolean result2;
    private MyClass mc;

    public MyClassMassTest(int[] a, int[] result1, boolean result2) {
        this.a = a;
        this.result1 = result1;
        this.result2 = result2;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        return Arrays.asList(new Object[][]{
                {new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}, new int[]{1, 7}, false},
                {new int[]{1, 4, 4, 4}, new int[]{}, true},
                {new int[]{4, 4, 4, 4}, new int[]{}, false}

        });
    }

    @Before
    public void init() {
        mc = new MyClass();
    }

    @Test(expected = RuntimeException.class)
    public void changeMyClass() {
        mc.change(new int[]{1, 2, 3, 5});
    }

    @Test
    public void checkMyClass() {
        Assert.assertFalse(mc.check(new int[]{1, 1, 1, 1}));
    }

    @Test
    public void change() {
        Assert.assertArrayEquals(result1, mc.change(a));
    }

    @Test
    public void check() {
        Assert.assertEquals(result2, mc.check(a));
    }
}
