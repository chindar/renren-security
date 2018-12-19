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

	private String cityName;

	private String area;

	private String site;

	private String courierName;

	private String erpId;

	private String remark;
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
	 * 工资
	 */
	private BigDecimal salary;
	/**
	 * 
	 */
	private BigDecimal totalMoney;
	/**
	 * 
	 */
	private Integer firstCount;
	/**
	 * 
	 */
	private Integer againCount;
	/**
	 * 
	 */
	private Integer otherCount;

	private Integer badCount;
    /**
     * 扣款
     */
	private BigDecimal deductMoney;

	private BigDecimal fineMoney;

	private Integer complaintCount;


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getErpId() {
		return erpId;
	}

	public void setErpId(String erpId) {
		this.erpId = erpId;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public BigDecimal getDeductMoney() {
        return deductMoney;
    }

    public void setDeductMoney(BigDecimal deductMoney) {
        this.deductMoney = deductMoney;
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

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getFirstCount() {
		return firstCount;
	}

	public void setFirstCount(Integer firstCount) {
		this.firstCount = firstCount;
	}

	public Integer getAgainCount() {
		return againCount;
	}

	public void setAgainCount(Integer againCount) {
		this.againCount = againCount;
	}

	public Integer getOtherCount() {
		return otherCount;
	}

	public void setOtherCount(Integer otherCount) {
		this.otherCount = otherCount;
	}

	public Integer getBadCount() {
		return badCount;
	}

	public void setBadCount(Integer badCount) {
		this.badCount = badCount;
	}

	public BigDecimal getFineMoney() {
		return fineMoney;
	}

	public void setFineMoney(BigDecimal fineMoney) {
		this.fineMoney = fineMoney;
	}

	public Integer getComplaintCount() {
		return complaintCount;
	}

	public void setComplaintCount(Integer complaintCount) {
		this.complaintCount = complaintCount;
	}
}
