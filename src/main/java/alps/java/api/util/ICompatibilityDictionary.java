package alps.java.api.util;
import java.util.Map;

public interface ICompatibilityDictionary<K, V> extends Map<K, V> {
    /**
     * this Method is a hidden Method (Implementation with static)
     * @param key
     * @param value
     * @return
     */
     boolean tryAdd(K key, V value);
}

