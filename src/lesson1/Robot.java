package lesson1;

public class Robot implements Activity{
    private int higth;
    private int length;
   private String name;

    public Robot(int higth, int length, String name) {
        this.higth = higth;
        this.length = length;
        this.name = name;
    }

    public int getHigth() {
        return higth;
    }

    public void setHigth(int higth) {
        this.higth = higth;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public int runable() {
        System.out.printf("%s начинает бежать %n", name);
        return length;
    }

    @Override
    public int jumpable() {
        System.out.printf("%s прыгает %n", name);
        return higth;
    }

    @Override
    public String toString() {
        return "Robot " + name;
    }
}
