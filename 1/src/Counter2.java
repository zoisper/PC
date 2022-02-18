public class Counter2 implements Runnable{

    public static int counter;
    public int n;

    public Counter2(int n){
        this.n = n;
    }

    public int getCounter(){
        return counter;
    }

    public void run(){
        for(int i=0; i<n; i++)
            counter +=1;
    }
}
