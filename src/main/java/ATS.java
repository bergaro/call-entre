import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * ПРИЧИНЫ ИСПОЛЬЗОВАНИЯ LinkedBlockingQueue<>
 * Использую LinkedBlockingQueue<> - из-за того, что в ней
 * имеется 2 ReentrantLock, т.е. один на чтение, другой на запись.
 * Следовательно, при добавлении нового вызова не блокируется получение вызова (ответ на вызов)
 * из очереди для оператора. (Ну по крайней мере так у меня в голове уложилось)
 */
public class ATS implements Runnable {
    // Доступность АТС в секундах
    private final static int ATS_WORK = 15; // секунд
    // Кол-во звонков генерируемых в секнуду
    private final static int CALLS_PER_SECOND = 60;

    private static ATS instance;
    // Очередь АТС (линия соединения с оператором)
    private final static Queue<Call> ATS_LINE = new LinkedBlockingQueue<>();

    private ATS() { }

    protected static ATS getInstance() {
        if(instance == null) {
            instance = new ATS();
        }
        return instance;
    }
    /**
     * Добавление звонков в очередь
     */
    private void setCallToAts() {
        // Технический таймаут для выдерживания 1 сек между добавлениями звонков
        int expectation = 1000;
        for (int i = 0; i < ATS_WORK; i++) {
            for (int j = 0; j < CALLS_PER_SECOND; j++) {
                ATS_LINE.add(new Call());
            }
            try {
                Thread.sleep(expectation);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            System.out.println("ЗВОНКОВ В РАБОТЕ = " + getQueueSize());
        }
        Thread.currentThread().interrupt();
    }

    protected int getQueueSize() {
        return ATS_LINE.size();
    }

    protected Call getCallFromQueue() {
        return ATS_LINE.poll();
    }

    @Override
    public void run() {
        System.out.println("АТС - НАЧАЛО ДНЯ.");
        setCallToAts();
        System.out.println("АТС - КОНЕЦ ДНЯ.");
    }
}
