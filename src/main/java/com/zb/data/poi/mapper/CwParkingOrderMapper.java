package com.zb.data.poi.mapper;

import com.zb.data.poi.entity.CwParkingOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author changchen
 * @create 2020-03-24 8:45
 */
@Mapper
public interface CwParkingOrderMapper {
    //根据车牌号码 入场时间 停车场name 查询停车流水
    CwParkingOrder getParkingOrderByCondition(@Param("carPlateNo") String carPlateNo,@Param("inTime") String inTime,@Param("parkingName") String parkingName);

    List<CwParkingOrder> queryAllParkingOrder();

    //查找所有的交易流水号
    List<String> getAllPaymentSerialNo();
}
