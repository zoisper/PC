import java.util.concurrent.Semaphore;

class Barreira {
    private final int N;
    private int c = 0;
    private Semaphore mut = new Semaphore(1);
    private Semaphore sem = new Semaphore(1);

    public Barreira(int N){
        this.N = N;
    }

    public void await() throws InterruptedException{
        mut.acquire();
        c+=1;
        int v = c;
        mut.release();
        
        /*if(v<N){
            sem.acquire();

        }
        
        else{
            for(int i=0; i<N-1; i++)
                sem.release();
        }*/

        if(v==N)
            for(int i=0;i<N;i++)
                sem.release();
        sem.acquire();
    }
    
}
