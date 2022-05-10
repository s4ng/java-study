public class Application {

    public static void main(String[] args) {
        MultiThread[] multiThreadPool = new MultiThread[4];
        for(int i = 0; i < 4; i++) {
            multiThreadPool[i] = new MultiThread(i + 1);
            multiThreadPool[i].start();
        }
    }
}
