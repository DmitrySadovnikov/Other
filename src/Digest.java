import java.util.HashMap;
import java.util.Map;

/**
 * 1. В Map должны быть заданны ключ и значение, т.е. должно выглядеть так  Map<byte[], byte[]> cache;
 * 2. Кэш не отчищается, возможна утечка памяти, поэтому new HashMap<>() лучше определять в методе, а не в поле
 * 3. HashMap не потокобезопасный, а в методе digest его синхронизируют, лучше заменить его на ConcurrentHashMap
 * */

public abstract class Digest {
    private Map<byte[], byte[]> cache;

    public byte[] digest(byte[] input) {
        cache = new HashMap<>();
        byte[] result = cache.get(input);
        if (result == null) {
            synchronized (cache) {
                result = cache.get(input);
                if (result == null) {
                    result = doDigest(input);
                    cache.put(input, result);
                }
            }
        }
        return result;
    }

    protected abstract byte[] doDigest(byte[] input);
}