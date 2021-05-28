public class Operator implements Runnable {
    // Очередь звонков
    private ATS ats;
    // Время ответа опреатора
    private long answerTime;

    protected Operator(ATS ats) {
        this.ats = ats;
    }

    @Override
    public void run() {
        Call nowCall;
        String nameOperator = Thread.currentThread().getName();
        while (true) {
            answerTime = (long) (Math.random() * 500 + 100);
            nowCall  = ats.getCallFromQueue();
            if(nowCall == null) {
                System.out.println(nameOperator + " закончил работу.");
                break;
            }
            try {
                Thread.sleep(answerTime);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            System.out.println(nameOperator + " в очереди ещё " + ats.getQueueSize() + " звонков.");
        }
        Thread.currentThread().interrupt();
    }
}
