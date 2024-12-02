package com.offcn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.offcn.mapper.OrdersettingMapper;
import com.offcn.pojo.Ordersetting;
import com.offcn.service.OrderSettringService;
import com.offcn.util.MessageConstant;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderSettringServiceImpl implements OrderSettringService {

    @Resource
    private OrdersettingMapper ordersettingMapper;

    @Override
    public Result uploadTempleate(List<String[]> stringList) {

        int count=0;
        if(stringList!=null && stringList.size()>0){
            for (String[] strings : stringList) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate date = LocalDate.parse(strings[0], dateTimeFormatter);
                int number=Integer.parseInt(strings[1]);
                Ordersetting orderSettring=new Ordersetting();
                orderSettring.setOrderdate(date);
                orderSettring.setReservations(0);
                orderSettring.setNumber(number);
                ordersettingMapper.insert(orderSettring);
                count++;
            }
        }
        if(stringList.size()==count && count!=0){
            return new Result(true, "预约数据导入成功");
        }
        return new Result(false, "预约数据导入失败 ");
    }

    @Override
    public Result getOrdersettingByDate(String date) {

        // 2022-06    2022-07-01    2022-07-31
        String startDate=date+"-1";
        String endDate=date+"-31";
        QueryWrapper<Ordersetting> queryWrapper=new QueryWrapper<>();
        queryWrapper.between("orderDate",startDate,endDate);
        List<Ordersetting> orderSettringList = ordersettingMapper.selectList(queryWrapper);
        List<Map> mapList=new ArrayList<>();
        for (Ordersetting ordersetting : orderSettringList) {
            Map map=new HashMap();
            map.put("date",ordersetting.getOrderdate().getDayOfMonth());
            map.put("number",ordersetting.getNumber());
            map.put("reservations",ordersetting.getReservations());
            mapList.add(map);
        }

        return new Result(true,"查询成功",mapList);
    }

    @Override
    public Result editNumberByDate(Ordersetting ordersetting) {

        QueryWrapper<Ordersetting> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("orderDate",ordersetting.getOrderdate());
        Ordersetting oldOrdersetting = ordersettingMapper.selectOne(queryWrapper);
        oldOrdersetting.setNumber(ordersetting.getNumber());
        int rows=ordersettingMapper.updateById(oldOrdersetting);
        if(rows==1){
            return new Result(true,"修改成功");
        }
        return new Result(false,"修改失败");
    }
}
