public class Counter {

    public int value;

    public Counter(){
        this.value = 0;
    }

    public void increment(){
        this.value +=1;
    }

    public int getValue(){
        return this.value;
    }

}
