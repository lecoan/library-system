package util;

/******************************************************************
 创建人: 杨翔
 日　期: 2017/3/18
 修改人:
 日　期:
 描　述: cache接口定义
 版　本: v1.00 Copyright(c).
 ******************************************************************/
public interface Cache<K, V> {
    /**
     * @return 当前缓存的大小
     */
    int size();

    /**
     * @return 默认存活时间
     */
    long getDefaultExpire();

    /**
     * @param key   key
     * @param value value
     */
    void put(K key, V value);

    /**
     * @param key    key
     * @param value  value
     * @param expire 过期时间
     */
    void put(K key, V value, long expire);

    /**
     * @param key key
     * @return 缓存对象
     */
    V get(K key);

    /**
     * 淘汰对象
     *
     * @return 被删除对象大小
     */
    int eliminate();

    /**
     * @return 缓存是否已经满
     */
    boolean isFull();

    /**
     * 删除缓存对象
     *
     * @param key key
     */
    void remove(K key);

    /**
     * 清除所有缓存对象
     */
    void clear();

    /**
     * @return 缓存大小
     */
    int getCacheSize();

    /**
     * 缓存中是否为空
     */
    boolean isEmpty();

}