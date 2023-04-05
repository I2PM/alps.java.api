package alps.java.api.util;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class CompatibilityDictionary<K, V> extends HashMap<K, V> implements ICompatibilityDictionary<K, V> {
    /**
     * Change the value to true or false depending on whether you have NET48 or not
     */
    private static final boolean NET48 = true;

    public boolean tryAdd(K key, V value) {
        if (NET48) {
            if (!this.containsKey(key)) {
                this.put(key, value);
                return this.containsKey(key);
            } else {
                return false;
            }
        } else {
            return (boolean) super.putIfAbsent(key, value);
        }
    }
}