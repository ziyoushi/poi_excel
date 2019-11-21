package com.zb.data.poi.controller;

import com.zb.data.poi.entity.DemoData;
import com.zb.data.poi.mapper.DemoDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author changchen
 * @create 2019-11-20 0:10
 */
@RestController
@RequestMapping("/data")
public class DemoController {

    @Autowired
    private DemoDataMapper demoDataMapper;
    @GetMapping("/list")
    public List<DemoData> list(){
       return demoDataMapper.queryAll();
    }


}
