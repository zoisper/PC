import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;  

class NotEnoughFunds extends Exception{}
class InvalidAccount extends Exception{}

class Bank{
    
    public static class Account{
        private int balance;
        public int balance(){ return balance;}
        public void deposit(int val){ balance+=val;}
        public void withdraw(int val) throws NotEnoughFunds{
            if(balance<val) throw new NotEnoughFunds();
            balance -=val;
        }
        Lock l = new ReeentrantLock();

    }

    HashMap<Integer, Account> accounts = new HashMap<>();
    Lock l = new ReeentrantLock();
    int lasId = 0;

    
    int creatAccount(int balance){
        Account c = new Account();
        c.deposit(balance);
        
        l.lock();
        try{
            lastId += 1;
            accounts.put(lastId,c);
            return lastId;
        }

        finally{
            l.unlock();
        }
        
    }


    int closeAccount(int id){

    } 



    public void deposit(int id, int val) throws InvalidAccount{
        l.lock();
        try{
            Account c = accounts.get(id);
            if(c == null) throw new InvalidAccount();
            c.deposit(val);
        }
        
        finally{
            l.unlock();
        }
        
    }

    public void withdraw(int id, int val) throws InvalidAccount, NotEnoughFunds{
        l.lock();
        try{
            Account c = accounts.get(id);
            if(c == null) throw new InvalidAccount();
            c.withdraw(val);
        }
        
        finally{
            l.unlock();
        }
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

class Guiao3{
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