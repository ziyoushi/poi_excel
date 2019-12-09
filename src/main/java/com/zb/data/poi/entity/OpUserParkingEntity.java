package com.zb.data.poi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 停车场&种子关联表
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-12-08 13:50:42
 */
@ApiModel
@Data
@TableName("t_op_user_parking")
public class OpUserParkingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 关联表主键Id
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "关联表主键Id")
	private Integer id;
	/**
	 * 白名单表Id
	 */
	@ApiModelProperty(name = "carPlateNo",value = "白名单表Id")
	private String carPlateNo;
	/**
	 * 是否是白名单或黑名单
	 */
	@ApiModelProperty(name = "isWhite",value = "是否是白名单或黑名单")
	private Integer isWhite;
	/**
	 * 停车场编号
	 */
	@ApiModelProperty(name = "parkingNo",value = "停车场编号")
	private String parkingNo;
	/**
	 * 开始日期
	 */
	@ApiModelProperty(name = "startDate",value = "开始日期")
	private String startDate;
	/**
	 * 结束日期
	 */
	@ApiModelProperty(name = "endDate",value = "结束日期")
	private String endDate;
	/**
	 * 001，未发送  002， 发送中 003，已发送
	 */
	@ApiModelProperty(name = "sendStatus",value = "001，未发送  002， 发送中 003，已发送")
	private String sendStatus;
	/**
	 * 
	 */
	@ApiModelProperty(name = "parkingId",value = "")
	private Integer parkingId;
	/**
	 * 1. 未下发 2 已下发 3 已撤销4 已修改
	 */
	@ApiModelProperty(name = "status",value = "1. 未下发 2 已下发 3 已撤销4 已修改")
	private String status;
	/**
	 * 
	 */
	@ApiModelProperty(name = "seedUserId",value = "")
	private Integer seedUserId;
	/**
	 * 
	 */
	@ApiModelProperty(name = "createDatetime",value = "")
	private Date createDatetime;
	/**
	 * 
	 */
	@ApiModelProperty(name = "updateDatetime",value = "")
	private Date updateDatetime;

}
