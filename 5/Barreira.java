class Barreira {
    private int n;
    private int c = 0;
    private boolean  w = false;
    
    Barreira (int N) { 
        this.n = N;

    }
    
    public synchronized void await() throws InterruptedException { 
        c +=1;
        if(c==1)
            w = true;
        
        if(c==n){
            notifyAll();
            c = 0;
            w = false;
        } 
        
        while(w)
            wait();

    }
}


class Main {
    public static void main(String args[]) {
        try {
            Barreira b = new Barreira(5);

            for (int i = 0; i < 20; i++) {
                new Thread(() -> {
                    try {
                        System.out.println("Await");
                        b.await();
                        System.out.println("Released");
                    } catch (Exception e) {}
                }).start();
                Thread.sleep(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
