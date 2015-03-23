package com.dianping.rotate.admin.utils;

import com.dianping.combiz.spring.util.PropertiesLoaderSupportUtils;

public class ConfigUtils {

    public static boolean isWantResignation() {
        return PropertiesLoaderSupportUtils.getBoolProperty("rotate-admin-web.userService.wantResignation", false);
    }

    public static String getBuAssnCheckBox() {
        return PropertiesLoaderSupportUtils.getProperty("rotate-admin-web.buAssnCheckBox");
    }


}
