package com.dandelion.memberandroid.service;

import android.content.Context;

import com.dandelion.memberandroid.dao.MemberDao;
import com.dandelion.memberandroid.dao.auto.Account;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by FengxiangZhu on 13-12-29.
 */
public class AccountService {
    private MemberDao memberDao;
    public AccountService(Context context) {
        memberDao = new MemberDao(context);
    }

    public void loginSuccessService(String response) {
        Account account = new Account();
        try {
            JSONObject json = new JSONObject(response);
            account.setSid(json.getString("sid"));
            account.setSkey(json.getLong("skey"));
            account.setAccountType(json.getInt("accountType"));
            account.setUsdId(json.getLong("userId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Date d = new Date();
        account.setCreatedDate(d);
        account.setModifyDate(d);

        memberDao.deleteAccounts();
        memberDao.insertAccount(account);
    }

    public boolean isAccountExist() {
        return memberDao.getAccountExist();
    }

    public Account getAuthAccount() {
        return memberDao.getAccount();
    }
    public void deleteAllAccounts() {
        memberDao.deleteAccounts();
    }

}
