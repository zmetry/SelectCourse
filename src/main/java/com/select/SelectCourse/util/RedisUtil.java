package com.select.SelectCourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 判断key是否过期
     * @param key 键
     * @return 时间（S）返回0表示永久有效
     */
    public boolean expire(String key,long time){
        try{
            if(time > 0){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 判断key是否存在
     * @param key 键
     * @return
     */
    public boolean hasKey(String key){
        try{
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存获取
     * @param key
     * @return
     */
    public Object get(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 缓存存入
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key,Object value){
        try{
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 存入缓存，并设置时间，不设置为无穷
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key,Object value,long time){
        try{
           if(time > 0){
               redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
           }else{
               set(key,value);
           }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除指定的键
     * @param key
     * @return
     */
    public boolean delete(String key){
        return redisTemplate.delete(key);
    }

    /**
     *  flush db操作
     */
    public Map<String, Object> clear() {
        Map<String, Object> map = new HashMap<>();
        try {
            // 获取所有key
            Set<String> keys = redisTemplate.keys("*");
            assert keys != null;
            // 迭代
            Iterator<String> it1 = keys.iterator();
            while (it1.hasNext()) {
                // 循环删除
                redisTemplate.delete(it1.next());
            }
            map.put("code", 1);
            map.put("msg", "清理全局缓存成功");
            return map;
        } catch (Exception e) {
            map.put("code", -1);
            map.put("msg", "清理全局缓存失败");
            return map;
        }
    }

}
