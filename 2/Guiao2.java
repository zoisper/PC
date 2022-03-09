class NotEnoughFunds extends Exception{}
class InvalidAccount extends Exception{}

class Bank{
    
    public static class Account{
        private int balance;
        public synchronized int balance(){ return balance;}
        public synchronized void deposit(int val){ balance+=val;}
        public synchronized void withdraw(int val) throws NotEnoughFunds{
            if(balance<val) throw new NotEnoughFunds();
            balance -=val;
        }
    }

    public Account[] accounts;

    public Account get(int id) throws InvalidAccount{
        if( id<0 || id>=accounts.length) throw new InvalidAccount();
        return accounts[id];
    }

    public Bank(int n) {
        accounts = new Account[n]; //Criado um array para as contas
        for( int i=0; i<accounts.length;++i) accounts[i] = new Account();
    }  

    public void deposit(int id, int val) throws InvalidAccount{
        Account c = get(id);
        synchronized(c){
            c.deposit(val);
        }
    }

    public void withdraw(int id, int val) throws InvalidAccount, NotEnoughFunds{
        Account c = get(id);
        synchronized(c){
            c.withdraw(val);
        }
    }

    public int verAccount(){
        return this.accounts.length;
    }

    public void transfer(int from, int to, int val) throws InvalidAccount, NotEnoughFunds{
        if (from==to) return;
        Account cfrom = get(from);
        Account cto = get(to);
        Account o1, o2;
        if(from < to){
            o1= cfrom;
            o2 = cto;
        } else{
            o1 = cto;
            o2 = cfrom;
        }
        synchronized(o1){
            synchronized(o2){
                cfrom.withdraw(val);
                cto.deposit(val);
            }
        }   
    }

    public synchronized int totalBalance(int accounts[]) throws InvalidAccount{
        int total=0;
        for(int id: accounts){
            total += get(id).balance();
        }
        return total;
    }
}

class Depositor extends Thread{
    final int iterations;
    final Bank b;

    Depositor(int iterations, Bank b){ this.iterations = iterations; this.b = b;}

    public void run(){
        for(int i=0;i<iterations;++i){ 
            try{
                b.deposit(i % b.verAccount(),1);
            }
            catch(Exception e){System.out.println(e);}
        }
    } 
}

class Transferer extends Thread{
    final int iterations;
    final Bank b;
    Transferer(int iterations,Bank b){ this.iterations = iterations; this.b=b;}
    public void run(){
        try{
            for(int i=0; i<iterations; ++i){
                b.deposit(i % b.accounts.length, 1);
            }
        } catch(Exception e){}
     }
 }

class Observer extends Thread{
    final int iterations;
    final Bank b;
    Observer(int iterations, Bank b){ this.iterations = iterations; this.b = b;}
    public void run(){
        try{
            int NC = 10;
            int[] todasContas = new int[NC];
            for(int i=0; i<iterations; ++i){
                int balance = b.totalBalance(todasContas);
                if( balance != NC * 1000000)
                    System.out.println("Saldo errado:" + balance);
            }
        } catch(Exception e){}
    }
}

class Guiao2{
    public static void main(String[] args) throws InterruptedException, Exception{
        final int N = Integer.parseInt(args[0]);
        final int NC = Integer.parseInt(args[1]);
        final int I = Integer.parseInt(args[2]);
        Bank b = new Bank(NC);
        Thread[] a = new Thread[N];
        int[] todasContas = new int [NC];
        for(int i=0; i<NC; ++i) todasContas[i]=i;
        for(int i=0; i<NC; ++i) b.deposit(i,1000000);

        //for(int i=0; i<N; ++i) a[i] = new Depositor(I,b);
        for(int i=0; i<N; ++i) a[i] = new Transferer(I, b);

        new Observer(I,b).start();

        for(int i=0; i<N; ++i) a[i].start();
        for(int i=0; i<N; ++i) a[i].join();
        try{
            System.out.println(b.totalBalance(todasContas));
        }
        catch(Exception e){System.out.println(e);}
    }
}