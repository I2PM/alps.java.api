package alps.java.api.util;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class CompatibilityDictionary<K, V> extends HashMap<K, V> implements ICompatibilityDictionary<K, V> {
    public boolean tryAdd(K key, V value) {
        if (!this.containsKey(key)) {
            this.put(key, value);
            return this.containsKey(key);
        } else {
            return false;
        }
    }
}