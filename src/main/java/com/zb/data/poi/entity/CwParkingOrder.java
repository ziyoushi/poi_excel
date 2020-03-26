package com.zb.data.poi.entity;

import lombok.Data;

/**
 * @author changchen
 * @create 2020-03-19 13:12
 */
@Data
public class CwParkingOrder {
    //订单号
    private String orderNo;
    //金额
    private String amount;
    //车牌号
    private String carPlateNo;
    //入场时间
    private String inTime;
    //出场时间
    private String outTime;
    //停车场name
    private String parkingName;
    //商户流水
    private String businessNo;
    //交易时间
    private String paymentTime;
}
