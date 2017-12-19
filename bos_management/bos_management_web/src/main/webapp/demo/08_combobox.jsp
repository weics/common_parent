<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>jQuery EasyUI accordion</title>
    <%--引入EasyUI相关资源文件--%>
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
    <script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
    <%--引入ztree相关资源文件--%>
    <link rel="stylesheet" href="../js/ztree/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="../js/ztree/jquery.ztree.all-3.5.js"></script>
    <script type="text/javascript" src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
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
        <div data-options="iconCls:'icon-save'" title="用户管理">
            <%--基于标准json数据创建ztree--%>
            <ul id="myztree_1" class="ztree"></ul>
            <script>
                $(function () {
                    //页面加载完成后,调用ztree提供的API,在页面上动态创建ztree
                    var settings = {};//所有属性都使用默认值
                    //定义节点数据
                    var zNodes = [
                        {
                            "name": "节点1", "children": [
                            {"name": "节点1.1"},
                            {"name": "节点1.2"},
                            {"name": "节点1.3"}
                        ]
                        },//每个json对象表示一个节点数据
                        {"name": "节点2"},
                        {"name": "节点3"}
                    ];
                    //调用init初始化方法创建ztree
                    $.fn.zTree.init($("#myztree_1"), settings, zNodes);
                });
            </script>
        </div>
        <div title="权限管理">
            <%--基于简单json数据创建ztree--%>
            <ul id="myztree_2" class="ztree"></ul>
            <script>
                $(function () {
                    var settings2 = {
                        data: {
                            simpleData: {
                                enable: true//启用简单格式的json数据
                            }
                        }//所有属性都使用默认值
                    };
                    //定义节点数据(简单json)
                    var zNodes2 = [
                        {"id": '1', "pId": '0', "name": "节点1"},//每个json对象表示一个节点数据
                        {"id": '2', "pId": '1', "name": "节点2"},
                        {"id": '3', "pId": '1', "name": "节点3"}
                    ];
                    //调用init初始化方法创建ztree
                    $.fn.zTree.init($("#myztree_2"), settings2, zNodes2);
                });
            </script>
        </div>

        <div title="xxx管理">
            <%--基于简单json数据创建ztree--%>
            <ul id="myztree_3" class="ztree"></ul>
            <script>
                $(function () {
                    //发送ajax请求
                    $.post(
                        '../data/menu.json', function (data) {
                            //页面加载完成后,调用ztree提供的API,在页面上动态创建ztree
                            var settings3 = {
                                data: {
                                    simpleData: {
                                        enable: true//启用简单格式的json数据
                                    }
                                },//所有属性都使用默认值
                                callback: {
                                    //绑定单击事件
                                    onClick: function (event, treeId, treeNode) {
                                        if (treeNode.page != undefined) {
                                            var e = $("#mytabs").tabs("exists", treeNode.name);
                                            if (e) {
                                                //已经存在了，只需要选中就可以了（select方法选中）
                                                $("#mytabs").tabs("select", treeNode.name);
                                            } else {
                                                //动态添加选项卡面板
                                                //调用tabs对象的add方法
                                                $("#mytabs").tabs("add",
                                                    {
                                                        title: treeNode.name,
                                                        closable: true,
                                                        content: '<iframe frameborder="0" width="100%" height="100%" src="${pageContext.request.contextPath}/' + treeNode.page + '"></iframe>'
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            };
                            //调用init初始化方法创建ztree
                            $.fn.zTree.init($("#myztree_3"), settings3, data);
                        },
                        'json'
                    );
                });
            </script>
        </div>
    </div>
</div>
<div style="width: 100px; " data-options="region:'east'" title="east">east</div>
<div data-options="region:'center'" title="center">

    <div id="win" class="easyui-window" title="My Window" style="width:600px;height:400px"
         data-options="iconCls:'icon-save',closable:true,closed:true,modal:true,collapsible:false,minimizable:false,maximizable:false">
        Window Content
    </div>

    <%--选项卡面板,fit:true,自适应父容器大小--%>
    <div id="mytabs" class="easyui-tabs" data-options="fit:true">
        <%--每个子div就是其中的一个面板--%>
        <div title="About" style="padding:10px">
            <table class="easyui-datagrid">
                <thead>
                <tr>
                    <th data-options="field:'id'">id</th>
                    <th data-options="field:'name'">name</th>
                    <th data-options="field:'age'">年龄</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>1</td>
                    <td>1</td>
                    <td>1</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>2</td>
                    <td>2</td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>3</td>
                    <td>3</td>
                </tr>
                </tbody>
            </table>
            <hr/>
            <%--为datagrid指定一个URL属性,当页面加载完成后,弓箭自动发送一个ajax请求获取服务端提供的动态数据(数据格式必须为json!!!)
                获取到动态数据后,自动解析,展示到页面--%>
            <table class="easyui-datagrid" data-options="url:'../data/standard.json'">
                <thead>
                <tr>
                    <th data-options="field:'id'">id</th>
                    <th data-options="field:'name'">name</th>
                    <th data-options="field:'age'">年龄</th>
                </tr>
                </thead>
            </table>
            <hr/>
            <table id="mygrid">
            </table>
            <script>
                $(function () {
                    //页面加载完成后,调用easyUI提供的API动态创建一个数据表格
                    $("#mygrid").datagrid({
                        //定义表头(表头所有的列)
                        columns: [[
                            {feild: 'id', title: '用户编号'},//每个json对象表示一列
                            {field: 'name', title: '用户姓名'},
                            {field: 'address', title: '用户地址'}
                        ]],
                        //获取数据
                        url: '../data/datagrid_data.json',
                        rownumbers: true,
                        singleSelect: true,
                        //工具栏
                        toolbar: [
                            {
                                text: "添加", iconCls: 'icon-add', handler: function () {
                                alert("你点击了添加按钮！！！");
                            }
                            },//每个json对象表示一个按钮
                            {text: "删除", iconCls: 'icon-remove'},
                            {text: "查询", iconCls: 'icon-search'}
                        ],
                        //分页条
                        pagination: true,
                        pageList: [7, 9, 13]
                    });
                });
            </script>
        </div>
    </div>
</div>
</body>
</html>
