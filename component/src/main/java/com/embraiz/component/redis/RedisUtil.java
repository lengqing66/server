package com.embraiz.component.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 返回key 剩余时间
     *
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    /**
     * 设置key 过期时间
     *
     * @param key
     * @param timeout
     */
    public void setExpireTime(String key, long timeout) {
        stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置key的value 自增一次+1
     *
     * @param key
     * @param data
     * @return
     */
    public long increase(String key, long data) {
        return stringRedisTemplate.opsForValue().increment(key, data);
    }

    /**
     * 设置key的value 自减一次-1
     *
     * @param key
     * @param data
     * @return
     */
    public long decrease(String key, long data) {
        return stringRedisTemplate.opsForValue().decrement(key, data);
    }

    /**
     * 删除key
     *
     * @param key
     */
    public void deleteKey(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 根据指定的key前缀 + "*"（* 号一定要有），查询出所有匹配到的key，从而进行删除
     *
     * @param key
     */
    public void deleteKeys(String key) {
        if ("".equals(key) || null == key) return;
        Set<String> keys = stringRedisTemplate.keys(key + "*");
        stringRedisTemplate.delete(keys);
    }

    /**
     * 删除全部key
     */
    public void deleteAll() {
        Set<String> keys = stringRedisTemplate.keys("*");
        stringRedisTemplate.delete(keys);
    }

    /**
     * Set key and value
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * Set key and value, expire time(秒)
     *
     * @param key
     * @param value
     * @param timeout
     */
    public void set(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * Get key and value
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 设置HASH，适合用于存储对象
     *
     * @param hashKey 可以理解为表名
     * @param key     可以理解为列
     * @param value   可以理解为值
     */
    public void setHash(String hashKey, String key, Object value) {
        stringRedisTemplate.opsForHash().put(hashKey, key, value);
    }

    /**
     * 获取Hash 里面知道的KEY 的 VALUE
     *
     * @param hashKey
     * @param key
     * @return
     */
    public Object getHash(String hashKey, String key) {
        return stringRedisTemplate.opsForHash().get(hashKey, key);
    }

    /**
     * 获取指定hashKey 的全部数据
     *
     * @param hashKey
     * @return
     */
    public Map<Object, Object> getHashAll(String hashKey) {
        return stringRedisTemplate.opsForHash().entries(hashKey);
    }

    /**
     * 删除hashkey 里面的一个或多个key
     *
     * @param hashKey
     * @param key
     */
    public void deleteHash(String hashKey, Object... key) {
        stringRedisTemplate.opsForHash().delete(hashKey, key);
    }

    /**
     * 设置List 类型数据,将一个value 插入到列表key 的表头（最左边）
     *
     * @param key
     * @param value
     * @return List的长度
     */
    public long setLeftList(String key, String value) {
        return stringRedisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 设置List 类型数据,将一个value 插入到列表key 的表尾（最右边）
     *
     * @param key
     * @param value
     * @return
     */
    public long setRightList(String key, String value) {
        return stringRedisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 移出最后的元素并获取列表的第一个元素
     *
     * @param key
     * @return
     */
    public String leftPop(String key) {
        return stringRedisTemplate.opsForList().leftPop(key);
    }

}
