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
        return "<script src=\"" + resource(path) + "\"></script>";
    }

    public static String css(String path) {
        return "<link href=\"" + resource(path) + "\" media=\"all\" rel=\"stylesheet\" type=\"text/css\" />";
    }
}
