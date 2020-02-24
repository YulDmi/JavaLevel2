package lesson1;

public class Treadmill implements Course{
    public final int length;

    public int getLength() {
        return length;
    }


   public Treadmill(int length) {
       this.length = length;

    }

    @Override
    public boolean isDoIt(Activity activity) {

    return  activity.runable() >= length;
    }

    @Override
    public String toString() {
        return "Беговая дорожка{" +
                 + length +
                '}';
    }
}
