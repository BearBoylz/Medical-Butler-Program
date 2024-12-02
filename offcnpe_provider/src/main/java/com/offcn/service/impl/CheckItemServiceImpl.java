package com.offcn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offcn.mapper.CheckgroupCheckitemMapper;
import com.offcn.mapper.CheckitemMapper;
import com.offcn.pojo.CheckgroupCheckitem;
import com.offcn.pojo.Checkitem;
import com.offcn.service.CheckItemService;
import com.offcn.util.MessageConstant;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckItemServiceImpl implements CheckItemService {

    @Resource
    private CheckitemMapper checkitemMapper;
    @Resource
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;

    @Override
    public PageResult findCheckItems(QueryPageBean queryPageBean) {

        Page<Checkitem> page=new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        QueryWrapper<Checkitem> queryWrapper=new QueryWrapper<>();
        if(queryPageBean.getQueryString()!=null && !"".equals(queryPageBean.getQueryString()))
        {
            queryWrapper.like("code",queryPageBean.getQueryString()).or().like("name",queryPageBean.getQueryString());
        }
        Page<Checkitem> checkitemPage = checkitemMapper.selectPage(page, queryWrapper);
        PageResult pageResult=new PageResult(checkitemPage.getTotal(),checkitemPage.getRecords());
        return pageResult;
    }

    @Override
    public Result saveCheckItem(Checkitem checkitem) {
        int rows=checkitemMapper.insert(checkitem);
        if(rows==1){
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        }
        return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
    }

    @Override
    public Result updateCheckItemById(Checkitem checkitem) {

        int rows=checkitemMapper.updateById(checkitem);
        if(rows==1){
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }
        return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
    }

    @Override
    public Result deleteCheckItemById(Integer id) {

        //删除检查组与检查项中间表中checkitem_id为id的记录
        QueryWrapper<CheckgroupCheckitem> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("checkitem_id",id);
        checkgroupCheckitemMapper.delete(queryWrapper);

        //再删除该检查项
        int rows=checkitemMapper.deleteById(id);
        if(rows==1){
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        }
        return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);

    }

    @Override
    public List<Checkitem> findAllCheckitems() {
        return checkitemMapper.selectList(null);
    }

    @Override
    public List<Integer> findAllCheckitemByCheckgroupId(int checkgroupId) {

        QueryWrapper<CheckgroupCheckitem> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("checkgroup_id",checkgroupId);
        List<CheckgroupCheckitem> checkgroupCheckitemList = checkgroupCheckitemMapper.selectList(queryWrapper);
        List<Integer> ids=new ArrayList<Integer>();
        for (CheckgroupCheckitem checkgroupCheckitem : checkgroupCheckitemList) {
            ids.add(checkgroupCheckitem.getCheckitemId());
        }
        return ids;
    }
}
