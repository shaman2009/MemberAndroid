package com.dandelion.memberandroid.model;

/**
 * Created by FengxiangZhu on 14-1-17.
 */
public class MemberInfoDataResponse {
    private Long id;
    private Long useridfk;
    private String avatarurl;
    private String backgroundurl;
    private String name;
    private Integer sex;
    private long birthday;
    private String address;
    private String phone;
    private String introduction;
    private long createddate;
    private long modifieddate;

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

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
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

    public long getCreateddate() {
        return createddate;
    }

    public void setCreateddate(long createddate) {
        this.createddate = createddate;
    }

    public long getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(long modifieddate) {
        this.modifieddate = modifieddate;
    }
}
