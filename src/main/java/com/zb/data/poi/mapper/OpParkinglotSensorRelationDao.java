package com.zb.data.poi.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.data.poi.entity.OpParkinglotSensorRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-11-29 17:07:06
 */
@Mapper
public interface OpParkinglotSensorRelationDao extends BaseMapper<OpParkinglotSensorRelationEntity> {
	//批量插入senorRelation
    int batchAddSenorRelation(@Param("list") List<OpParkinglotSensorRelationEntity> list);
}
