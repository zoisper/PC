public class Printer implements Runnable{
    private int n = 100;

    public Printer(int n){
        this.n = n;
    }
    public void run(){

        for (int i = 0; i<n; i++)
            System.out.print(i + " ");
        System.out.println();

    }
}
