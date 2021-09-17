package com.blond.jobs;

import com.blond.constant.RedisConstant;
import com.blond.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * 自定义jobs，实现定时清理垃圾图片
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-05 1:55
 */
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){
        Jedis resource = jedisPool.getResource();
        // 根据Redis库中的上传图片库SETMEAL_PIC_RESOURCES和实际上传图片库SETMEAL_PIC_DB_RESOURCES
        // 进行差值计算，获得垃圾图片合集
        try {
            Set<String> garbageSet = resource.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
            if (garbageSet != null) {
                for (String gbName : garbageSet) {
                    // 调用七牛工具类清理存储在七牛云服务器上的垃圾图片
                    QiniuUtils.deleteFileFromQiniu(gbName);
                    // 从Redis库SETMEAL_PIC_RESOURCES中删除垃圾图片名称
                    jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, gbName);
                    System.out.println("自定义任务执行-->" + gbName + "已清理");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            resource.close();
        }
    }




}
