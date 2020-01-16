package com.zb.data.poi.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author changchen
 * @create 2020-01-16 8:51
 */
@Repository
public interface FlowMapper {
    //根据serialNo删除流水
    int deleteFlowBySerialNo(@Param("serialNo") String serialNo);
}
