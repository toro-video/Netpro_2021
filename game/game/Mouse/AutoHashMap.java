package game.Mouse;

import java.util.HashMap;

public class AutoHashMap<V> extends HashMap<Integer, V> {
    public int autoMapping(V v){
        int n=0;
        while(true){
            if(!this.containsKey(n))break;
            n++;
        }
        this.put(Integer.valueOf(n),v);
        return n;
    }
    public int autoRemoving(Integer k){
        remove(k);
        return -1;
    }
}