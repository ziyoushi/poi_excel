package com.zb.data.poi.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.data.poi.entity.OpRoadsegmentParkinglotRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 路段&车位关联
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-11-28 13:48:12
 */
@Mapper
public interface OpRoadsegmentParkinglotRelationDao extends BaseMapper<OpRoadsegmentParkinglotRelationEntity> {
	//批量插入road-lot-relation
    int batchAddRoadParkingLotRelation(@Param("list") List<OpRoadsegmentParkinglotRelationEntity> list);
}
