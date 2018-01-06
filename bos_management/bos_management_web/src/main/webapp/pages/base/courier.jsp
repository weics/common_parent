<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>管理取派员</title>
    <!-- 导入jquery核心类库 -->
    <script type="text/javascript" src="../../js/jquery-1.8.3.js"></script>
    <!-- 导入easyui类库 -->
    <link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../../js/easyui/ext/portal.css">
    <link rel="stylesheet" type="text/css" href="../../css/default.css">
    <script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../js/easyui/ext/jquery.portal.js"></script>
    <script type="text/javascript" src="../../js/easyui/ext/jquery.cookie.js"></script>
    <script src="../../js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
    <script type="text/javascript">
        function doAdd() {
            $('#addWindow').window("open");
        }

        function doEdit() {
            alert("修改...");
        }

        function doDelete() {
            //获得所有选中的行
            var rows = $("#grid").datagrid("getSelections");
            if (rows.length == 0) {
                //提示信息
                $.messager.alert("提示信息", "请选择要删除的数据", "warning");
            } else {
                //弹出一个确认框
                $.messager.confirm("确认信息", "您确定要删除当前选中的记录吗?", function (r) {
                    if (r) {
                        var arry = new Array();
                        //点击的是确定按钮
                        for (var i = 0; i < rows.length; i++) {
                            var row = rows[i];
                            var id = row.id;
                            arry.push(id);//将id压入数组对象中
                        }
                        var ids = arry.join(",");//join方法作用是将数组中所有的元素进行字符串拼接,并使用,进行分隔
                        window.location.href = "${pageContext.request.contextPath}/courierAction_deleteBatch.action?ids=" + ids;
                    }
                });
            }
        }

        function doRestore() {
            //获得所有选中行
            var rows = $("#grid").datagrid("getSelections");
            //判断选中行是否为空
            if (rows.length==0) {
                $.messager.alert("提示信息", "请选要还原的数据!", "warning");
            } else {
                //取出所选快递员id
                var array = new Array();
                for (var i = 0; i < rows.length; i++) {
                    array.push(rows[i].id);
                }
                var ids = array.join(",");
                window.location.href = "${pageContext.request.contextPath}/courierAction_recoverBatch.action?ids=" + ids;
            }
        }

        //工具栏
        var toolbar = [{
            id: 'button-add',
            text: '增加',
            iconCls: 'icon-add',
            handler: doAdd
        }, {
            id: 'button-edit',
            text: '修改',
            iconCls: 'icon-edit',
            handler: doEdit
        }, {
            id: 'button-delete',
            text: '作废',
            iconCls: 'icon-cancel',
            handler: doDelete
        }, {
            id: 'button-restore',
            text: '还原',
            iconCls: 'icon-save',
            handler: doRestore
        }, {
            id: 'button-search',
            text: '查询',
            iconCls: 'icon-search',
            handler: function () {
                $("#searchWindow").window("open");
            }
        }];
        // 定义列
        var columns = [[{
            field: 'id',
            checkbox: true,
        }, {
            field: 'courierNum',
            title: '工号',
            width: 80,
            align: 'center'
        }, {
            field: 'name',
            title: '姓名',
            width: 80,
            align: 'center'
        }, {
            field: 'telephone',
            title: '手机号',
            width: 120,
            align: 'center'
        }, {
            field: 'checkPwd',
            title: '查台密码',
            width: 120,
            align: 'center'
        }, {
            field: 'pda',
            title: 'PDA号',
            width: 120,
            align: 'center'
        }, {
            field: 'standard.name',
            title: '取派标准',
            width: 120,
            align: 'center',
            formatter: function (data, row, index) {
                if (row.standard != null) {
                    return row.standard.name;
                }
                return "";
            }
        }, {
            field: 'type',
            title: '取派员类型',
            width: 120,
            align: 'center'
        }, {
            field: 'company',
            title: '所属单位',
            width: 200,
            align: 'center'
        }, {
            field: 'deltag',
            title: '是否作废',
            width: 80,
            align: 'center',
            formatter: function (data, row, index) {
                if (data == "0") {
                    return "正常使用"
                } else {
                    return "已作废";
                }
            }
        }, {
            field: 'vehicleType',
            title: '车型',
            width: 100,
            align: 'center'
        }, {
            field: 'vehicleNum',
            title: '车牌号',
            width: 120,
            align: 'center'
        }]];

        $(function () {
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility: "visible"});

            // 取派员信息表格
            $('#grid').datagrid({
                iconCls: 'icon-forward',
                fit: true,
                border: false,
                rownumbers: true,
                striped: true,
                pageList: [3, 5, 10],
                pagination: true,
                toolbar: toolbar,
                url: "${pageContext.request.contextPath}/courierAction_pageQuery.action",
                idField: 'id',
                columns: columns,
                onDblClickRow: doDblClickRow
            });

            // 添加取派员窗口
            $('#addWindow').window({
                title: '添加取派员',
                width: 800,
                modal: true,
                shadow: true,
                closed: true,
                height: 400,
                resizable: false
            });

        });

        function doDblClickRow() {
            alert("双击表格数据...");
        }
    </script>
</head>

<body class="easyui-layout" style="visibility:hidden;">
<div region="center" border="false">
    <table id="grid"></table>
</div>
<div class="easyui-window" title="对收派员进行添加或者修改" id="addWindow" collapsible="false" minimizable="false"
     maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
        <div class="datagrid-toolbar">
            <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
            <script type="text/javascript">
                $("#save").click(function () {
                    //进行表单校验
                    var v = $("#courierForm").form("validate");
                    if (v) {
                        //校验通过,可以提交表单
                        $("#courierForm").submit();
                    }
                });
            </script>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="courierForm" action="${pageContext.request.contextPath}/courierAction_save.action" method="post">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="4">收派员信息</td>
                </tr>
                <tr>
                    <td>快递员工号</td>
                    <td>
                        <input type="text" name="courierNum" class="easyui-validatebox" required="true"/>
                    </td>
                    <td>姓名</td>
                    <td>
                        <input type="text" name="name" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>手机</td>
                    <td>
                        <input type="text" name="telephone" class="easyui-validatebox" required="true"/>
                    </td>
                    <td>所属单位</td>
                    <td>
                        <input type="text" name="company" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>查台密码</td>
                    <td>
                        <input type="text" name="checkPwd" class="easyui-validatebox" required="true"/>
                    </td>
                    <td>PDA号码</td>
                    <td>
                        <input type="text" name="pda" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>快递员类型</td>
                    <td>
                        <input type="text" name="type" class="easyui-validatebox" required="true"/>
                    </td>
                    <td>取派标准</td>
                    <td>
                        <input type="text" name="standard.id"
                               class="easyui-combobox"
                               data-options="required:true,valueField:'id',textField:'name',
											url:'${pageContext.request.contextPath}/standardAction_findAll.action'"/>
                    </td>
                </tr>
                <tr>
                    <td>车型</td>
                    <td>
                        <input type="text" name="vehicleType" class="easyui-validatebox" required="true"/>
                    </td>
                    <td>车牌号</td>
                    <td>
                        <input type="text" name="vehicleNum" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 查询快递员-->
<div class="easyui-window" data-options="modal:true" title="查询快递员窗口" closed="true" id="searchWindow" collapsible="false"
     minimizable="false"
     maximizable="false" style="width: 400px; top:40px;left:200px">
    <div style="overflow:auto;padding:5px;" border="false">
        <form id="searchForm">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">查询条件</td>
                </tr>
                <tr>
                    <td>工号</td>
                    <td>
                        <input type="text" name="courierNum"/>
                    </td>
                </tr>
                <tr>
                    <td>收派标准</td>
                    <td>
                        <input type="text" name="standard.name"/>
                    </td>
                </tr>
                <tr>
                    <td>所属单位</td>
                    <td>
                        <input type="text" name="company"/>
                    </td>
                </tr>
                <tr>
                    <td>类型</td>
                    <td>
                        <input type="text" name="type"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><a id="searchBtn" href="#" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-search'">查询</a>
                        <script type="text/javascript">
                            $(function () {
                                $.fn.serializeJson = function () {
                                    var serializeObj = {};
                                    var array = this.serializeArray();//将指定form表单中所有的输入项转为数组简单对象
                                    var str = this.serialize();
                                    $(array).each(function () {
                                        if (serializeObj[this.name]) {
                                            if ($.isArray(serializeObj[this.name])) {
                                                serializeObj[this.name].push(this.value);
                                            } else {
                                                serializeObj[this.name] = [serializeObj[this.name], this.value];
                                            }
                                        } else {
                                            serializeObj[this.name] = this.value;
                                        }
                                    });
                                    return serializeObj;
                                };

                                $("#searchBtn").click(function () {
                                    //调用datagrid的load方法,这个方法可以发送ajax请求,并且可以携带参数
                                    var json = $("#searchForm").serializeJson();//{k:v,k:v,k:v}
                                    $("#grid").datagrid("load", json);//请求地址就是datagrid的url地址
                                    //关闭查询窗口
                                    $("#searchWindow").window("close");
                                });
                            });
                        </script>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>

</html>