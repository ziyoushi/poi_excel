package com.zb.data.poi.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 停车场收费规则
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-11-20 09:19:27
 */
@ApiModel
@Data
@TableName("t_op_charging_rule")
public class OpChargingRuleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 收费规则ID
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "收费规则ID")
	private Integer id;
	/**
	 * 租户id
	 */
	@ApiModelProperty(name = "siteId",value = "租户id")
	private Integer siteId;
	/**
	 * 规则版本号
	 */
	@ApiModelProperty(name = "version",value = "规则版本号")
	private String version;
	/**
	 * 收费规则编号
	 */
	@ApiModelProperty(name = "code",value = "收费规则编号")
	private String code;
	/**
	 * 收费规则名称
	 */
	@ApiModelProperty(name = "name",value = "收费规则名称")
	private String name;
	/**
	 * 车牌颜色
	 */
	@ApiModelProperty(name = "carPlateColor",value = "车牌颜色")
	private String carPlateColor;
	/**
	 * 收费上限（元）
	 */
	@ApiModelProperty(name = "chargingUpperLimit",value = "收费上限（元）")
	private BigDecimal chargingUpperLimit;
	/**
	 * 规则描述
	 */
	@ApiModelProperty(name = "comments",value = "规则描述")
	private String comments;
	/**
	 * 1, 立即生效 2. 延迟生效
	 */
	@ApiModelProperty(name = "effectiveType",value = "1, 立即生效 2. 延迟生效")
	private String effectiveType;
	/**
	 * 生效时间
	 */
	@ApiModelProperty(name = "effectiveTime",value = "生效时间")
	private Date effectiveTime;
	/**
	 * 1. 新增 2 .更新 3.删除
	 */
	@ApiModelProperty(name = "status",value = "1. 新增 2 .更新 3.删除")
	private String status;
	/**
	 * 001 待生效,002,已生效,003 已失效
	 */
	@ApiModelProperty(name = "effectiveStatus",value = "001 待生效,002,已生效,003 已失效")
	private String effectiveStatus;
	/**
	 * 001，草稿 ，002 发布 ,003 撤销
	 */
	@ApiModelProperty(name = "releaseStatus",value = "001，草稿 ，002 发布 ,003 撤销")
	private String releaseStatus;
	/**
	 * 发布失败原因
	 */
	@ApiModelProperty(name = "releaseErrorReason",value = "发布失败原因")
	private String releaseErrorReason;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(name = "createDate",value = "创建时间")
	private Date createDate;
	/**
	 * 创建人
	 */
	@ApiModelProperty(name = "createUser",value = "创建人")
	private String createUser;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(name = "updateDate",value = "更新时间")
	private Date updateDate;
	/**
	 * 更新人
	 */
	@ApiModelProperty(name = "updateUser",value = "更新人")
	private String updateUser;

}
