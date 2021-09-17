package com.blond.jobs;

import com.blond.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;

/**
 *  定时任务，用于判断是否为新的一天，应用于清空日访问量
 *  @author Blonde
 * @program: blond_health
 * @create 2021-08-26 18:31
 */
public class resetDailyVisitJob {
    @Autowired
    private JedisPool jedisPool;

    public void resetDailyVisit() throws Exception {
        Jedis resource = jedisPool.getResource();
        try {
            String nowadays = resource.get("date");
            if (nowadays != null) {
                Date date = new Date();
                boolean sameDay = org.apache.commons.lang3.time.DateUtils.isSameDay(DateUtils.parseString2Date(nowadays), date);
                if (sameDay) { // 两次获取的日期还是同一天，更新存放于Redis中的日期
                    resource.set("date", DateUtils.parseDate2String(date));
                } else { //  两次获取的日期不是同一天，需要刷新Redis中用来记录日访问量的key
                    resource.set("DailyVisit", "0");
                    // 更新存放于Redis中的日期
                    resource.set("date", DateUtils.parseDate2String(date));
                }
            } else { // 程序第一次使用，或是存放日期的键值对丢失
                // 重新设置
                resource.set("date", DateUtils.parseDate2String(new Date()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            resource.close();
        }
    }
}
