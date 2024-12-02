package com.offcn.service;

import com.offcn.pojo.Ordersetting;
import com.offcn.util.Result;

import java.util.List;

public interface OrderSettringService {
    Result uploadTempleate(List<String[]> stringList);

    Result getOrdersettingByDate(String date);

    Result editNumberByDate(Ordersetting ordersetting);
}
