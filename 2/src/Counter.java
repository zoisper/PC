public class Counter {

    public int value;

    public Counter(){
        this.value = 0;
    }

    public synchronized void increment(){
        this.value +=1;
    }

    // ou

    /*
    public void increment(){
        synchronized(this){
            this.value +=1;
        }
    }*/

    public int getValue(){
        return this.value;
    }

}
