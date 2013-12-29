package com.dandelion.memberandroid.model;

/**
 * Created by FengxiangZhu on 13-12-29.
 */
public class MemberTimelineFeedPO {
    private String feedimageUrl;
    private String feedTitle;
    private String feedContent;
    private long userId;
    private long feedId;
    private long merchantId;
    private String merchantAvatarUrl;
    private String merchantName;
    private long merchantTel;
    private String merchantAddress;
    private String merchantEmail;
    private boolean isMember;

    public String getFeedimageUrl() {
        return feedimageUrl;
    }

    public void setFeedimageUrl(String feedimageUrl) {
        this.feedimageUrl = feedimageUrl;
    }

    public String getFeedTitle() {
        return feedTitle;
    }

    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

    public String getFeedContent() {
        return feedContent;
    }

    public void setFeedContent(String feedContent) {
        this.feedContent = feedContent;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getFeedId() {
        return feedId;
    }

    public void setFeedId(long feedId) {
        this.feedId = feedId;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantAvatarUrl() {
        return merchantAvatarUrl;
    }

    public void setMerchantAvatarUrl(String merchantAvatarUrl) {
        this.merchantAvatarUrl = merchantAvatarUrl;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public long getMerchantTel() {
        return merchantTel;
    }

    public void setMerchantTel(long merchantTel) {
        this.merchantTel = merchantTel;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getMerchantEmail() {
        return merchantEmail;
    }

    public void setMerchantEmail(String merchantEmail) {
        this.merchantEmail = merchantEmail;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean isMember) {
        this.isMember = isMember;
    }
}
