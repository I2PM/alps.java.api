
/**
 * A {@link java.util.Dictionary} implementation that extends {@link Hashtable} and implements
 * the {@link alps.net.api.util.ICompatibilityDictionary} interface.
 *
 * @param <K> the type of keys maintained by this dictionary
 * @param <V> the type of mapped values
 */

package alps.net.api.util;
import java.util.Hashtable;

public class CompatibilityDictionary<K, V> extends Hashtable<K, V> implements ICompatibilityDictionary<K, V> {
    public boolean tryAdd(K key, V value) {
        if (!this.containsKey(key)) {
            this.put(key, value);
            return this.containsKey(key);
        } else {
            return false;
        }
    }
}