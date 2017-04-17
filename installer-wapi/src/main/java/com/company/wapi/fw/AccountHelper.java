package com.company.wapi.fw;

import java.util.Map;

import com.company.po.account.Account;
import com.company.util.ExpireMap;
import com.company.util.StringUtil;

public class AccountHelper {

    private static Map<String, Account> cache2Account = new ExpireMap<>();
    
    public static void cacheTokenAccount(String token, Account account) {
        
        cache2Account.put(token, account);
    }
    
    
    public static Account getAccountByToken(String token) {
        if (StringUtil.isEmpty(token)) {
            return null;
        }
        return cache2Account.get(token);
    }
}