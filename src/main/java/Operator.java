public class Operator implements Runnable {
    // Очередь звонков
    private ATS ats;

    protected Operator(ATS ats) {
        this.ats = ats;
    }

    @Override
    public void run() {
        Call nowCall;
        // Время ответа опреатора
        long answerTime;
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
                System.out.println(ex.getMessage());
            }
            System.out.println(nameOperator + " в очереди ещё " + ats.getQueueSize() + " звонков.");
        }
    }
}
