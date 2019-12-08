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
 * 停车场信息
 * 
 * @author changchen
 * @email ${email}
 * @date 2019-12-02 20:47:31
 */
@ApiModel
@Data
@TableName("t_op_parking")
public class OpParkingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 停车场ID
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "停车场ID")
	private Integer id;
	/**
	 * 租户id
	 */
	@ApiModelProperty(name = "siteId",value = "租户id")
	private Integer siteId;
	/**
	 * 停车场编码
	 */
	@ApiModelProperty(name = "code",value = "停车场编码")
	private String code;
	/**
	 * 停车场名称
	 */
	@ApiModelProperty(name = "name",value = "停车场名称")
	private String name;
	/**
	 * 停车场类型：0101.路内停车场 0201.普通停车场 0301.P+R停车场
	 */
	@ApiModelProperty(name = "category",value = "停车场类型：0101.路内停车场 0201.普通停车场 0301.P+R停车场")
	private String category;
	/**
	 * 停车场所属区域ID
	 */
	@ApiModelProperty(name = "areaId",value = "停车场所属区域ID")
	private String areaId;
	/**
	 * 停车场地址
	 */
	@ApiModelProperty(name = "location",value = "停车场地址")
	private String location;
	/**
	 * 运营人姓名
	 */
	@ApiModelProperty(name = "opName",value = "运营人姓名")
	private String opName;
	/**
	 * 运营电话
	 */
	@ApiModelProperty(name = "opMobile",value = "运营电话")
	private String opMobile;
	/**
	 * 停车场描述
	 */
	@ApiModelProperty(name = "comments",value = "停车场描述")
	private String comments;
	/**
	 * 1.独立公共、2.配建公共、3.其他
	 */
	@ApiModelProperty(name = "type",value = "1.独立公共、2.配建公共、3.其他")
	private String type;
	/**
	 * 1.永久 2. 临时
	 */
	@ApiModelProperty(name = "properties",value = "1.永久 2. 临时")
	private String properties;
	/**
	 * 停车场/泊位面积
	 */
	@ApiModelProperty(name = "size",value = "停车场/泊位面积")
	private BigDecimal size;
	/**
	 * 1.出让、2.自由、3.租赁、4.划拨
	 */
	@ApiModelProperty(name = "source",value = "1.出让、2.自由、3.租赁、4.划拨")
	private String source;
	/**
	 * 停车场泊位数ID
	 */
	@ApiModelProperty(name = "parkinglotNumId",value = "停车场泊位数ID")
	private Integer parkinglotNumId;
	/**
	 * 充电设备数量
	 */
	@ApiModelProperty(name = "chargingDeviceNum",value = "充电设备数量")
	private Integer chargingDeviceNum;
	/**
	 * 是否含非机动车车位
	 */
	@ApiModelProperty(name = "ishasNonemotorParking",value = "是否含非机动车车位")
	private Integer ishasNonemotorParking;
	/**
	 * 非机动车规模
	 */
	@ApiModelProperty(name = "nonemotorComments",value = "非机动车规模")
	private String nonemotorComments;
	/**
	 * 是否有信息管理系统
	 */
	@ApiModelProperty(name = "ishasInformationSystem",value = "是否有信息管理系统")
	private Integer ishasInformationSystem;
	/**
	 * 1. 正常收费 2 免费 3 停用
	 */
	@ApiModelProperty(name = "status",value = "1. 正常收费 2 免费 3 停用")
	private String status;
	/**
	 * 停车场经纬度
	 */
	@ApiModelProperty(name = "coordinate",value = "停车场经纬度")
	private String coordinate;
	/**
	 * 是否缓存
	 */
	@ApiModelProperty(name = "isCache",value = "是否缓存")
	private Integer isCache;
	/**
	 * 排序
	 */
	@ApiModelProperty(name = "sort",value = "排序")
	private Integer sort;
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
	/**
	 * 区域整理id
	 */
	@ApiModelProperty(name = "excelId",value = "区域整理id")
	private Integer excelId;

}
