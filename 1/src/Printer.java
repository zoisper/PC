public class Printer implements Runnable{
    private int n = 100;

    public Printer(int n){
        this.n = n;
    }
    public void run(){
        StringBuilder sb = new StringBuilder();

        /*for (int i = 0; i<n; i++)
            System.out.print(i + " ");
        System.out.println();*/

        for (int i = 0; i<n; i++)
            sb.append(i).append(" ");
        System.out.println(sb.toString());

    }
}
