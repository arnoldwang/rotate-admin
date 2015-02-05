package com.dianping.rotate.admin.util;

import com.dianping.combiz.spring.util.LionConfigUtils;

/**
 * Created by yangjie on 1/21/15.
 */
public class PageUtils {
    private static String resource(String path) {
        return getPrefix() + "/" + path;
    }

    private static String getPrefix() {
        return System.getProperty("localresource") != null ? System.getProperty("localresource") :  LionConfigUtils.getProperty("rotate-admin-web.js.path") + LionConfigUtils.getProperty("rotate-admin-web.js.version");
    }

    public static String js(String path) {
        return "<script src=\"" + resource(path) + getJSSuffix() + "\"></script>";
    }

    private static String getJSSuffix() {
        String suffix = ".js";
        if (System.getProperty("localresource") == null && LionConfigUtils.getProperty("rotate-admin-web.js.compress").equals("true")) {
            suffix = ".min" + suffix;
        }
        return suffix;
    }

    public static String css(String path) {
        return "<link href=\"" + resource(path) + ".css" + "\" media=\"all\" rel=\"stylesheet\" type=\"text/css\" />";
    }
}
