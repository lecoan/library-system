package util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/******************************************************************
 创建人: 杨翔
 日　期: 2017/3/18
 修改人:
 日　期:
 描　述: LRU具体实现
 版　本: v1.00 Copyright(c).
 ******************************************************************/
public class LRUCache<K, V> extends AbstractCacheMap<K, V> {

    public LRUCache(int cacheSize, long defaultExpire) {

        super(cacheSize, defaultExpire);

        //linkedHash已经实现LRU算法 是通过双向链表来实现，当某个位置被命中，通过调整链表的指向将该位置调整到头位置，
        //新加入的内容直接放在链表头，如此一来，最近被命中的内容就向链表头移动，需要替换时，链表最后的位置就是最近最少使用的位置
        this.cacheMap = new LinkedHashMap<K, CacheObject<K, V>>
                (cacheSize + 1, 1f, true) {

            @Override
            protected boolean removeEldestEntry(
                    Map.Entry<K, CacheObject<K, V>> eldest) {

                return LRUCache.this.removeEldestEntry();
            }

        };
    }

    /**
     * @return 是否应该清除最后一个缓存对象
     */
    private boolean removeEldestEntry() {

        return cacheSize != 0 && size() > cacheSize;

    }

    /**
     * 只需要实现清除过期对象就可以了,linkedHashMap已经实现LRU
     */
    @Override
    protected int eliminateCache() {

        if (!isNeedClearExpiredObject()) {
            return 0;
        }

        Iterator<CacheObject<K, V>> iterator = cacheMap.values().iterator();
        int count = 0;
        while (iterator.hasNext()) {
            CacheObject<K, V> cacheObject = iterator.next();

            if (cacheObject.isExpired()) {
                iterator.remove();
                count++;
            }
        }

        return count;
    }

}

