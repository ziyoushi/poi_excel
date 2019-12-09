package com.zb.data.poi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 种子用户
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-12-08 13:50:42
 */
@ApiModel
@Data
@TableName("t_op_seed_user")
public class OpSeedUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 种子用户ID
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "种子用户ID")
	private Integer id;
	/**
	 * 租户id
	 */
	@ApiModelProperty(name = "siteId",value = "租户id")
	private Integer siteId;
	/**
	 * 车主姓名
	 */
	@ApiModelProperty(name = "carownerName",value = "车主姓名")
	private String carownerName;
	/**
	 * 车主身份证号
	 */
	@ApiModelProperty(name = "idCard",value = "车主身份证号")
	private String idCard;
	/**
	 * 车牌颜色
	 */
	@ApiModelProperty(name = "carPlateColor",value = "车牌颜色")
	private String carPlateColor;
	/**
	 * 车牌号
	 */
	@ApiModelProperty(name = "carPlateNo",value = "车牌号")
	private String carPlateNo;
	/**
	 * 联系电话
	 */
	@ApiModelProperty(name = "contactMobile",value = "联系电话")
	private String contactMobile;
	/**
	 * 联系地址
	 */
	@ApiModelProperty(name = "contactAdrress",value = "联系地址")
	private String contactAdrress;
	/**
	 * 种子用户类型 1_月卡 2_内部员工套餐 3 VIP 客户
            
	 */
	@ApiModelProperty(name = "seedUserType",value = "种子用户类型 1_月卡 2_内部员工套餐 3 VIP 客户")
	private String seedUserType;
	/**
	 * 有效无效
	 */
	@ApiModelProperty(name = "invalidateStatus",value = "有效无效")
	private String invalidateStatus;
	/**
	 * 备注
	 */
	@ApiModelProperty(name = "comments",value = "备注")
	private String comments;

}
