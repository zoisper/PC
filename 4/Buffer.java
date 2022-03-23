import java.util.concurrent.Semaphore;
import java.util.Random;

class BoundedBuffer{
    private int[] buf;
    private int iget = 0;
    private int iput = 0;
    
    Semaphore items;
    Semaphore slots;
    Semaphore mutget;
    Semaphore mutput;  

    BoundedBuffer(int N) { 

        this.buf = new int[N];
        this.items = new Semaphore(0);
        this.slots = new Semaphore(N);
        this.mutget = new Semaphore(1);
        this.mutput = new Semaphore(1);
        
     }
    
     public int get() throws InterruptedException { 
        int res;
        items.acquire();
        mutget.acquire();
        res = buf[iget];
        iget = (iget+1) % buf.length;
        mutget.release();
        slots.release();
        return res;
     }


    public void put(int v) throws InterruptedException {
        slots.acquire();
        mutput.acquire();
        buf[iput] =  v;
        iput = (iput+1) % buf.length;
        mutput.release();
        items.release();

     }

}



class Teste{
    public static void main(String[] args){
        
       BoundedBuffer b = new BoundedBuffer(20);
       
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