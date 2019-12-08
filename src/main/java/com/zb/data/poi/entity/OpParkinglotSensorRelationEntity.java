package com.zb.data.poi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-11-29 17:07:06
 */
@ApiModel
@Data
@TableName("t_op_parkinglot_sensor_relation")
public class OpParkinglotSensorRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 车位ID
	 */
	@TableId
	@ApiModelProperty(name = "parkinglotId",value = "车位ID")
	private Integer parkinglotId;
	/**
	 * 地磁编号
	 */
	@ApiModelProperty(name = "sensorCode",value = "地磁编号")
	private String sensorCode;

}
