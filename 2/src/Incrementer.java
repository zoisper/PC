public class Incrementer implements Runnable {

    private Counter counter;
    private int n;

    public Incrementer(Counter counter, int n) {
        this.counter = counter;
        this.n = n;
    }


        public void run () {
            for (int i = 0; i < n; i++)
                counter.increment();    
        }

        
        // metodo a utilizar pelo incrementer que acede diretamente à variavel value do contador
        /*
        public void run () {
                for (int i = 0; i < n; i++)
                    synchronized(counter){
                        counter.value+=1;
                    }
        }*/
    
    }


