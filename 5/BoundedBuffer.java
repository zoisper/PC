
class BoundedBuffer<T> {
    private T[] buff;
    private int iget = 0;
    private int iput = 0;
    private int nelems = 0;

    BoundedBuffer(int N) { 
        this.buff = (T[]) new Object[N];
     }


    public synchronized T get() throws InterruptedException { 
        T res;
        while(nelems == 0)
            wait();
        res = buff[iget];
        iget = (iget+1) %buff.length;
        nelems -=1;
        notifyAll();

        return res;

     }

    public synchronized void put(T x) throws InterruptedException {
        while(nelems == buff.length)
            wait();
        buff[iput] = x;
        iput = (iput+1) % buff.length;
        nelems +=1;
        notifyAll();

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
