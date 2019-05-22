<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>ftp-plupload</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link type="text/css" rel="stylesheet" href="<%=path%>/css/font-awesome.min.css" media="screen" /
		<link rel="stylesheet" href="<%=path%>/plupload-2.3.6/js/jquery.plupload.queue/css/jquery.plupload.queue.css" type="text/css"></link>
		<link rel="stylesheet" href="<%=path%>/plupload-2.3.6/js/jquery.ui.plupload/css/jquery.ui.plupload.css" type="text/css"></link>
		<link rel="stylesheet" href="<%=path%>/jquery-ui-1.10.4/css/base/jquery-ui-1.10.4.custom.css" type="text/css"></link>
		
		<script type="text/javascript" src="<%=path%>/js/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="<%=path%>/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.js"></script>
		<script type="text/javascript" src="<%=path%>/plupload-2.3.6/js/plupload.full.min.js"></script>
		
		<script type="text/javascript" src="<%=path%>/plupload-2.3.6/js/moxie.js"></script>
		<script type="text/javascript" src="<%=path%>/plupload-2.3.6/js/plupload.dev.js"></script>
		
		<script type="text/javascript" src="<%=path%>/plupload-2.3.6/js/jquery.ui.plupload/jquery.ui.plupload.min.js"></script>
		<script type="text/javascript" src="<%=path%>/plupload-2.3.6/js/jquery.plupload.queue/jquery.plupload.queue.js"></script>

	</head>

	<body>
    	<div id="uploader" style="width: 800px;height: 300px;">&nbsp;</div>
	</body>
	
	<script type="text/javascript">

	$(function() {
	    $("#uploader").plupload({
	        runtimes : 'html5,flash,silverlight,html4',
	        url : "<%=path%>/uploader.action",
	        max_file_size : '20mb',
	        multipart : true,
	        chunk_size: '10mb',
	 
	        filters : [
	            {title : "Image files", extensions : "jpg,gif,png"},
	            {title : "Zip files", extensions : "zip,avi"}
	        ],
	        rename: true,
	        sortable: true,
	        dragdrop: true,
	        views: {
	            list: true,
	            thumbs: true, // 视图显示
	            active: 'thumbs'
	        },
	        flash_swf_url : '<%=path%>/plupload-2.3.6/js/Moxie.swf',
	        silverlight_xap_url : '<%=path%>/plupload-2.3.6/js/Moxie.xap'
	    });
	});
	
	</script>
</html>
