import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * Использую LinkedBlockingQueue<> - из-за того, что в ней
 * имеется 2 ReentrantLock, т.е. один на чтение, другой на запись.
 * Следовательно, при добавлении нового вызова не блокируется получение вызова
 * из очереди для оператора. (Ну по крайней мере так у меня в голове уложилось =) )
 */
public class ATS implements Runnable {
    // Доступность АТС в секундах
    private int atsWork = 15; // секунд
    // Кол-во звонков генерируемых в секнуду
    private int callsPerSecond = 60;
    // Технический таймаут для выдерживания 1 сек между добавлениями звонков
    private int expectation = 1000;

    private static ATS instance;
    // Очередь АТС (линия соединения с оператором)
    private Queue<Call> atsLine = new LinkedBlockingQueue<>();

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
        for (int i = 0; i < atsWork; i++) {
            for (int j = 0; j < callsPerSecond; j++) {
                atsLine.add(new Call());
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
        return atsLine.size();
    }

    protected Call getCallFromQueue() {
        return atsLine.poll();
    }

    @Override
    public void run() {
        System.out.println("АТС - НАЧАЛО ДНЯ.");
        setCallToAts();
        System.out.println("АТС - КОНЕЦ ДНЯ.");
    }
}
