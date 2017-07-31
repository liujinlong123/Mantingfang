package com.android.mantingfanggsc;

import android.content.Context;
import android.content.pm.PackageManager;

public class PermissionsChecker {
    private final Context mContext;

    public PermissionsChecker(Context context) {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少某个权限
    public boolean lacksPermission(String permission) {
    	PackageManager pm = mContext.getPackageManager();
    	boolean permiss = (PackageManager.PERMISSION_GRANTED == pm.checkPermission(permission, "packageName"));
    	if (permiss) {
    		return true;
    	} else {
    		return false;
    	}
    }
}