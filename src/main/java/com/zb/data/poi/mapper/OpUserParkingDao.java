package com.zb.data.poi.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.data.poi.entity.OpUserParkingEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 停车场&种子关联表
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-12-08 13:50:42
 */
@Mapper
public interface OpUserParkingDao extends BaseMapper<OpUserParkingEntity> {
	//批量添加种子用户关联表
    int batchAddUserParking(@Param("list") List<OpUserParkingEntity> list);
}
