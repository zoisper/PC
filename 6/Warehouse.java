import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

class Warehouse {
    
    Lock l = new ReentrantLock();
   
    private class Item {
        int quant = 0;
        Condition cond = l.newCondition();

    }

    Map<String, Item> map = new HashMap<>();

    private Item get(String s){
        Item item = map.get(s);
        if(item == null){
            item = new Item();
            map.put(s, item);
        }

        return item;
    }

    public void supply(String item, int quantity){
        l.lock();
        try{
            Item it = get(item);
            it.quant += quantity;
            it.cond.signalAll();

        }
        finally{
            l.unlock();
        }

    }

    public void consume(String[] items) throws InterruptedException{
        l.lock();
        try{
            for(String s: items){
                Item it = get(s);
                while(it.quant == 0)
                    it.cond.await();
                it.quant -=1;
            }

        }
        finally{
            l.unlock();
        }

    }

}