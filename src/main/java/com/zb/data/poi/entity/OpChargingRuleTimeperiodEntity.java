package com.zb.data.poi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收费规则(按时段)
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-11-20 11:26:02
 */
@ApiModel
@Data
@TableName("t_op_charging_rule_timeperiod")
public class OpChargingRuleTimeperiodEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 收费规则按时段ID
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "收费规则按时段ID")
	private Integer id;
	/**
	 * 租户id
	 */
	@ApiModelProperty(name = "siteId",value = "租户id")
	private Integer siteId;
	/**
	 * 收费规则ID
	 */
	@ApiModelProperty(name = "chargingRuleId",value = "收费规则ID")
	private Integer chargingRuleId;
	/**
	 * 收费起始时间
	 */
	@ApiModelProperty(name = "startTime",value = "收费起始时间")
	private Date startTime;
	/**
	 * 收费结束时间
	 */
	@ApiModelProperty(name = "endTime",value = "收费结束时间")
	private Date endTime;

}
