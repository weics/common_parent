<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jquery easyui tabs选项卡面板</title>
<!-- 引入easyUI相关的资源文件 -->
<link rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="../js/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="../js/ztree/jquery.ztree.all-3.5.js"></script>
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
				<a id="mybutton" class="easyui-linkbutton">动态添加选项卡</a>
				<script type="text/javascript">
					$(function(){
						//页面加载完成后，为上面的按钮绑定事件，动态添加选项卡面板
						$("#mybutton").click(function(){
							//调用easyUI插件的API，动态添加一个选项卡面板
							//判断当前面板是否已经存在（打开）
							var e = $("#mytabs").tabs("exists","角色管理");
							if(e){
								//已经存在了，只需要选中就可以了（select方法选中）
								$("#mytabs").tabs("select","角色管理");
							}else{
								//调用tabs对象的add方法
								$("#mytabs").tabs("add",{
									title:'角色管理',
									iconCls:'icon-search',
									closable:true,
									content:'<iframe frameborder="0" width="100%" height="100%" src="https://www.baidu.com"></iframe>'
								});
							}
						});
					});
				</script>
			</div>
			<div data-options="iconCls:'icon-save'" title="用户管理">
				<!-- 基于标准json数据创建ztree -->
				<ul id="myztree_1" class="ztree"></ul>
				<script type="text/javascript">
					$(function(){
						//页面加载完成后，调用ztree提供的API，在页面上动态创建ztree
						var settings = {};//所有属性都使用默认值
						//定义节点数据
						var zNodes = [
						              {"name":"节点一","children":[
						                                        	{"name":"节点一_1"},
						                                        	{"name":"节点一_2"},
						                                        	{"name":"节点一_3"}
						                                        ]},//每个json对象表示一个节点数据
						              {"name":"节点二"},
						              {"name":"节点三"}
						              ];
						//调用init初始化方法创建ztree
						$.fn.zTree.init($("#myztree_1"), settings, zNodes);
					});
				</script>
			</div>
			<div title="权限管理">
				<!-- 基于简单json数据创建ztree -->
				<ul id="myztree_2" class="ztree"></ul>
				<script type="text/javascript">
					$(function(){
						//页面加载完成后，调用ztree提供的API，在页面上动态创建ztree
						var settings2 = {
								data: {
									simpleData: {
										enable: true//启用简单格式的json数据
									}
								}
						};//所有属性都使用默认值
						//定义节点数据(简单json)
						var zNodes2 = [
							              {"id":'1',"pId":'0',"name":"节点一"},//每个json对象表示一个节点数据
							              {"id":'2',"pId":'1',"name":"节点二"},
							              {"id":'3',"pId":'1',"name":"节点三"}
						              ];
						//调用init初始化方法创建ztree
						$.fn.zTree.init($("#myztree_2"), settings2, zNodes2);
					});
				</script>
			</div>
			<div title="XX管理">
				<!-- 发送请求获取服务端提供的json数据构造ztree -->
				<ul id="myztree_3" class="ztree"></ul>
				<script type="text/javascript">
					$(function(){
						//发送ajax请求
						$.post('../data/menu.json',function(data){
							//页面加载完成后，调用ztree提供的API，在页面上动态创建ztree
							var settings3 = {
									data: {
										simpleData: {
											enable: true//启用简单格式的json数据
										}
									},
									callback: {
										//绑定单击事件
										onClick: function(event, treeId, treeNode) {
											if(treeNode.page != undefined){
												var e = $("#mytabs").tabs("exists",treeNode.name);
												if(e){
													//已经存在了，只需要选中就可以了（select方法选中）
													$("#mytabs").tabs("select",treeNode.name);
												}else{
													//动态添加选项卡面板
													//调用tabs对象的add方法
													$("#mytabs").tabs("add",{
														title:treeNode.name,
														closable:true,
														content:'<iframe frameborder="0" width="100%" height="100%" src="${pageContext.request.contextPath}/'+treeNode.page+'"></iframe>'
													});
												}
											}
										}
									}
							};
							//调用init初始化方法创建ztree
							$.fn.zTree.init($("#myztree_3"), settings3, data);
						},'json');
					});
				</script>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<!-- 
			选项卡面板 
			fit:true,自适应父容器大小
		-->
		<div id="mytabs" class="easyui-tabs" data-options="fit:true">
			<!-- 每个子div就是其中的一个面板 -->
			<div data-options="iconCls:'icon-help',closable:true" title="系统管理">
				
				
			</div>
			<div data-options="iconCls:'icon-save'" title="用户管理"></div>
			<div title="权限管理"></div>
		</div>
	</div>
	<div style="width: 100px" data-options="region:'east'">东部区域</div>
	<div style="height: 50px" data-options="region:'south'">南部区域</div>
</body>
</html>