<%--
  Created by IntelliJ IDEA.
  User: Think
  Date: 2018-1-9
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>数据表格行编辑功能</title>

    <!-- 引入easyUI相关的资源文件 -->
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
    <script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/ocupload/jquery.ocupload-1.1.2.js"></script>

</head>
<body>
<table id="mygrid"></table>
<script>
    $(function () {
        var index;
        //页面加载完成后,调用easyUI提供的api动态创建一个数据表格
        $("#mygrid").datagrid({
            //定义表头(表头所有的列)
            columns: [[
                {field: 'id', title: '用户编号', checkbox: true},//每个json对象表示一列
                {
                    width: 150, field: 'name', title: '用户姓名', editor: {
                        type: 'validatebox',
                        options: {required: true}
                    }
                },
                {
                    width: 150, field: 'address', title: '用户地址', editor: {
                        type: 'datebox',
                        options: {required: true}
                    }
                }
            ]],
            //获取数据
            url: '../data/datagrid_data.json',
            //结束标记时出发这个时间
            onAfterEdit: function (rowIndex, rowData, changes) {
                alert("索引为" + rowIndex + "的行结束编辑了。。。,修改后的用户姓名为：" + rowData.name);
            },
            rownumbers:true,
            singleSelect:false,
            //工具栏
            toolbar:[
                {text:"添加",iconCls:'icon-add',handler:function(){
                        $("#mygrid").datagrid("insertRow",{
                            index:0,
                            row:{}
                        });
                        $("#mygrid").datagrid("beginEdit",0);
                        index = 0;
                    }},//每个json对象表示一个按钮
                {text:"删除",iconCls:'icon-remove',handler:function(){
                        //获得所有选中的行
                        var rows = $("#mygrid").datagrid("getSelections");
                        if(rows.length == 0){
                            $.messager.alert("提示信息","请选择需要删除的行！","info");
                        }else{
                            for(var i=0;i<rows.length;i++){
                                var row = rows[i];
                                //获得当前选中行的索引
                                var index = $("#mygrid").datagrid("getRowIndex",row);
                                $("#mygrid").datagrid("deleteRow",index);
                            }
                        }
                    }},
                {text:"修改",iconCls:'icon-edit',handler:function(){
                        //获得所有选中的行
                        var rows = $("#mygrid").datagrid("getSelections");
                        if(rows.length != 1){
                            $.messager.alert("提示信息","请选择一行进行修改！","info");
                        }else{
                            index = $("#mygrid").datagrid("getRowIndex",rows[0]);
                            $("#mygrid").datagrid("beginEdit",index);
                        }
                    }},
                {text:"保存",iconCls:'icon-save',handler:function(){
                        $("#mygrid").datagrid("endEdit",index);
                    }}
            ],
            //分页条
            pagination:true,
            pageList:[7,9,13]
        });
    });
</script>
</body>
</html>
