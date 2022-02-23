public class Guiao1 {
    public static void main(String[] args)  {
        System.out.println("* Exercicio 1");
        ex1(10,10);
        System.out.println("* Exercicio 2.1");
        ex2_1(100, 100);
        System.out.println("* Exercicio 2.2");
        ex2_2(100, 100);
        System.out.println("* Exercicio 3");
        ex3(10,100,100);
    }

    public static void ex1(int N, int I){
        System.out.println("N:" + N + " I:" + I);
        Thread[] threads = new Thread[N];
        for(int i=0; i<N; i++)
            threads[i] = new Thread(new Printer(I));
        for(int i=0; i<N; i++)
            threads[i].start();
        for(int i=0; i<N; i++)
            try{
                threads[i].join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        System.out.println();
    }

    public static void ex2_1(int N, int I){
        System.out.println("N:" + N + " I:" + I);
        Thread[] threads = new Thread[N];
        Counter counter = new Counter();
        for(int i=0; i<N; i++)
            threads[i] = new Thread(new Incrementer1(counter,I));
        for(int i=0; i<N; i++)
            threads[i].start();
        for(int i=0; i<N; i++)
            try{
                threads[i].join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        System.out.println("Contador1: " + counter.getValue() + '\n');
    }

    public static void ex2_2(int N, int I){
        System.out.println("N:" + N + " I:" + I);
        Thread[] threads = new Thread[N];
        Counter counter = new Counter();
        for(int i=0; i<N; i++)
            threads[i] = new Thread(new Incrementer2(counter,I));
        for(int i=0; i<N; i++)
            threads[i].start();
        for(int i=0; i<N; i++)
            try{
                threads[i].join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        System.out.println("Contador2: " + counter.getValue() + '\n');
    }

    public static  void ex3(int n, int start, int inc){
        for(int i = 0; i<n; i++ ){
            System.out.println("n: " + i + "\n");
            ex2_1(start + i*inc,start + i*inc);
            ex2_2(start + i*inc,start + i*inc);


        }

    }




}
