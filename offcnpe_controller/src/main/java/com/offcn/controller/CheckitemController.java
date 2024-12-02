package com.offcn.controller;


import com.offcn.pojo.Checkitem;
import com.offcn.service.CheckItemService;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/checkitem")
public class CheckitemController {

    @Reference
    private CheckItemService checkItemService;

    @RequestMapping("/findCheckItems")
    public PageResult findCheckItems(@RequestBody QueryPageBean queryPageBean){

       return checkItemService.findCheckItems(queryPageBean);
    }

    @PostMapping("/saveCheckItem")
    public Result saveCheckItem(@RequestBody Checkitem checkitem){

       return  checkItemService.saveCheckItem(checkitem);
    }
    @PostMapping("/updateCheckItemById")
    public Result updateCheckItemById(@RequestBody Checkitem checkitem){

      return   checkItemService.updateCheckItemById(checkitem);
    }
    @PostMapping("/deleteCheckItemById")
    public Result deleteCheckItemById(Integer id){
        return   checkItemService.deleteCheckItemById(id);
    }

    @PostMapping("/findAllCheckitems")
    public List<Checkitem> findAllCheckitems(){
        return   checkItemService.findAllCheckitems();
    }

    @PostMapping("/findAllCheckitemByCheckgroupId")
    public List<Integer> findAllCheckitemByCheckgroupId(int checkgroupId){
        return   checkItemService.findAllCheckitemByCheckgroupId(checkgroupId);
    }

}

