package util;


import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/******************************************************************
 创建人: 杨翔
 日　期: 2017/3/18
 修改人:
 日　期:
 描　述: 对缓存接口的一般性实现
 版　本: v1.00 Copyright(c).
 ******************************************************************/
public abstract class AbstractCacheMap<K, V> implements Cache<K, V> {

    class CacheObject<K2, V2> {

        final K2 key;
        final V2 cachedObject;

        /**
         * 最后访问时间
         */
        long lastAccess;

        /**
         * 访问次数
         */
        long accessCount;

        /**
         * 对象存活时间(time-to-live)
         */
        long ttl;

        CacheObject(K2 key, V2 value, long ttl) {
            this.key = key;
            this.cachedObject = value;
            this.ttl = ttl;
            this.lastAccess = System.currentTimeMillis();
        }

        /**
         * @return 是否到期
         */
        boolean isExpired() {
            return ttl != 0 && lastAccess + ttl < System.currentTimeMillis();
        }

        V2 getObject() {
            lastAccess = System.currentTimeMillis();
            accessCount++;
            return cachedObject;
        }
    }

    protected Map<K, CacheObject<K, V>> cacheMap;

    private final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
    private final Lock readLock = cacheLock.readLock();
    private final Lock writeLock = cacheLock.writeLock();


    /**
     * 缓存大小
     */
    protected int cacheSize;

    /**
     * 是否设置默认过期时间
     */
    protected boolean existCustomExpire;

    @Override
    public int getCacheSize() {
        return cacheSize;
    }

    /**
     * 默认过期时间
     */
    protected long defaultExpire;

    public AbstractCacheMap(int cacheSize, long defaultExpire) {
        this.cacheSize = cacheSize;
        this.defaultExpire = defaultExpire;
    }

    @Override
    public long getDefaultExpire() {
        return defaultExpire;
    }

    /**
     * 是否需要清除过期对象
     *
     * @return 是否需要清除过期对象
     */
    protected boolean isNeedClearExpiredObject() {
        return defaultExpire > 0 || existCustomExpire;
    }

    @Override
    public void put(K key, V value) {
        put(key, value, defaultExpire);
    }

    @Override
    public void put(K key, V value, long expire) {
        writeLock.lock();

        try {
            CacheObject<K, V> co = new CacheObject<K, V>(key, value, expire);
            if (expire != 0) {
                existCustomExpire = true;
            }
            if (isFull()) {
                eliminate();
            }
            cacheMap.put(key, co);
        } finally {
            writeLock.unlock();
        }
    }


    /**
     * 通过key获取对应value
     *
     * @param key key
     * @return value or null
     */
    @Override
    public V get(K key) {
        readLock.lock();

        try {
            CacheObject<K, V> co = cacheMap.get(key);
            if (co == null) {
                return null;
            }
            if (co.isExpired()) {
                cacheMap.remove(key);
                return null;
            }

            return co.getObject();
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 淘汰过期对象
     *
     * @return 淘汰个数
     */
    @Override
    public final int eliminate() {
        writeLock.lock();
        try {
            return eliminateCache();
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 淘汰对象具体实现
     *
     * @return 淘汰个数
     */
    protected abstract int eliminateCache();

    @Override
    public boolean isFull() {
        //o -> 无限制
        return cacheSize != 0 && cacheMap.size() >= cacheSize;
    }

    @Override
    public void remove(K key) {
        writeLock.lock();
        try {
            cacheMap.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void clear() {
        writeLock.lock();
        try {
            cacheMap.clear();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public int size() {
        return cacheMap.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
}
