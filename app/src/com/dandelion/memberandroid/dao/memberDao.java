package com.dandelion.memberandroid.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dandelion.memberandroid.constant.LoggerConstant;
import com.dandelion.memberandroid.dao.auto.Account;
import com.dandelion.memberandroid.dao.auto.AccountDao;
import com.dandelion.memberandroid.dao.auto.DaoMaster;
import com.dandelion.memberandroid.dao.auto.DaoSession;

/**
 * Created by FengxiangZhu on 13-12-28.
 */
public class MemberDao {

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Cursor cursor;

    public MemberDao(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "notes-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    public void insertAccount(Account account) {
        AccountDao accountDao = daoSession.getAccountDao();
        accountDao.insert(account);
    }
    public String getAccountSid() {
        AccountDao accountDao = daoSession.getAccountDao();
        cursor = db.query(accountDao.getTablename(), accountDao.getAllColumns(), null, null, null, null, null);
        String s = "   ";
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(AccountDao.Properties.Id.columnName));
                String sid = cursor.getString(cursor.getColumnIndex(AccountDao.Properties.Sid.columnName));
                String userId = cursor.getString(cursor.getColumnIndex(AccountDao.Properties.UsdId.columnName));
                s = sid;
                Log.i(LoggerConstant.DATABASE_LOGGER, "id :" + id + " , sid :" + sid + "userId :" + userId);
            } while (cursor.moveToNext());
        }

        return s;
    }
}
