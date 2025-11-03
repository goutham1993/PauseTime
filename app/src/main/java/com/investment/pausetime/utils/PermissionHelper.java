package com.investment.pausetime.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

public class PermissionHelper {

    public static boolean hasOverlayPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        }
        return true;
    }

    public static boolean isAccessibilityServiceEnabled(Context context) {
        String service = context.getPackageName() + "/" + 
                "com.investment.pausetime.service.AppMonitoringService";
        try {
            int accessibilityEnabled = Settings.Secure.getInt(
                    context.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
            if (accessibilityEnabled == 1) {
                String settingValue = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
                if (settingValue != null) {
                    return settingValue.contains(service);
                }
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean areAllPermissionsGranted(Context context) {
        return hasOverlayPermission(context) && isAccessibilityServiceEnabled(context);
    }
}

