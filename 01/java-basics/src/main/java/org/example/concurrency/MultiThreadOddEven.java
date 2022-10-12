package org.example.concurrency;

public class MultiThreadOddEven {

    public static void main(String[] args) {

        int n=10;
        NumGenerator ng= new NumGenerator(n);
        Thread todd=new Thread(new Runnable() {
            public void run()
            {
                ng.printOddNumber();
            }
        });
        Thread teven=new Thread(new Runnable() {
            public void run()
            {
                ng.printEvenNumber();
            }
        });
        todd.start();
        teven.start();


    }
}


class NumGenerator
{
    int l;
    NumGenerator(int n)
    {
        this.l=n;
    }
   int num=1;
    void printOddNumber()
    {
     //   synchronized (this) {
        while(num<=l) {
           synchronized (this) {
                while (num % 2 == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                System.out.println("t1 : "+num);
                num++;
                notify();
            }
        }
    }
    void printEvenNumber()
    {
        synchronized (this) {
        while(num<=l) {
     //       synchronized (this) {
                while (num % 2 == 1) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                System.out.println("t2 : "+num);
                num++;
                notifyAll();
            }
        }
    }

}