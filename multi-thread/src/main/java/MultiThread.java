public class MultiThread extends Thread {

    private int threadNum;

    MultiThread(int threadNum) {
        this.threadNum = threadNum;
    }

    public void run() {
        try {
            while(SumData.getCount() <= 100) {
                System.out.println("Thread[" + threadNum + "] : " + SumData.getSum() + " + " +
                        SumData.getCount() + " = " + (SumData.getCount() + SumData.getSum()));
                SumData.addNumber();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
