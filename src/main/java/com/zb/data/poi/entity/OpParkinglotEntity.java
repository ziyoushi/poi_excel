package com.zb.data.poi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 停车场停车位
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-11-29 14:09:43
 */
@ApiModel
@Data
@TableName("t_op_parkinglot")
public class OpParkinglotEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 车位ID
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "车位ID")
	private Integer id;
	/**
	 * 租户ID
	 */
	@ApiModelProperty(name = "siteId",value = "租户ID")
	private Integer siteId;
	/**
	 * 停车场ID
	 */
	@ApiModelProperty(name = "parkingId",value = "停车场ID")
	private Integer parkingId;
	/**
	 * 路段ID
	 */
	@ApiModelProperty(name = "roadsegmentId",value = "路段ID")
	private Integer roadsegmentId;
	/**
	 * 车位编号
	 */
	@ApiModelProperty(name = "code",value = "车位编号")
	private String code;
	/**
	 * 车位经纬度
	 */
	@ApiModelProperty(name = "coordinate",value = "车位经纬度")
	private String coordinate;
	/**
	 * 是否有效:001.有效,002.无效
	 */
	@ApiModelProperty(name = "isInvalidate",value = "是否有效:001.有效,002.无效")
	private String isInvalidate;

}
