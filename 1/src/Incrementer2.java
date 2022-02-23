public class Incrementer2 implements Runnable{


    private Counter counter;
    private int n;

    public Incrementer2(Counter counter, int n) {
        this.counter = counter;
        this.n = n;
    }



    public void run () {
        for (int i = 0; i < n; i++)
            counter.value +=1;
    }

}
