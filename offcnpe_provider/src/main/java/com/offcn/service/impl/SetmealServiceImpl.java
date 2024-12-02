package com.offcn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offcn.mapper.SetmealCheckgroupMapper;
import com.offcn.mapper.SetmealMapper;
import com.offcn.pojo.Setmeal;
import com.offcn.pojo.SetmealCheckgroup;
import com.offcn.service.SetmealService;
import com.offcn.util.*;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private SetmealMapper setmealMapper;
    @Resource
    private SetmealCheckgroupMapper setmealCheckgroupMapper;

    @Override
    public PageResult addSetemeal(QueryPageBean queryPageBean) {
        Page<Setmeal> page=new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        QueryWrapper<Setmeal> queryWrapper=new QueryWrapper<>();
        if(queryPageBean.getQueryString()!=null && !"".equals(queryPageBean.getQueryString())){
            queryWrapper.like("name",queryPageBean.getQueryString())
                    .or().like("code",queryPageBean.getQueryString())
                    .or().like("helpCode",queryPageBean.getQueryString());
        }
        Page<Setmeal> setmealPage = setmealMapper.selectPage(page, queryWrapper);
        PageResult pageResult=new PageResult(setmealPage.getTotal(),setmealPage.getRecords());
        return pageResult;
    }

    @Override
    public Result addSetmeal(Setmeal setmeal, Integer[] checkgroupIds) {

        //保存套餐
        int rows = setmealMapper.insert(setmeal);
        //保存套餐所有的检查组
        if(checkgroupIds!=null && checkgroupIds.length>0){
            for (Integer checkgroupId : checkgroupIds) {
                SetmealCheckgroup setmealCheckgroup=new SetmealCheckgroup();
                setmealCheckgroup.setSetmealId(setmeal.getId());
                setmealCheckgroup.setCheckgroupId(checkgroupId);
                setmealCheckgroupMapper.insert(setmealCheckgroup);
            }
        }

        if(rows==1){
            //把保存到数据中的图片的文件名放到redis
            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_DB,setmeal.getImg());
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        }
        return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
    }
}
