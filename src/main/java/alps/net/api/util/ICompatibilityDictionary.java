//interface for CompatibilityDictionary which extends Map with the Method tryAdd
//Not sure about the import java.util.Dictionary
package alps.net.api.util;
import java.util.Dictionary;
import java.util.Map;

public interface ICompatibilityDictionary<K, V> extends Map<K, V> {
    boolean tryAdd(K key, V value);
}

