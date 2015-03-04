<!DOCTYPE html>
<%@ page import="com.dianping.rotate.admin.util.PageUtils" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page contentType="text/html; UTF-8" %>
<html>
    <head>
        <meta charset="utf8">
        <%= PageUtils.css("vendor/bootstrap/css/bootstrap.min") %>
        <%= PageUtils.css("vendor/bootstrap/css/bootstrap-theme.min") %>
        <%= PageUtils.css("vendor/toastr/toastr") %>
        <%= PageUtils.css("vendor/typeahead.js/typeahead") %>
        <%= PageUtils.css("asset/index") %>
    </head>

    <body>
        <header id="header"></header>
        <div id="content" style="width: 960px;margin: 0 auto;text-align: center">

            你没有权限访问该系统
        </div>



        <%
            if (StringUtils.isBlank(System.getProperty("ssoServerName"))) {
//            if (true) {
        %>

        <!-- feedback -->
        <script src="/feedback/static/js/feedback.js"></script>

        <!-- header -->
        <link rel="stylesheet" href="/hu/headunified/static/css/head.css">
        <script src="/hu/js/head.js"></script>

        <script>

//            window.app.state.onChange(function(data) {
//
//            });


            if (!HEADER.isExceptional()){
                HEADER.render("crm");
            }


        </script>
        <!-- end -->

        <%
            }
        %>
    </body>
</html>
