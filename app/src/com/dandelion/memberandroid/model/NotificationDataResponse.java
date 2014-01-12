package com.dandelion.memberandroid.model;

public class NotificationDataResponse {

    private Long id;
    private Long fromuseridfk;
    private Long touseridfk;
    private String content;
    private Boolean isread;
    private Boolean isdeleted;
    private Long createddate;
    private Long modifieddate;
    private Integer sort;
    private MemberDataResponse member;

    public MemberDataResponse getMember() {
        return member;
    }

    public void setMember(MemberDataResponse member) {
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromuseridfk() {
        return fromuseridfk;
    }

    public void setFromuseridfk(Long fromuseridfk) {
        this.fromuseridfk = fromuseridfk;
    }


    public Long getTouseridfk() {
        return touseridfk;
    }

    public void setTouseridfk(Long touseridfk) {
        this.touseridfk = touseridfk;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsread() {
        return isread;
    }

    public void setIsread(Boolean isread) {
        this.isread = isread;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Long getCreateddate() {
        return createddate;
    }

    public Long getModifieddate() {
        return modifieddate;
    }

    public void setCreateddate(Long createddate) {
        this.createddate = createddate;
    }

    public void setModifieddate(Long modifieddate) {
        this.modifieddate = modifieddate;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}