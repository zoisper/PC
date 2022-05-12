import java.util.concurrent.Semaphore;

class Barreira {
    int n;
    int c = 0;
    Semaphore mut = new Semaphore(1);
    Semaphore sem = new Semaphore(0);


    Barreira (int N) { 
        this.n = N;
    }
    void await() throws InterruptedException { 
        mut.acquire();
        c+=1;
        int v = c;
        mut.release();
        if(v==n)
            for(int i=0; i<n ; i++)
                sem.release();
        sem.acquire();
     }
}
