package com.offcn.service;

import com.offcn.pojo.Setmeal;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;

public interface SetmealService {
    PageResult addSetemeal(QueryPageBean queryPageBean);

    Result addSetmeal(Setmeal setmeal, Integer[] checkgroupIds);
}
