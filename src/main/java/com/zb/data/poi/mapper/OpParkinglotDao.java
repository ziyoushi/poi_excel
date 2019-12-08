package com.zb.data.poi.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.data.poi.entity.OpParkinglotEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 停车场停车位
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-11-29 14:09:43
 */
@Mapper
public interface OpParkinglotDao extends BaseMapper<OpParkinglotEntity> {

    //批量插入车位
    int batchAddParkingLot(@Param("list") List<OpParkinglotEntity> list);

    //查询 parkingLotId
    int getParkingLotIdByCondition(@Param("code") String code,@Param("roadName") String roadName,@Param("parkingName") String parkingName);
}
