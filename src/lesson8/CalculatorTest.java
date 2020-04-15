package lesson8;

public class CalculatorTest {
    private static Calculator cl;

    @BeforeSuite
    public void init() {
        cl = new Calculator();
        System.out.println("Start");
    }

    @Test (value = 2)
    public void testAdd() {
        if ((cl.add(2, 3)) != 5) System.out.println(false);
    }

    @Test
    public void testSub() {
        if ((cl.sub(5, 1)) != 4) System.out.println(false);
    }

    @Test (value = 8)
    public void testMul() {
        if ((cl.mul(2, 5)) != 10) System.out.println(false);
    }

    @Test
    public void testDiv() {
        if ((cl.div(6, 2)) != 3) System.out.println(false);
    }

    @AfterSuite
    public void stop() {
        //  cl = null;
        System.out.println("Finish");
    }
    @AfterSuite
    public void stop2() {
        //  cl = null;
        System.out.println("Finish2");
    }

}
