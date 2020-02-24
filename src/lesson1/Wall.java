package lesson1;

public class Wall implements Course {
public final int hight;

    public int getHight() {
        return hight;
    }

    public Wall(int hight) {
        this.hight = hight;
    }

    @Override
    public boolean isDoIt(Activity activity) {
        return activity.jumpable() >= hight;
    }

    @Override
    public String toString() {
        return "Стена {" +
                 hight +
                '}';
    }
}
