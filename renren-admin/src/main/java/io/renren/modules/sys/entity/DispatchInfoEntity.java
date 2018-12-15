package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 配送信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-12-15 12:06:08
 */
@TableName("tb_dispatch_info")
public class DispatchInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 月份
	 */
	private String month;
	/**
	 * 快递员id
	 */
	private Integer courierId;
	/**
	 * 
	 */
	private Integer allOrderTotal;
	/**
	 * 
	 */
	private Integer countOrderTotal;
	/**
	 * 大件
	 */
	private Integer large;
	/**
	 * 
	 */
	private Integer small;
	/**
	 * 三同
	 */
	private String thrIdentical;
	/**
	 * 售后取件
	 */
	private String afterSale;
	/**
	 * 商家接货
	 */
	private String sellerPick;
	/**
	 * 工资
	 */
	private BigDecimal salary;
	/**
	 * 
	 */
	private Integer creater;
	/**
	 * 
	 */
	private Date createDate;
	/**
	 * 
	 */
	private Integer modify;
	/**
	 * 
	 */
	private Date modifyDate;
	/**
	 * 1:删除0：正常
	 */
	private Integer isDelete;

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
	 * 设置：月份
	 */
	public void setMonth(String month) {
		this.month = month;
	}
	/**
	 * 获取：月份
	 */
	public String getMonth() {
		return month;
	}
	/**
	 * 设置：快递员id
	 */
	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}
	/**
	 * 获取：快递员id
	 */
	public Integer getCourierId() {
		return courierId;
	}
	/**
	 * 设置：
	 */
	public void setAllOrderTotal(Integer allOrderTotal) {
		this.allOrderTotal = allOrderTotal;
	}
	/**
	 * 获取：
	 */
	public Integer getAllOrderTotal() {
		return allOrderTotal;
	}
	/**
	 * 设置：
	 */
	public void setCountOrderTotal(Integer countOrderTotal) {
		this.countOrderTotal = countOrderTotal;
	}
	/**
	 * 获取：
	 */
	public Integer getCountOrderTotal() {
		return countOrderTotal;
	}
	/**
	 * 设置：大件
	 */
	public void setLarge(Integer large) {
		this.large = large;
	}
	/**
	 * 获取：大件
	 */
	public Integer getLarge() {
		return large;
	}
	/**
	 * 设置：
	 */
	public void setSmall(Integer small) {
		this.small = small;
	}
	/**
	 * 获取：
	 */
	public Integer getSmall() {
		return small;
	}
	/**
	 * 设置：三同
	 */
	public void setThrIdentical(String thrIdentical) {
		this.thrIdentical = thrIdentical;
	}
	/**
	 * 获取：三同
	 */
	public String getThrIdentical() {
		return thrIdentical;
	}
	/**
	 * 设置：售后取件
	 */
	public void setAfterSale(String afterSale) {
		this.afterSale = afterSale;
	}
	/**
	 * 获取：售后取件
	 */
	public String getAfterSale() {
		return afterSale;
	}
	/**
	 * 设置：商家接货
	 */
	public void setSellerPick(String sellerPick) {
		this.sellerPick = sellerPick;
	}
	/**
	 * 获取：商家接货
	 */
	public String getSellerPick() {
		return sellerPick;
	}
	/**
	 * 设置：工资
	 */
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	/**
	 * 获取：工资
	 */
	public BigDecimal getSalary() {
		return salary;
	}
	/**
	 * 设置：
	 */
	public void setCreater(Integer creater) {
		this.creater = creater;
	}
	/**
	 * 获取：
	 */
	public Integer getCreater() {
		return creater;
	}
	/**
	 * 设置：
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：
	 */
	public void setModify(Integer modify) {
		this.modify = modify;
	}
	/**
	 * 获取：
	 */
	public Integer getModify() {
		return modify;
	}
	/**
	 * 设置：
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	/**
	 * 获取：
	 */
	public Date getModifyDate() {
		return modifyDate;
	}
	/**
	 * 设置：1:删除0：正常
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	/**
	 * 获取：1:删除0：正常
	 */
	public Integer getIsDelete() {
		return isDelete;
	}
}
