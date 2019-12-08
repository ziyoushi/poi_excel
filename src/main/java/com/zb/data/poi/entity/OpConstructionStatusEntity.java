package com.zb.data.poi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 停车场建设状态
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-12-05 08:39:03
 */
@ApiModel
@Data
@TableName("t_op_construction_status")
public class OpConstructionStatusEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 停车场建设ID
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "停车场建设ID")
	private Integer id;
	/**
	 * 租户id
	 */
	@ApiModelProperty(name = "siteId",value = "租户id")
	private Integer siteId;
	/**
	 * 1.筹建期 2 建设期 3 收费核准期 4物价公示期 5宣传报道期 6开始收费期
	 */
	@ApiModelProperty(name = "status",value = "1.筹建期 2 建设期 3 收费核准期 4物价公示期 5宣传报道期 6开始收费期")
	private String status;
	/**
	 * 计划时间
	 */
	@ApiModelProperty(name = "planFinishDate",value = "计划时间")
	private String planFinishDate;
	/**
	 * 实际完成时间
	 */
	@ApiModelProperty(name = "actualFinishDate",value = "实际完成时间")
	private String actualFinishDate;
	/**
	 * 备注
	 */
	@ApiModelProperty(name = "comments",value = "备注")
	private String comments;
	/**
	 * 停车场建设状态id
	 */
	@ApiModelProperty(name = "constructionId",value = "停车场建设状态id")
	private Integer constructionId;
	/**
	 * 停车场ID
	 */
	@ApiModelProperty(name = "parkingId",value = "停车场ID")
	private Integer parkingId;

}
