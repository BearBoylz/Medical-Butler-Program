package com.offcn.controller;


import com.offcn.pojo.Setmeal;
import com.offcn.service.SetmealService;
import com.offcn.util.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2022-06-22
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Resource
    private RedisTemplate redisTemplate;
    @Reference
    private SetmealService setmealService;

    @PostMapping("/addSetemeal")
    public PageResult addSetemeal(@RequestBody QueryPageBean queryPageBean){

        return setmealService.addSetemeal(queryPageBean);
    }

    @PostMapping("/uploadImage")
    public Result uploadImage(MultipartFile imgFile){
        try {
            String originalFileName=imgFile.getOriginalFilename();
            String saveDir="D:\\workspace\\210\\day28\\upload\\images";
            File saveDirFile=new File(saveDir);
            if(saveDirFile.exists()) saveDirFile.mkdirs();
            String newFileName= UUID.randomUUID().toString()+"&"+originalFileName;
            File saveFile=new File(saveDirFile,newFileName);
            imgFile.transferTo(saveFile);
            //把图片放到redis中
            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_UPLOAD,newFileName);
            return new Result(true, MessageConstant.UPLOAD_SUCCESS,newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }


    @PostMapping("/addSetmeal")
    public Result addSetmeal(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){

      return   setmealService.addSetmeal(setmeal,checkgroupIds);
    }
}

