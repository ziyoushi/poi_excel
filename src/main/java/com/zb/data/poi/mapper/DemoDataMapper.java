package com.zb.data.poi.mapper;

import com.zb.data.poi.entity.DemoData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author changchen
 * @create 2019-11-19 23:01
 */
@Repository
public interface DemoDataMapper {

    List<DemoData> queryAll();

    //批量添加demoData
    int batchAddDemoData(@Param("list") List<DemoData> list);
}
