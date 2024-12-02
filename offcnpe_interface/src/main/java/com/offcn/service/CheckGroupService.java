package com.offcn.service;

import com.offcn.pojo.Checkgroup;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;

import java.util.List;

public interface CheckGroupService {
    PageResult findCheckGroups(QueryPageBean queryPageBean);

    Result addCheckGroup(Checkgroup checkgroup, Integer[] checkitemIds);

    Result updateCheckGroup(Checkgroup checkgroup, Integer[] checkitemIds);

    Result deleteCheckGroupById(int id);

    List<Checkgroup> findAllCheckGroups();
}
