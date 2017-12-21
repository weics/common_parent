<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>

    <!-- 引入easyUI相关的资源文件 -->
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
    <script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/ocupload/jquery.ocupload-1.1.2.js"></script>
    <script type="text/javascript">
        $(function () {
            /*
                页面加载完成后,调用ocupload插件的一个方法upload,
                这个方法会通过jQuery语法动态修改页面HTML元素,
                在页面上动态创建一个form表单和input上传输入框,iframe
            */
            $("#test").click(function () {
                alert('123');
                $("#myBtn").upload({
                    name: 'myFile',
                    action: 'xxx.action'
                });
            });
        });
    </script>
</head>
<body>
<input id="test" type="button" value="test">
<input value="上传" type="button" id="myBtn">
</body>
</html>
