
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
        import com.dandelion.memberandroid.dao.auto.MerchantInfo;
        import com.dandelion.memberandroid.dao.auto.MerchantInfoDao;

        import java.util.List;

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
    //Account
    public void insertAccount(Account account) {
        AccountDao accountDao = daoSession.getAccountDao();
        accountDao.insert(account);
    }

    public boolean getAccountExist() {
        boolean b = false;
        AccountDao accountDao = daoSession.getAccountDao();
        cursor = db.query(accountDao.getTablename(), accountDao.getAllColumns(), null, null, null, null, null);
        int count = cursor.getCount();
        if (count > 0) {
            b = true;
        }
        Log.i(LoggerConstant.DATABASE_LOGGER, "Account count : " + count);
        return b;
    }

    public Account getAccount() {
        AccountDao accountDao = daoSession.getAccountDao();
        cursor = db.query(accountDao.getTablename(), accountDao.getAllColumns(), null, null, null, null, null, "1");
        Account account = new Account();
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(AccountDao.Properties.Id.columnName));
                String sid = cursor.getString(cursor.getColumnIndex(AccountDao.Properties.Sid.columnName));
                long userId = cursor.getLong(cursor.getColumnIndex(AccountDao.Properties.UsdId.columnName));
                int accountType = cursor.getInt(cursor.getColumnIndex(AccountDao.Properties.AccountType.columnName));
                account.setId(id);
                account.setSid(sid);
                account.setUsdId(userId);
                account.setAccountType(accountType);
                Log.i(LoggerConstant.DATABASE_LOGGER, "id :" + id + " , sid :" + sid + "userId :" + userId + "AccountType" + accountType);
            } while (cursor.moveToNext());
        }
        return account;
    }

    public void deleteAccounts() {
        AccountDao accountDao = daoSession.getAccountDao();
        accountDao.deleteAll();
    }


    //Merchant
    public void insertMerchantInfo(MerchantInfo merchantinfo) {
        MerchantInfoDao merchantInfoDao = daoSession.getMerchantInfoDao();
        merchantInfoDao.insert(merchantinfo);
    }
    public void updateMerchantInfo(MerchantInfo merchantinfo) {
        MerchantInfoDao merchantInfoDao = daoSession.getMerchantInfoDao();
        merchantInfoDao.update(merchantinfo);
    }
    public List<MerchantInfo> getMerchantInfos(long userId) {
        MerchantInfoDao merchantInfoDao = daoSession.getMerchantInfoDao();
        List<MerchantInfo> merchantList = merchantInfoDao.queryBuilder().where(MerchantInfoDao.Properties.Useridfk.eq(userId)).list();
        return merchantList;
    }
    public void deleteMerchantInfo(long id) {
        MerchantInfoDao merchantInfoDao = daoSession.getMerchantInfoDao();
        merchantInfoDao.deleteByKey(id);

    }





}
