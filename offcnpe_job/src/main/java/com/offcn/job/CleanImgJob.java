package com.offcn.job;

import com.offcn.util.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.Set;

@Component
public class CleanImgJob {

    @Resource
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0/10 * * * * ?")
    public void task(){

        Set<String> differenceList = redisTemplate.opsForSet().difference(RedisConstant.SETMEAL_PIC_UPLOAD, RedisConstant.SETMEAL_PIC_DB);
        if(differenceList!=null && differenceList.size()>0){
            for (String picName : differenceList) {
                File file=new File("D:\\workspace\\210\\day28\\upload\\images",picName);
                file.delete();
                redisTemplate.opsForSet().remove(RedisConstant.SETMEAL_PIC_UPLOAD,picName);
            }
        }
    }
}
