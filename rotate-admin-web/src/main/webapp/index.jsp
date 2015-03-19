<!DOCTYPE html>
<%@ page import="com.dianping.rotate.admin.util.PageUtils" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="com.dianping.lion.EnvZooKeeperConfig" %>
<html>
    <head>
        <meta charset="utf8">
        <%= PageUtils.css("vendor/bootstrap/css/bootstrap.min") %>
        <%= PageUtils.css("vendor/bootstrap/css/bootstrap-theme.min") %>
        <%= PageUtils.css("vendor/toastr/toastr") %>
        <%= PageUtils.css("vendor/typeahead.js/typeahead") %>
        <%= PageUtils.css("asset/index") %>
        <script>
            var ENV = {
                ajaxPrefix: '/rotate/data'
            };
        </script>
    </head>

    <body>
        <header id="header"></header>
        <div id="content"></div>

        <%= PageUtils.js("node_modules") %>
        <%= PageUtils.js("jquery-vendors") %>
        <%= PageUtils.js("services") %>
        <%= PageUtils.js("modules") %>
        <%= PageUtils.js("index") %>


        <%
            if (StringUtils.isBlank(System.getProperty("ssoServerName"))&& !EnvZooKeeperConfig.getEnv().equals("alpha")) {

        %>

        <!-- header -->
        <link rel="stylesheet" href="/hu/headunified/static/css/head.css">
        <script src="/hu/js/head.js"></script>

        <script>
            if (!HEADER.isExceptional()){
                HEADER.render("rotate");
            }
        </script>
        <!-- end -->

        <%
            }
        %>
    </body>
</html>
