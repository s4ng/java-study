public class MultiThread extends Thread {

    private int threadNum;

    private SumData sumData;

    MultiThread(int threadNum, SumData sumData) {
        this.threadNum = threadNum;
        this.sumData = sumData;
    }

    public void run() {
        synchronized ( this ) {
            try {
                while(sumData.getCount() <= 100) {
                    System.out.println("Thread[" + threadNum + "] : " + sumData.getSum() + " + " +
                            sumData.getCount() + " = " + (sumData.getCount() + sumData.getSum()));
                    sumData.addNumber();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
