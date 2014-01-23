package com.dandelion.memberandroid.model;

import java.util.List;

/**
 * Created by FengxiangZhu on 14-1-13.
 */
public class MemberListResponse {

    private List<MemberDataResponse> memberList;

    public List<MemberDataResponse> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<MemberDataResponse> memberList) {
        this.memberList = memberList;
    }
}
