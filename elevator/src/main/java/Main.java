import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Elevator[][] elevators = new Elevator[10][3];

        while(true) {
            Thread.sleep(1000L);
            printDashBoard(elevators);
        }
    }

    private static void printDashBoard(Elevator[][] elevators) {
        int one, two ,three;
        clearScreen();
        printLine();
        for(int i = 10; i >= 1; i--) {
            System.out.println(i + (i == 10 ? "층" : " 층") + "  |    " + printElevator(elevators[10-i][0]) +
                    printElevator(elevators[10-i][1])+ printElevator(elevators[10-i][2]));
        }
        printLine();
        System.out.println("[엘리베이터]");
        System.out.println("- 1호기 : 현재 위치 ");
        System.out.println("- 2호기 : 현재 위치 ");
        System.out.println("- 3호기 : 현재 위치 ");

        System.out.println("[최근 이벤트]");
    }

    private static String printElevator(Elevator elevator) {
        StringBuilder sb = new StringBuilder();
        if(elevator == null) {
            sb.append(" [                 ] ");
        } else {
            sb.append(" [ ");
            sb.append(elevator.getIsUp() == null ? "--" : ( elevator.getIsUp() ? "UP : " : "DN : "));
            sb.append(elevator.getWeight()).append("/1000");
            if(sb.length() < 19) {
                sb.append(" ".repeat(Math.max(0, sb.length() - 19)));
            }
            sb.append("] ");
        }

        return sb.toString();
    }

    private static void printLine() {
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    public static void clearScreen() {
        for(int i = 0; i < 50; i++) {
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

        public int getWeight() {
            return this.weight;
        }

        public int getPeopleWeight() {
            int result = 0;
            for(int i = 0; i < people.size(); i++) {
                result += people.get(i);
            }
            return result;
        }

        public int getFlour() {
            return this.flour;
        }


        public Elevator(Boolean isUp, int weight, int flour) {
            this.isUp = isUp;
            this.weight = weight;
            this.people = new ArrayList<>();
            this.flour = flour;
        }
    }
}
