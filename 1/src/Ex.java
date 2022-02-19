public class Ex {
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
        Printer printer = new Printer(I);
        for(int i=0; i<N; i++)
            threads[i] = new Thread(printer);
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
        Counter1 counter = new Counter1(I);
        for(int i=0; i<N; i++)
            threads[i] = new Thread(counter);
        for(int i=0; i<N; i++)
            threads[i].start();
        for(int i=0; i<N; i++)
            try{
                threads[i].join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        System.out.println("Contador1: " + counter.getCounter() + '\n');
    }

    public static void ex2_2(int N, int I){
        System.out.println("N:" + N + " I:" + I);
        Thread[] threads = new Thread[N];
        Counter2 counter = new Counter2(I);
        for(int i=0; i<N; i++)
            threads[i] = new Thread(counter);
        for(int i=0; i<N; i++)
            threads[i].start();
        for(int i=0; i<N; i++)
            try{
                threads[i].join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        System.out.println("Contador2: " + counter.getCounter() + '\n');
    }

    public static  void ex3(int n, int start, int inc){
        for(int i = 0; i<n; i++ ){
            System.out.println("n: " + i + "\n");
            ex2_1(start + i*inc,start + i*inc);
            ex2_2(start + i*inc,start + i*inc);


        }

    }




}
