package com.zb.data.poi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.data.poi.entity.OpChargingRuleEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 停车场收费规则
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-11-20 09:19:27
 */
@Repository
public interface OpChargingRuleDao extends BaseMapper<OpChargingRuleEntity> {
    //批量更新计费规则
    int batchAddCharging(@Param("list") List<OpChargingRuleEntity> list);
	
}
