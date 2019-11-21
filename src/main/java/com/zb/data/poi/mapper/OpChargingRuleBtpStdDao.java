package com.zb.data.poi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.data.poi.entity.OpChargingRuleBtpStdEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收费标准按时间段
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-11-20 15:16:17
 */
@Repository
public interface OpChargingRuleBtpStdDao extends BaseMapper<OpChargingRuleBtpStdEntity> {
	//批量更新btp
    int batchAddBtp(@Param("list") List<OpChargingRuleBtpStdEntity> list);
}
