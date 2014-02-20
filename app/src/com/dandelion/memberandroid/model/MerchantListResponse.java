package com.dandelion.memberandroid.model;

import java.util.List;

/**
 * Created by FengxiangZhu on 14-1-15.
 */
public class MerchantListResponse {
    private List<MerchantDataResponse> merchantList;

    public List<MerchantDataResponse> getMerchantList() {
        return merchantList;
    }

    public void setMerchantList(List<MerchantDataResponse> merchantList) {
        this.merchantList = merchantList;
    }
}
