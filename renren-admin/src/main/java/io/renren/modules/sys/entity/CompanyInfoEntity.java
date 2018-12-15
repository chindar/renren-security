package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 公司信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-12-15 12:06:08
 */
@TableName("tb_company_info")
public class CompanyInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private Integer cityId;
    /**
     * 法人
     */
    private String legalPersonName;
    /**
     *
     */
    private String address;
    /**
     *
     */
    private String email;
    /**
     * 邮编
     */
    private String zipCode;
    /**
     *
     */
    private String contactName;
    /**
     *
     */
    private String phone;
    /**
     *
     */
    private String


            businessFileid;
    /**
     *
     */
    private String cardFileid;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBusinessFileid() {
        return businessFileid;
    }

    public void setBusinessFileid(String businessFileid) {
        this.businessFileid = businessFileid;
    }

    public String getCardFileid() {
        return cardFileid;
    }

    public void setCardFileid(String cardFileid) {
        this.cardFileid = cardFileid;
    }
}
