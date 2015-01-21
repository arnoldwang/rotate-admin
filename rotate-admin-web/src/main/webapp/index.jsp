<!DOCTYPE html>
<%@ page import="com.dianping.rotate.admin.util.PageUtils" %>
<html>
    <head>
        <meta charset="utf8">
        <%= PageUtils.css("vendor/bootstrap/css/bootstrap.min.css") %>
        <%= PageUtils.css("vendor/bootstrap/css/bootstrap-theme.min.css") %>
        <%= PageUtils.css("vendor/toastr/toastr.css") %>

        <%= PageUtils.css("asset/index.css") %>
        <script>
            var ENV = {
                mock: false
            }
        </script>
    </head>

    <body>
        <%= PageUtils.js("node_modules.js") %>
        <%= PageUtils.js("jquery-vendors.js") %>
        <%= PageUtils.js("services.js") %>
        <%= PageUtils.js("modules.js") %>
        <%= PageUtils.js("index.js") %>
    </body>
</html>
