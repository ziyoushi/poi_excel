package com.zb.data.poi.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author changchen
 * @create 2020-01-16 8:37
 */


@Data
public class PictureHistoryEntity {
    private String parkingSerialNo;
    private String picImgUrl;
    private String carPlateNo;
    private Date picDatetime;
}
