package com.zb.data.poi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 停车场&计费规则关联
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-11-20 09:27:14
 */
@ApiModel
@Data
@TableName("t_op_parking_charging_rule_relation")
public class OpParkingChargingRuleRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 停车场ID
	 */
	@TableId
	@ApiModelProperty(name = "parkingId",value = "停车场ID")
	private Integer parkingId;
	/**
	 * 收费规则ID
	 */
	@ApiModelProperty(name = "chargingRuleId",value = "收费规则ID")
	private Integer chargingRuleId;

}
