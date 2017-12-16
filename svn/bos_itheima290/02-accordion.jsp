<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jquery easyui accordion折叠面板</title>
<!-- 引入easyUI相关的资源文件 -->
<link rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
	<!-- 使用div表示每个区域 -->
	<div title="速运快递后台系统" style="height: 100px" data-options="region:'north'">北部区域</div>
	<div title="系统菜单" style="width: 200px" data-options="region:'west'">
		<!-- 
			折叠面板 
			fit:true,自适应父容器大小
		-->
		<div class="easyui-accordion" data-options="fit:true">
			<!-- 每个子div就是其中的一个面板 -->
			<div data-options="iconCls:'icon-help'" title="系统管理">
				这里边需要有东西
			</div>
			<div data-options="iconCls:'icon-save'" title="用户管理"></div>
			<div title="权限管理"></div>
		</div>
	</div>
	<div data-options="region:'center'">中心区域</div>
	<div style="width: 100px" data-options="region:'east'">东部区域</div>
	<div style="height: 50px" data-options="region:'south'">南部区域</div>
</body>
</html>