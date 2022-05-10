public class SumData {

    private static int count = 0;
    private static int sum = 0;

    public static int getCount() {
        return count;
    }

    public static int getSum() {
        return sum;
    }

    public static void addNumber() {
        sum += count++;
    }
}
