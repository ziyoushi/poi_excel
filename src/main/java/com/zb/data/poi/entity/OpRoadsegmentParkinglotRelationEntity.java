package com.zb.data.poi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 路段&车位关联
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-11-28 13:48:12
 */
@ApiModel
@Data
@TableName("t_op_roadsegment_parkinglot_relation")
public class OpRoadsegmentParkinglotRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 路段ID
	 */
	@TableId
	@ApiModelProperty(name = "roadSegmentId",value = "路段ID")
	private Integer roadSegmentId;
	/**
	 * 车位ID
	 */
	@ApiModelProperty(name = "parkinglotId",value = "车位ID")
	private Integer parkinglotId;

}
