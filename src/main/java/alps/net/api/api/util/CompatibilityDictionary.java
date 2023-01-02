
/**
 * A {@link java.util.Dictionary} implementation that extends {@link Hashtable} and implements
 * the {@link alps.net.api.api.util.ICompatibilityDictionary} interface.
 *
 * @param <K> the type of keys maintained by this dictionary
 * @param <V> the type of mapped values
 */

package alps.net.api.api.util;
import java.util.Dictionary;
import java.util.Hashtable;

public class CompatibilityDictionary<K, V> extends Hashtable<K, V> implements ICompatibilityDictionary<K, V> {
    /**
     * Attempts to add the specified key-value pair to this dictionary.
     * If the key is already present in the dictionary, the value is not added and the method returns false.
     *
     * @param key the key to add
     * @param value the value to associate with the key
     * @return true if the key-value pair was added to the dictionary, false if the key was already present
     */

    public boolean tryAdd(K key, V value) {
        if (!this.containsKey(key)) {
            this.put(key, value);
            return this.containsKey(key);
        } else {
            return false;
        }
    }
}