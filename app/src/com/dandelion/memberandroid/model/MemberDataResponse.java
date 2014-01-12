package com.dandelion.memberandroid.model;

/**
 * Created by FengxiangZhu on 14-1-12.
 */
public class MemberDataResponse {
    private Long id;
    private Long useridfk;
    private String avatarurl;
    private String backgroundurl;
    private String name;
    private Integer sex;
    private Long birthday;
    private String address;
    private String phone;
    private String introduction;
    private Long createddate;
    private Long modifieddate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUseridfk() {
        return useridfk;
    }

    public void setUseridfk(Long useridfk) {
        this.useridfk = useridfk;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public String getBackgroundurl() {
        return backgroundurl;
    }

    public void setBackgroundurl(String backgroundurl) {
        this.backgroundurl = backgroundurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Long getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Long createddate) {
        this.createddate = createddate;
    }

    public Long getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(Long modifieddate) {
        this.modifieddate = modifieddate;
    }
}
