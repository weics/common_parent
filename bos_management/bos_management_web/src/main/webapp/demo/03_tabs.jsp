<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>jQuery EasyUI accordion</title>
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
    <script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
<!-- 使用div表示每个区域 -->
<div style="height: 100px;" data-options="region:'north'" title="north">north</div>
<div style="height: 50px;" data-options="region:'south'" title="south">south</div>
<div style="width: 200px;" data-options="region:'west'" title="west">
    <%--折叠面板.fit:true,自适应父容器大小--%>
    <div class="easyui-accordion" data-options="fit:true">
        <%--每个子div就是其中的一个面板--%>
        <div data-options="iconCls:'icon-help'" title="系统管理">
            <a id="mybutton" class="easyui-linkbutton">动态添加选项卡</a>

            <script type="text/javascript">
                $(function () {
                    //页面加载完成后，为上面的按钮绑定事件，动态添加选项卡面板
                    $("#mybutton").click(function () {
                        //调用easyUI插件的API,动态添加一个选项卡面板
                        //判断当前面板是否已存在(打开)
                        var e = $("#mytabs").tabs("exists", "角色管理");
                        if (e) {
                            //已经存在了,只需要选中就可以了(select方法选中)
                            $("#mytabs").tabs("select", "角色管理");
                        } else {
                            //不存在,调用tabs对象的add方法
                            $("#mytabs").tabs(
                                "add", {
                                    title: '角色管理',
                                    iconCls: 'icon-search',
                                    closable: true,
                                    content: '<iframe frameborder="0" width="100%" height="100%" src="https://www.baidu.com"/>'
                                }
                            );
                        }
                    });
                });
            </script>
        </div>
        <div data-options="iconCls:'icon-save'" title="用户管理"></div>
        <div title="权限管理"></div>
    </div>
</div>
<div style="width: 100px; " data-options="region:'east'" title="east">east</div>
<div data-options="region:'center'" title="center">
    <%--选项卡面板,fit:true,自适应父容器大小--%>
    <div id="mytabs" class="easyui-tabs" data-options="fit:true">
        <%--每个子div就是其中的一个面板--%>
        <div title="About" style="padding:10px">
            <p style="font-size:14px">jQuery EasyUI framework help you build your web page easily.</p>
            <ul>
                <li>easyui is a collection of user-interface plugin based on jQuery.</li>
                <li>easyui provides essential functionality for building modem, interactive, javascript applications.
                </li>
                <li>using easyui you don't need to write many javascript code, you usually defines user-interface by
                    writing some HTML markup.
                </li>
                <li>complete framework for HTML5 web page.</li>
                <li>easyui save your time and scales while developing your products.</li>
                <li>easyui is very easy but powerful.</li>
            </ul>
        </div>
        <div title="My Documents" style="padding:10px">
            <ul class="easyui-tree" data-options="url:'../tabs/tree_data1.json',animate:true"></ul>
        </div>
        <div title="Help" data-options="iconCls:'icon-help',closable:true" style="padding:10px">
            This is the help content.
        </div>
    </div>
</div>
</body>
</html>
