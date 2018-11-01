package com.zheng.configurer.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.UUID;

/**
 * Created by zhenghui on 2018/11/1.
 * 分布式锁简单实现
 */
public class DistributedLock {

    private final JedisPool jedisPool;

    public DistributedLock(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String lockWithTimeout(String lockName, long acquireTimeout, long timeout) {
        String identifier = UUID.randomUUID().toString();
        String lockKey = "lock:" + lockName;
        int lockExpire = (int) (timeout / 1000);
        long end = System.currentTimeMillis() + acquireTimeout;
        String retIdentifier = null; //用于释放锁时间确认

        Jedis conn = null;
        try {
            conn = jedisPool.getResource();
            while (System.currentTimeMillis() < end) {
                if (conn.setnx(lockKey, identifier) == 1) {
                    conn.expire(lockKey, lockExpire);
                    // 返回value值，用于释放锁时间确认
                    retIdentifier = identifier;
                    System.out.println("加锁成功->" + retIdentifier);
                    return retIdentifier;
                }

                // 返回-1代表key没有设置超时时间，为key设置一个超时时间
                if (conn.ttl(lockKey) == -1) {
                    conn.expire(lockKey, lockExpire);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return retIdentifier;
    }

    public boolean releaseLock(String lockName, String identifier) {
        Jedis conn = null;
        String lockKey = "lock:" + lockName;
        boolean retFlag = false;
        try {
            conn = jedisPool.getResource();
            while (true) {
                // 监视lock，准备开始事务
                conn.watch(lockKey);
                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if (identifier.equals(conn.get(lockKey))) {
                    Transaction transaction = conn.multi();
                    transaction.del(lockKey);
                    List<Object> results = transaction.exec();
                    if (results == null) {
                        continue;
                    }
                    retFlag = true;
                }
                conn.unwatch();
                break;
            }
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return retFlag;
    }

    public static void main(String[] args) {
        String lockName = "redis1";//锁的key
        long timeout = 1000;//锁的超时时间
        long acquireTimeout = 1000;//获取超时时间
        String retIdentifier = null;
        DistributedLock distributedLock = new DistributedLock(new JedisPool());
        retIdentifier = distributedLock.lockWithTimeout(lockName, acquireTimeout, timeout);
        boolean retFlag = distributedLock.releaseLock(lockName, retIdentifier);
        System.out.println("释放锁->" + retFlag);
    }
}
