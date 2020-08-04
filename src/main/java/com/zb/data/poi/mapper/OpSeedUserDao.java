package com.zb.data.poi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.data.poi.entity.OpSeedUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 种子用户
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-12-08 13:50:42
 */
@Mapper
public interface OpSeedUserDao extends BaseMapper<OpSeedUserEntity> {
	//批量添加种子用户表
    int batchAddSeedUser(@Param("list") List<OpSeedUserEntity> list);

    int querySeedUserIdByCarPlateNo(@Param("carPlateNo") String carPlateNo);
}
