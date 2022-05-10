public class SumData {

    private int count;
    private int sum;

    public SumData() {
        this.count = 1;
        this.sum = 0;
    }

    public int getCount() {
        return count;
    }

    public int getSum() {
        return sum;
    }

    public void addNumber() {
        sum += count++;
    }
}
