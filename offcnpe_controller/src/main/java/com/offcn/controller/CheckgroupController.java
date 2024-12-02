package com.offcn.controller;


import com.offcn.pojo.Checkgroup;
import com.offcn.service.CheckGroupService;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2022-06-22
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckgroupController {

    @Reference
    private CheckGroupService checkGroupService;

    @RequestMapping("/findCheckGroups")
    public PageResult findCheckGroups(@RequestBody QueryPageBean queryPageBean){

       return  checkGroupService.findCheckGroups(queryPageBean);
    }
    @RequestMapping("/addCheckGroup")
    public Result addCheckGroup(@RequestBody Checkgroup checkgroup,Integer[] checkitemIds){
        return  checkGroupService.addCheckGroup(checkgroup,checkitemIds);
    }
    @RequestMapping("/updateCheckGroup")
    public Result updateCheckGroup(@RequestBody Checkgroup checkgroup,Integer[] checkitemIds){
        return  checkGroupService.updateCheckGroup(checkgroup,checkitemIds);
    }
    @RequestMapping("/deleteCheckGroupById")
    public Result deleteCheckGroupById(int id){
        return  checkGroupService.deleteCheckGroupById(id);
    }

    @RequestMapping("/findAllCheckGroups")
    public List<Checkgroup> findAllCheckGroups(){
        return  checkGroupService.findAllCheckGroups();
    }
}

