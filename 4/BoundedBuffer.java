import java.util.concurrent.Semaphore;
import java.util.Random;

class BoundedBuffer<T> {
    T buf[];
    int iget = 0;
    int iput = 0;
    Semaphore items;
    Semaphore slots;
    Semaphore mutget;
    Semaphore mutput;

    BoundedBuffer(int N) { 
        this.buf =  (T[]) new Object[N];
        this.items = new Semaphore(0);
        this.slots = new Semaphore(N);
        this.mutget = new Semaphore(1);
        this.mutput = new Semaphore(1); 
     }
    
     T get() throws InterruptedException { 
        T res;
        items.acquire();
        mutget.acquire();
        res = (T) buf[iget];
        iget = (iget+1) % buf.length;
        mutget.release();
        slots.release();
        return res;
     }
    void put(T x) throws InterruptedException { 
        slots.acquire();
        mutput.acquire();
        buf[iput] = x;
        iput = (iput +1) % buf.length;
        mutput.release();
        items.release();
     }
}

class Main{
    public static void main(String[] args){
        
       BoundedBuffer<Integer> b = new BoundedBuffer<>(20);
       
       new Thread(() -> {
           try{   
                for(int i=1;;i++){
                   System.out.println("Vou fazer put de: " + i);
                   b.put(i);
                   System.out.println("Fiz put de: " + i);
                   Thread.sleep(1000);
                }
            }
            catch(InterruptedException e){}

       }).start();

       new Thread(() -> {
           try{
                for(int i=1;;i++){
                    System.out.println("Vou fazer get");
                    int x = b.get();
                    System.out.println("Fiz get de: " + x);
                    Thread.sleep(2000);
                }
            }
            catch(InterruptedException e){}

        }).start();
    
       
    }      
}