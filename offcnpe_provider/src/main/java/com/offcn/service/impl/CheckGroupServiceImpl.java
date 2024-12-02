package com.offcn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offcn.mapper.CheckgroupCheckitemMapper;
import com.offcn.mapper.CheckgroupMapper;
import com.offcn.mapper.SetmealCheckgroupMapper;
import com.offcn.pojo.Checkgroup;
import com.offcn.pojo.CheckgroupCheckitem;
import com.offcn.pojo.SetmealCheckgroup;
import com.offcn.service.CheckGroupService;
import com.offcn.util.MessageConstant;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CheckGroupServiceImpl implements CheckGroupService {

    @Resource
    private CheckgroupMapper checkgroupMapper;
    @Resource
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;
    @Resource
    private SetmealCheckgroupMapper setmealCheckgroupMapper;

    @Override
    public PageResult findCheckGroups(QueryPageBean queryPageBean) {

        Page<Checkgroup> page=new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        QueryWrapper<Checkgroup> queryWrapper=new QueryWrapper<>();
        if(queryPageBean.getQueryString()!=null && !"".equals(queryPageBean.getQueryString())){
            queryWrapper.like("code",queryPageBean.getQueryString())
                    .or().like("name",queryPageBean.getQueryString())
                    .or().like("helpCode",queryPageBean.getQueryString());
        }
        Page<Checkgroup> checkgroupPage = checkgroupMapper.selectPage(page, queryWrapper);
        PageResult pageResult=new PageResult(checkgroupPage.getTotal(),checkgroupPage.getRecords());
        return pageResult;
    }

    @Override
    public Result addCheckGroup(Checkgroup checkgroup, Integer[] checkitemIds) {

        //保存检查组
       int rows= checkgroupMapper.insert(checkgroup);

        //把检查组与检查项之后关系保存到中间表
        if(checkitemIds!=null && checkitemIds.length>0){
            for (Integer checkitemId : checkitemIds) {
                CheckgroupCheckitem checkgroupCheckitem=new CheckgroupCheckitem();
                checkgroupCheckitem.setCheckgroupId(checkgroup.getId());
                checkgroupCheckitem.setCheckitemId(checkitemId);
                checkgroupCheckitemMapper.insert(checkgroupCheckitem);
            }

        }
        if(rows==1){
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        }
        return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
    }

    @Override
    public Result updateCheckGroup(Checkgroup checkgroup, Integer[] checkitemIds) {

        //更新检查组
        int rows=checkgroupMapper.updateById(checkgroup);
        //根据检查组的id删除中间表数据
        if(checkitemIds!=null && checkitemIds.length>0){
            for (Integer checkitemId : checkitemIds) {
                QueryWrapper<CheckgroupCheckitem> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("checkgroup_id",checkgroup.getId());
                checkgroupCheckitemMapper.delete(queryWrapper);
            }
        }
        //重新添加
        //把检查组与检查项之后关系保存到中间表
        if(checkitemIds!=null && checkitemIds.length>0){
            for (Integer checkitemId : checkitemIds) {
                CheckgroupCheckitem checkgroupCheckitem=new CheckgroupCheckitem();
                checkgroupCheckitem.setCheckgroupId(checkgroup.getId());
                checkgroupCheckitem.setCheckitemId(checkitemId);
                checkgroupCheckitemMapper.insert(checkgroupCheckitem);
            }

        }
        if(rows==1){
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        }
        return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
    }

    @Override
    public Result deleteCheckGroupById(int id) {

        //删除检查组与检查项之间中间表数据
        QueryWrapper<CheckgroupCheckitem> checkgroupCheckitemQueryWrapper=new QueryWrapper<>();
        checkgroupCheckitemQueryWrapper.eq("checkgroup_id",id);
        checkgroupCheckitemMapper.delete(checkgroupCheckitemQueryWrapper);

        //套餐与检查组之间的中间表数据
        QueryWrapper<SetmealCheckgroup> setmealCheckgroupQueryWrapper=new QueryWrapper<>();
        setmealCheckgroupQueryWrapper.eq("checkgroup_id",id);
        setmealCheckgroupMapper.delete(setmealCheckgroupQueryWrapper);

        //删除检查组
        int rows=checkgroupMapper.deleteById(id);
        if(rows==1){
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        }
        return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
    }

    @Override
    public List<Checkgroup> findAllCheckGroups() {
        return checkgroupMapper.selectList(null);
    }
}
