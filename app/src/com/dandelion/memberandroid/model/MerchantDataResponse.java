package com.dandelion.memberandroid.model;

/**
 * Created by FengxiangZhu on 14-1-15.
 */

public class MerchantDataResponse {
    private Long merchantId;
    private String avatarurl;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String merchanttype;
    private String introduction;
    private Boolean namerequired;
    private Boolean sexrequired;
    private Boolean phonerequired;
    private Boolean addressrequired;
    private Boolean emailrequired;
    private Boolean birthdayrequired;
    private Boolean membersetting;
    private Integer amountrequired;
    private Integer amountcountrequired;
    private Boolean scoreplan;
    private String backgroundurl;
    private Long friendId;
    private boolean ismember;
    private Long amount;
    private Long amountcount;
    private Long score;
    private Long userId;
    private String useremail;
    private Integer friendcount;
    private Integer fancount;
    private Integer followcount;
    private Integer articlecount;

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public boolean isIsmember() {
        return ismember;
    }

    public void setIsmember(boolean ismember) {
        this.ismember = ismember;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getAmountcount() {
        return amountcount;
    }

    public void setAmountcount(Long amountcount) {
        this.amountcount = amountcount;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMerchanttype() {
        return merchanttype;
    }

    public void setMerchanttype(String merchanttype) {
        this.merchanttype = merchanttype;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Boolean getNamerequired() {
        return namerequired;
    }

    public void setNamerequired(Boolean namerequired) {
        this.namerequired = namerequired;
    }

    public Boolean getSexrequired() {
        return sexrequired;
    }

    public void setSexrequired(Boolean sexrequired) {
        this.sexrequired = sexrequired;
    }

    public Boolean getPhonerequired() {
        return phonerequired;
    }

    public void setPhonerequired(Boolean phonerequired) {
        this.phonerequired = phonerequired;
    }

    public Boolean getAddressrequired() {
        return addressrequired;
    }

    public void setAddressrequired(Boolean addressrequired) {
        this.addressrequired = addressrequired;
    }

    public Boolean getEmailrequired() {
        return emailrequired;
    }

    public void setEmailrequired(Boolean emailrequired) {
        this.emailrequired = emailrequired;
    }

    public Boolean getBirthdayrequired() {
        return birthdayrequired;
    }

    public void setBirthdayrequired(Boolean birthdayrequired) {
        this.birthdayrequired = birthdayrequired;
    }

    public Boolean getMembersetting() {
        return membersetting;
    }

    public void setMembersetting(Boolean membersetting) {
        this.membersetting = membersetting;
    }

    public Integer getAmountrequired() {
        return amountrequired;
    }

    public void setAmountrequired(Integer amountrequired) {
        this.amountrequired = amountrequired;
    }

    public Integer getAmountcountrequired() {
        return amountcountrequired;
    }

    public void setAmountcountrequired(Integer amountcountrequired) {
        this.amountcountrequired = amountcountrequired;
    }

    public Boolean getScoreplan() {
        return scoreplan;
    }

    public void setScoreplan(Boolean scoreplan) {
        this.scoreplan = scoreplan;
    }

    public String getBackgroundurl() {
        return backgroundurl;
    }

    public void setBackgroundurl(String backgroundurl) {
        this.backgroundurl = backgroundurl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public Integer getFriendcount() {
        return friendcount;
    }

    public void setFriendcount(Integer friendcount) {
        this.friendcount = friendcount;
    }

    public Integer getFancount() {
        return fancount;
    }

    public void setFancount(Integer fancount) {
        this.fancount = fancount;
    }

    public Integer getFollowcount() {
        return followcount;
    }

    public void setFollowcount(Integer followcount) {
        this.followcount = followcount;
    }

    public Integer getArticlecount() {
        return articlecount;
    }

    public void setArticlecount(Integer articlecount) {
        this.articlecount = articlecount;
    }


}
