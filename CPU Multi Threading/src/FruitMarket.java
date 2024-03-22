import java.util.LinkedList;
import java.util.Queue;

class FruitMarket {
    private static final int MARKET_CAPACITY = 10;
    private static final Queue<String> appleBuffer = new LinkedList<>();
    private static final Queue<String> orangeBuffer = new LinkedList<>();
    private static final Queue<String> grapeBuffer = new LinkedList<>();
    private static final Queue<String> watermelonBuffer = new LinkedList<>();

    static class Farmer implements Runnable {
        private String fruitType;

        public Farmer(String fruitType) {
            this.fruitType = fruitType;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (this) {
                    if (fruitType.equals("apple") && appleBuffer.size() < MARKET_CAPACITY) {
                        appleBuffer.offer("Apple");
                        System.out.println("Farmer puts one Apple up for sale.");
                    } else if (fruitType.equals("orange") && orangeBuffer.size() < MARKET_CAPACITY) {
                        orangeBuffer.offer("Orange");
                        System.out.println("Farmer puts one Orange up for sale.");
                    } else if (fruitType.equals("grape") && grapeBuffer.size() < MARKET_CAPACITY) {
                        grapeBuffer.offer("Grape");
                        System.out.println("Farmer puts one Grape up for sale.");
                    } else if (fruitType.equals("watermelon") && watermelonBuffer.size() < MARKET_CAPACITY) {
                        watermelonBuffer.offer("Watermelon");
                        System.out.println("Farmer puts one Watermelon up for sale.");
                    } else {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    notifyAll();
                }
            }
        }
    }

    static class Customer implements Runnable {
        private String fruitType;

        public Customer(String fruitType) {
            this.fruitType = fruitType;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (this) {
                    if (fruitType.equals("apple") && !appleBuffer.isEmpty()) {
                        appleBuffer.poll();
                        System.out.println("Customer bought one Apple.");
                    } else if (fruitType.equals("orange") && !orangeBuffer.isEmpty()) {
                        orangeBuffer.poll();
                        System.out.println("Customer bought one Orange.");
                    } else if (fruitType.equals("grape") && !grapeBuffer.isEmpty()) {
                        grapeBuffer.poll();
                        System.out.println("Customer bought one Grape.");
                    } else if (fruitType.equals("watermelon") && !watermelonBuffer.isEmpty()) {
                        watermelonBuffer.poll();
                        System.out.println("Customer bought one Watermelon.");
                    } else {
                        System.out.println("Customer is waiting to buy one " + fruitType + "...");
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread farmer1 = new Thread(new Farmer("apple"));
        Thread farmer2 = new Thread(new Farmer("orange"));
        Thread farmer3 = new Thread(new Farmer("grape"));
        Thread farmer4 = new Thread(new Farmer("watermelon"));

        Thread customer1 = new Thread(new Customer("apple"));
        Thread customer2 = new Thread(new Customer("orange"));
        Thread customer3 = new Thread(new Customer("apple"));
        Thread customer4 = new Thread(new Customer("apple"));
        Thread customer5 = new Thread(new Customer("orange"));
        Thread customer6 = new Thread(new Customer("grape"));

        farmer1.start();
        farmer2.start();
        farmer3.start();
        farmer4.start();

        customer1.start();
        customer2.start();
        customer3.start();
        customer4.start();
        customer5.start();
        customer6.start();
    }
}
