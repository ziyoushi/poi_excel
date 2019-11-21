package com.zb.data.poi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.data.poi.entity.OpChargingRuleTimeperiodEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收费规则(按时段)
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-11-20 11:26:02
 */
@Repository
public interface OpChargingRuleTimeperiodDao extends BaseMapper<OpChargingRuleTimeperiodEntity> {
	//批量插入
    int batchAddChargingTime(@Param("list") List<OpChargingRuleTimeperiodEntity> list);
}
