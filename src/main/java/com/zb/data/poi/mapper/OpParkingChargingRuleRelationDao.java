package com.zb.data.poi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.data.poi.entity.OpParkingChargingRuleRelationEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 停车场&计费规则关联
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-11-20 09:27:14
 */
@Repository
public interface OpParkingChargingRuleRelationDao extends BaseMapper<OpParkingChargingRuleRelationEntity> {
	//批量添加parking-rule
    int batchAddRuleRelation(@Param("list") List<OpParkingChargingRuleRelationEntity> list);

    //根据计费规则名查询id
    int getRuleIdByName(@Param("name") String name);


    //根据停车场名 查询id
    int getParkingIdByName(@Param("name")String name);


}
