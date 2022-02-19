public class Counter1 implements Runnable{

    private int counter;
    private int n;
    public Counter1(int n){
        this.counter = 0;
        this.n = n;
    }

    public void increment(){
        this.counter +=1;
    }

    public int getCounter(){
        return this.counter;
    }

    public void run(){
        for (int i = 0; i<n; i++)
            increment();
    }
}
