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
            这里边需要有东西
        </div>
        <div data-options="iconCls:'icon-save'" title="用户管理"></div>
        <div title="权限管理"></div>

        <div title="TreeMenu" data-options="iconCls:'icon-search'" style="padding:10px;">
            <ul class="easyui-tree">
                <li>
                    <span>Foods</span>
                    <ul>
                        <li>
                            <span>Fruits</span>
                            <ul>
                                <li>apple</li>
                                <li>orange</li>
                            </ul>
                        </li>
                        <li>
                            <span>Vegetables</span>
                            <ul>
                                <li>tomato</li>
                                <li>carrot</li>
                                <li>cabbage</li>
                                <li>potato</li>
                                <li>lettuce</li>
                            </ul>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>
<div style="width: 100px; " data-options="region:'east'" title="east">east</div>
<div data-options="region:'center'" title="center">center</div>
</body>
</html>
