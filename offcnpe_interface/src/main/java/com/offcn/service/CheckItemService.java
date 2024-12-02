package com.offcn.service;

import com.offcn.pojo.Checkitem;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;

import java.util.List;

public interface CheckItemService {

    /**
     * 分页查询检查项
     * @param queryPageBean
     * @return
     */
    public PageResult findCheckItems(QueryPageBean queryPageBean);

    Result saveCheckItem(Checkitem checkitem);

    Result updateCheckItemById(Checkitem checkitem);

    Result deleteCheckItemById(Integer id);

    List<Checkitem> findAllCheckitems();

    List<Integer> findAllCheckitemByCheckgroupId(int checkgroupId);
}
