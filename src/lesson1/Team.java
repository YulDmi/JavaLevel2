package lesson1;

public class Team {

    Activity[] activity;
    String name;


    public Team(String name, Activity... activities) {
        this.activity = activities;
        this.name = name;

    }

    public void go(Course[] course) {

        for (Activity t : activity) {
            boolean win = true;
            for (Course value : course) {
                if (value.isDoIt(t))
                    System.out.printf("Участник команды %s - %s преодолел препядствие %s %n", name, t.toString(), value.toString());
                else {
                    System.out.printf("участник %s не смог преодолеть препядствие %s и выбыл %n", t.toString(), value.toString());
                    win = false;
                    break;
                }

            }
            if (win) {
                System.out.printf("ПОЗДРАВЛЯЕМ участника команды %s. ОН преодолел ВСЕ ПРЕПЯДСТВИЯ! %n", t.toString());
            }
        }
    }
}




