public class Main {
    public static void main(String[] args) {
        ATS line = ATS.getInstance();
        new Thread(null, line, "АТС").start();

        new Thread(null, new Operator(line), "Оператор 1").start();
        new Thread(null, new Operator(line), "Оператор 2").start();
        new Thread(null, new Operator(line), "Оператор 3").start();
        new Thread(null, new Operator(line), "Оператор 4").start();
        new Thread(null, new Operator(line), "Оператор 5").start();
        new Thread(null, new Operator(line), "Оператор 6").start();
        new Thread(null, new Operator(line), "Оператор 7").start();
        new Thread(null, new Operator(line), "Оператор 8").start();

    }
}
