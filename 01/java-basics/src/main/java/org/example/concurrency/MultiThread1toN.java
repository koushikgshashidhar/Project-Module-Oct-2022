package org.example.concurrency;

/*
To get output:
T1-1
T2-2
T3-3
T1-4
T2-5
T3-6
T1-7
T2-8
T3-9
T1-10
 */
public class MultiThread1toN {

    private static final Object lock = new Object();
    private static int NO_OF_THREADS = 3;
    private static int PRINT_NUMBERS_UPTO = 10;
    private static int counter = 1;

    public static void main(String[] args) {
//        if (args.length > 0) {
//            try {
//                NO_OF_THREADS = Integer.parseInt(args[0]);
//                PRINT_NUMBERS_UPTO = Integer.parseInt(args[1]);
//            } catch (NumberFormatException e) {
//                System.err.println("Arguments must be an integer.");
//                System.exit(1);
//            }
//        }
        // Creating threads
        for (int i = 1; i <= NO_OF_THREADS; i++) {
            new Thread(new Generator(i % NO_OF_THREADS), "T" + i).start();
        }
    }

    static class Generator implements Runnable {

        private int remainder;

        Generator(int remainder) {
            this.remainder = remainder;
        }

        @Override
        public void run() {
            while (counter <= PRINT_NUMBERS_UPTO) {
                synchronized (lock) {
                    while (counter % NO_OF_THREADS != remainder) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (counter % NO_OF_THREADS == remainder &&
                            counter <= PRINT_NUMBERS_UPTO) {
                        System.out.println(Thread.currentThread().getName()
                                + "-" + counter);
                    }
                    counter++;
                    lock.notifyAll();
                }

            }
        }

    }
}
