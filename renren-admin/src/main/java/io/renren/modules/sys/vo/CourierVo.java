/*
 * file name: CourierVo.java
 * copyright:
 * desc: 快递员数据传递对象
 * author: Wang Chinda
 * date: 2018-12-16 8:34
 * modify history: add
 */
package io.renren.modules.sys.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 快递员数据传递对象
 *
 * @author Wang Chinda
 * @date 2018-12-16 8:34
 * @see
 * @since 1.0
 */
public class CourierVo implements Serializable {
    private static final long serialVersionUID = -7299850319822134930L;
    /**
     *
     */
    private Integer id;
    /**
     * 快递员姓名
     */
    private String courierName;
    /**
     *
     */
    private String cardId;
    /**
     *
     */
    private String phone;
    /**
     *
     */
    private String bankCardId;
    /**
     * 开户行名称
     */
    private String depositBank;
    /**
     * 联行号
     */
    private String joinBankNumber;
    /**
     * 入职时间
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date entryDate;
    /**
     * 离职时间
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date leaveDate;
    /**
     * 1:已绑定第三方 0:未绑定第三方
     */
    private Integer status;
    /**
     *
     */
    private String comment;
    /**
     * ERP账号
     */
    private String erpId;
    /**
     * 片区
     */
    private String area;
    /**
     * 站点
     */
    private String site;
    /**
     * 合同id
     */
    private Integer pactId;
    /** 合同名称 */
    private String pactName;
    /**
     * 城市id
     */
    private Integer cityId;
    /** 城市名称 */
    private String cityName;
    /**
     *
     */
    private String creater;
    /**
     *
     */
    private Date createDate;
    /**
     *
     */
    private String modify;
    /**
     *
     */
    private Date modifyDate;
    /**
     * 1:删除0:正常
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
     * 设置：快递员姓名
     */
    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }
    /**
     * 获取：快递员姓名
     */
    public String getCourierName() {
        return courierName;
    }
    /**
     * 设置：
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    /**
     * 获取：
     */
    public String getCardId() {
        return cardId;
    }
    /**
     * 设置：
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * 获取：
     */
    public String getPhone() {
        return phone;
    }
    /**
     * 设置：
     */
    public void setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId;
    }
    /**
     * 获取：
     */
    public String getBankCardId() {
        return bankCardId;
    }
    /**
     * 设置：开户行名称
     */
    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }
    /**
     * 获取：开户行名称
     */
    public String getDepositBank() {
        return depositBank;
    }
    /**
     * 设置：联行号
     */
    public void setJoinBankNumber(String joinBankNumber) {
        this.joinBankNumber = joinBankNumber;
    }
    /**
     * 获取：联行号
     */
    public String getJoinBankNumber() {
        return joinBankNumber;
    }
    /**
     * 设置：入职时间
     */
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
    /**
     * 获取：入职时间
     */
    public Date getEntryDate() {
        return entryDate;
    }
    /**
     * 设置：离职时间
     */
    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }
    /**
     * 获取：离职时间
     */
    public Date getLeaveDate() {
        return leaveDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getModify() {
        return modify;
    }

    /**
     * 设置：
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    /**
     * 获取：
     */
    public String getComment() {
        return comment;
    }
    /**
     * 设置：ERP账号
     */
    public void setErpId(String erpId) {
        this.erpId = erpId;
    }
    /**
     * 获取：ERP账号
     */
    public String getErpId() {
        return erpId;
    }
    /**
     * 设置：片区
     */
    public void setArea(String area) {
        this.area = area;
    }
    /**
     * 获取：片区
     */
    public String getArea() {
        return area;
    }
    /**
     * 设置：站点
     */
    public void setSite(String site) {
        this.site = site;
    }
    /**
     * 获取：站点
     */
    public String getSite() {
        return site;
    }
    /**
     * 设置：合同id
     */
    public void setPactId(Integer pactId) {
        this.pactId = pactId;
    }
    /**
     * 获取：合同id
     */
    public Integer getPactId() {
        return pactId;
    }
    /**
     * 设置：城市id
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
    /**
     * 获取：城市id
     */
    public Integer getCityId() {
        return cityId;
    }
    /**
     * 获取：
     */
    public String getCreater() {
        return creater;
    }

    /**
     * 设置：
     */
    public void setCreater(String creater) {
        this.creater = creater;
    }

    public void setModify(String modify) {
        this.modify = modify;
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
     * 设置：1:删除0:正常
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
    /**
     * 获取：1:删除0:正常
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    public String getPactName() {
        return pactName;
    }

    public void setPactName(String pactName) {
        this.pactName = pactName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
