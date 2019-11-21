package com.zb.data.poi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 收费标准按时间段
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-11-20 15:16:17
 */
@ApiModel
@Data
@TableName("t_op_charging_rule_btp_std")
public class OpChargingRuleBtpStdEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 按时间段收费标准ID
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "按时间段收费标准ID")
	private Integer id;
	/**
	 * 收费规则按时段ID
	 */
	@ApiModelProperty(name = "chargingRuleBtpId",value = "收费规则按时段ID")
	private Integer chargingRuleBtpId;
	/**
	 * 租户id
	 */
	@ApiModelProperty(name = "siteId",value = "租户id")
	private Integer siteId;
	/**
	 * 阶段序号
	 */
	@ApiModelProperty(name = "periodNo",value = "阶段序号")
	private Integer periodNo;
	/**
	 * 阶段名称
	 */
	@ApiModelProperty(name = "periodName",value = "阶段名称")
	private String periodName;
	/**
	 * 1. 时间段 2间隔
	 */
	@ApiModelProperty(name = "type",value = "1. 时间段 2间隔")
	private String type;
	/**
	 * 数量
	 */
	@ApiModelProperty(name = "count",value = "数量")
	private Integer count;
	/**
	 * 收费金额
	 */
	@ApiModelProperty(name = "timeChargingAmount",value = "收费金额")
	private BigDecimal timeChargingAmount;
	/**
	 * 1. 小时 2. 半小时  3. 一刻钟 
	 */
	@ApiModelProperty(name = "timeIntervalType",value = "1. 小时 2. 半小时  3. 一刻钟 ")
	private String timeIntervalType;

}
