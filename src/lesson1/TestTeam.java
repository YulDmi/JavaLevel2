package lesson1;

public class TestTeam {
    public static void main(String[] args) {

      Team teams = initTeam();
       Course [] courses = initCourse();
       teams.go(courses);

        }

    private static Course[] initCourse() {
       Course[] courses = new Course[3];
        courses[0] = new Treadmill(100);
        courses[1] = new Wall(1);
        courses[2] = new Treadmill(150);
        return courses;
    }

    public static Team initTeam(){
            Activity activity1 = new Cat (2, 100, "Tom");
            Activity activity2 = new Person(1, 300, "Bob");
            Activity activity3 = new Robot(0, 1000, "QR200");
            Activity activity4 = new Person(2, 550, "Chack");
            return new Team("First", activity1, activity2, activity3,activity4);

        }
    }

