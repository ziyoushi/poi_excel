package com.zb.data.poi.mapper;

import com.zb.data.poi.entity.PictureHistoryEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author changchen
 * @create 2020-01-16 8:42
 */
@Repository
public interface PictureHistoryMapper {
    //根据停车流水查询
    PictureHistoryEntity getHistoryBySerialNo(@Param("serialNo") String serialNo);
}
