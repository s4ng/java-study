import java.util.*;

public class Application {

    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        Queue<Person> waitingLine = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            waitingLine.add(new Person(i + 1, makeRandomNumber()));
        }

        printWaitingLine(waitingLine);

        Runnable task = new Runnable() {

            private Person person;

            @Override
            public void run() {
                if(!waitingLine.isEmpty()) {
                    person = waitingLine.poll();
                } else {
                    if(person.getNowWaitingTime() == 0) {
                        person = null;
                    }
                    person.waitSecond();
                    System.out.print(personString(person));
                }
            }
        };

        Thread thread = new Thread(task);
        thread.start();
        for(int i = 0; i < 10; i++) {
            Thread.sleep(100);
            printTimer(i + 1);
        }
    }

    private static int makeRandomNumber() {

        Random random = new Random();

        int randomNum;
        while (true) {
            randomNum = random.nextInt(11);
            if (randomNum != 0) {
                break;
            }
        }

        return randomNum;
    }

    private static void printWaitingLine(Queue<Person> people) {

        System.out.print("최초 대기열 : ");

        StringBuilder waitingLineString = new StringBuilder();

        for (Person p : people) {
            waitingLineString.append(" " + personString(p) + " ");
        }

        System.out.println(waitingLineString);
    }

    private static void printTimer(int time) {
        System.out.printf(">>> 화장실 모니터 : [   " + time + " 초 경과 ] ========================================" +
                "=====================================================================\n");
    }

    private static void printToiletMonitor(List<Person> people) {
        System.out.print("");
    }

    private static String personString(Person person) {

        StringBuilder personString = new StringBuilder();

        personString.append("[ " + person.getId() + " ( " + person.getNowWaitingTime() +
                " / " + person.getWaitingTime() + " )" + " ]");

        return personString.toString();
    }

}

class ToiletThread implements Runnable {

    private Person person;

    @Override
    public void run() {
        if(this.person == null) {

        }
    }
}

class Person {
    private int id;
    private int nowWaitingTime;
    private int waitingTime;

    public Person(int id, int time) {
        this.id = id;
        this.nowWaitingTime = time;
        this.waitingTime = time;
    }

    public boolean waitSecond() {
        if (this.nowWaitingTime <= 0) {
            return false;
        }
        nowWaitingTime--;
        return true;
    }

    public int getId() {
        return id;
    }

    public int getNowWaitingTime() {
        return nowWaitingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }
}