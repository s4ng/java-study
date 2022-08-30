import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Elevator e1 = new Elevator(null, 0, 1);
        Elevator e2 = new Elevator(null, 0, 1);
        Elevator e3 = new Elevator(null, 0, 1);

        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 0, TimeUnit.SECONDS, blockingQueue);

        

        while(true) {
            Thread.sleep(1000L);
            printDashBoard(e1, e2, e3);
        }
    }

    private static void printDashBoard(Elevator e1, Elevator e2, Elevator e3) {
        int one, two ,three;
        clearScreen();
        printLine();
        for(int i = 10; i >= 1; i--) {
            System.out.println(i + (i == 10 ? "층" : " 층") + "  |    " + printElevator(e1, i) +
                    printElevator(e2, i)+ printElevator(e3, i));
        }
        printLine();
        System.out.println("[엘리베이터]");
        System.out.println("- 1호기 : 현재 위치 : " + (e1.flour == 10 ? "10층" : " " + e1.flour + "층")
                + " / " + "방향 : " + e1.getIsUpToString() + " / 적재량 : " + e1.getWeight() + " / 인원수 : " + e1.getPeopleCount());
        System.out.println("- 2호기 : 현재 위치 : " + (e2.flour == 10 ? "10층" : " " + e2.flour + "층")
                + " / " + "방향 : " + e2.getIsUpToString() + " / 적재량 : " + e2.getWeight() + " / 인원수 : " + e2.getPeopleCount());
        System.out.println("- 3호기 : 현재 위치 : " + (e3.flour == 10 ? "10층" : " " + e3.flour + "층")
                + " / " + "방향 : " + e3.getIsUpToString() + " / 적재량 : " + e3.getWeight() + " / 인원수 : " + e3.getPeopleCount());

        System.out.println("[최근 이벤트]");
    }

    private static String printElevator(Elevator elevator, int flour) {
        StringBuilder sb = new StringBuilder();
        if(elevator.getFlour() != flour) {
            sb.append(" [                 ] ");
        } else {
            sb.append(" [ ");
            sb.append(elevator.getIsUp() == null ? "ST :  " : ( elevator.getIsUp() ? "UP : "  : "DN :  "));
            sb.append(elevator.getWeight()).append("/1000");
            if(sb.length() <= 19) {
                sb.append(" ".repeat(Math.max(0, 19 - sb.length())));
            }
            sb.append("] ");
        }

        return sb.toString();
    }

    private static void printLine() {
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    public static void clearScreen() {
        for(int i = 0; i < 10; i++) {
            System.out.println("\n");
        }
    }

    public static class Elevator {
        Boolean isUp;
        List<Integer> people;
        int flour;
        int weight;

        public Boolean getIsUp() {
            return this.isUp;
        }

        public String getIsUpToString() {
            if(isUp == null) {
                return "STOP";
            } else if (isUp) {
                return "UP  ";
            } else {
                return "DOWN";
            }
        }

        public int getWeight() {
            return this.weight;
        }

        public int getPeopleWeight() {
            int result = 0;
            for ( Integer person : people ) {
                result += person;
            }
            return result;
        }

        public int getFlour() {
            return this.flour;
        }

        public int getPeopleCount() {
            return this.people.size();
        }

        public void setFlour(int flour) {
            this.flour = flour;
        }

        public void addPeople(int peopleWeight) {
            people.add(peopleWeight);
        }

        public Elevator(Boolean isUp, int weight, int flour) {
            this.isUp = isUp;
            this.weight = weight;
            this.people = new ArrayList<>();
            this.flour = flour;
        }
    }

    static class Task implements Runnable {

        Elevator e1;
        Elevator e2;
        Elevator e3;
        Waiting waiting;

        public Task(Waiting waiting, Elevator e1, Elevator e2, Elevator e3) {
            this.waiting = waiting;
            this.e1 = e1;
            this.e2 = e2;
            this.e3 = e3;
        }

        @Override
        public void run() {
            // TODO
        }
    }

    static class Waiting {
        int nowFlour;
        List<WaitingPerson> waitingPeople;
        public Waiting(int nowFlour, List<WaitingPerson> waitingPeople) {
            this.nowFlour = nowFlour;
            this.waitingPeople = waitingPeople;
        }
    }

    static class WaitingPerson {
        int weight;
        int goingFlour;
        public WaitingPerson(int weight, int goingFlour) {
            this.weight = weight;
            this.goingFlour = goingFlour;
        }
    }
}
