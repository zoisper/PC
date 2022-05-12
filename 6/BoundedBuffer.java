import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer<T> {
    T buf[];
    int iget = 0;
    int iput = 0;
    private int nelems = 0;
    Lock l = new ReentrantLock();
    Condition notFull = l.newCondition();
    Condition notEmpty = l.newCondition();

    public BoundedBuffer(int N){
        this.buf = (T[]) new Object[N];
    }

    public T get() throws InterruptedException {
        l.lock();

        try {
            while (nelems == 0) 
                notEmpty.await();
            T res = buf[iget];
            iget = (iget + 1) % buf.length;
            nelems--;
            notFull.signal();
            return res;
        } 
        finally {
            l.unlock();
        }
    }

    public void put(T v) throws InterruptedException {
        l.lock();
        try{
            while(nelems == buf.length)
                notFull.await();
            buf[iput] = v;
            iput = (iput+1) % buf.length;
            nelems+=1;
            notEmpty.signal();

        }
        finally{
            l.unlock();
        }
    }
    
}



class Main{
    public static void main(String[] args){
        
       BoundedBuffer<Integer> b = new BoundedBuffer<>(5);
       
       new Thread(() -> {
        try{
             while(true){
                 System.out.println("Vou fazer get");
                 int x = b.get();
                 System.out.println("Fiz get de: " + x);
                 Thread.sleep(200);
             }
         }
         catch(Exception e){}

     }).start();


       new Thread(() -> {
           try{   
                for(int i=1;;i++){
                   System.out.println("Vou fazer put de: " + i);
                   b.put(i);
                   System.out.println("Fiz put de: " + i);
                   Thread.sleep(200);
                }
            }
            catch(Exception e){}

       }).start();

       
    
       
    }      
}
