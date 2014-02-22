package com.dandelion.memberandroid.model;

/**
 * Created by FengxiangZhu on 13-12-28.
 */
public class MyMembersPO {
    private String avatarUrl;
    private boolean isMember;
    private Long score;
    private String name;
    private Long memberTotalCosts;
    private Long memberTotalTimes;
    private Long friendId;
    private Long merchantUserId;

    private Long merchantId;

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
    public Long getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(Long merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    private boolean merchantOrMember; // false means members , true means merchants.

    public boolean isMerchantOrMember() {
        return merchantOrMember;
    }

    public void setMerchantOrMember(boolean merchantOrMember) {
        this.merchantOrMember = merchantOrMember;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public Long getMemberTotalCosts() {
        return memberTotalCosts;
    }

    public void setMemberTotalCosts(Long memberTotalCosts) {
        this.memberTotalCosts = memberTotalCosts;
    }

    public Long getMemberTotalTimes() {
        return memberTotalTimes;
    }

    public void setMemberTotalTimes(Long memberTotalTimes) {
        this.memberTotalTimes = memberTotalTimes;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean isMember) {
        this.isMember = isMember;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
