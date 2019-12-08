package com.zb.data.poi.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.data.poi.entity.OpConstructionStatusEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 停车场建设状态
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-12-05 08:39:03
 */
@Mapper
public interface OpConstructionStatusDao extends BaseMapper<OpConstructionStatusEntity> {
	//批量添加construction
    int batchAddConstruction(@Param("list") List<OpConstructionStatusEntity> list);


}
