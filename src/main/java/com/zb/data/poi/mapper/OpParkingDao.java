package com.zb.data.poi.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.data.poi.entity.OpParkingEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 停车场信息
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-12-02 20:47:31
 */
@Mapper
public interface OpParkingDao extends BaseMapper<OpParkingEntity> {
    //根据停车场名 修改经纬度
    int updateParking(@Param("coordinate") String coordinate,@Param("name") String name);

    int batchUpdateParking(@Param("list") List<OpParkingEntity> list);

    int getParkingIdByName(@Param("name") String name);

}
