package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotBlank(message="公司名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;
    /**
     *
     */
    @NotNull(message="城市不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer cityId;
    /**
     * 法人
     */
    @NotBlank(message="法人不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String legalPersonName;
    /**
     *
     */
    @NotBlank(message="地址不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String address;
    /**
     *
     */
    @NotBlank(message="邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String email;
    /**
     * 邮编
     */
    @NotBlank(message="邮编不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String zipCode;
    /**
     *
     */
    @NotBlank(message="联系人不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String contactName;
    /**
     *
     */
    @NotBlank(message="电话不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String phone;
    /**
     *
     */
    @NotBlank(message="营业执照不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String businessFileid;
    /**
     *
     */
    @NotBlank(message="法人身份证不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String cardFileid;

    private String cityName;

    private String businessFileUrl;

    private String cardFileUrl;

    public String getBusinessFileUrl() {
        return businessFileUrl;
    }

    public void setBusinessFileUrl(String businessFileUrl) {
        this.businessFileUrl = businessFileUrl;
    }

    public String getCardFileUrl() {
        return cardFileUrl;
    }

    public void setCardFileUrl(String cardFileUrl) {
        this.cardFileUrl = cardFileUrl;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

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
