package com.offcn.controller;


import com.offcn.pojo.Ordersetting;
import com.offcn.service.OrderSettringService;
import com.offcn.util.POIUtils;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/ordersetting")
public class OrdersettingController {

    @Reference
    private OrderSettringService orderSettringService;

    @PostMapping("/uploadTempleate")
    public Result uploadTempleate(MultipartFile excelFile)throws Exception{

        //{[2022/7/1,30],[20227/2,40]}
        List<String[]> stringList = POIUtils.readExcel(excelFile);

        return  orderSettringService.uploadTempleate(stringList);

    }
    @PostMapping("/getOrdersettingByDate")
    public Result getOrdersettingByDate(String date){
        return  orderSettringService.getOrdersettingByDate(date);
    }
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody Ordersetting ordersetting){

        return  orderSettringService.editNumberByDate(ordersetting);
    }

}

