package alps.java.api.util;
import java.util.Map;

public interface ICompatibilityDictionary<K, V> extends Map<K, V> {
    boolean tryAdd(K key, V value);
}

