package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 合同信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-12-15 12:06:08
 */
@TableName("tb_pact_info")
public class PactInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	@NotBlank(message="合同名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 
	 */
	@NotBlank(message="业务名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String businessName;
	/**
	 * 
	 */
	@NotNull(message="城市不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer cityId;
	/**
	 * 
	 */
	@NotBlank(message="起始日期不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String startDate;
	/**
	 * 
	 */
	@NotBlank(message="终止日期不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String endDate;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	@NotNull(message="合同状态不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer pactStatus;
	/**
	 * 
	 */
	@NotBlank(message="合同文件不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String fileId;
	/**
	 * 
	 */
	private String fileName;
	/**
	 * 
	 */
	private Integer isDelete;

	private String cityName;

	private String fileUrl;

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	/**
	 * 获取：
	 */
	public String getBusinessName() {
		return businessName;
	}
	/**
	 * 设置：
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	/**
	 * 获取：
	 */
	public Integer getCityId() {
		return cityId;
	}
	/**
	 * 设置：
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * 获取：
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * 设置：
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * 获取：
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setPactStatus(Integer pactStatus) {
		this.pactStatus = pactStatus;
	}
	/**
	 * 获取：
	 */
	public Integer getPactStatus() {
		return pactStatus;
	}
	/**
	 * 设置：
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	/**
	 * 获取：
	 */
	public String getFileId() {
		return fileId;
	}
	/**
	 * 设置：
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 获取：
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * 设置：
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	/**
	 * 获取：
	 */
	public Integer getIsDelete() {
		return isDelete;
	}
}
