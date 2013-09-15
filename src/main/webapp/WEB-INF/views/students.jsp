<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<%-- <title><spring:message code="label.title" /></title> --%>
<!-- JQuery CSS -->
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.14/themes/redmond/jquery-ui.css" type="text/css" />
 
<!-- jqGrid CSS -->
<link rel="stylesheet" href="/resources/css/ui.jqgrid.css" type="text/css" />
 
 
<!-- The actual JQuery code -->
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js" />
<!-- The JQuery UI code -->
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.min.js" />
<!-- The jqGrid language file code-->
<script type="text/javascript" src="/com.epam.trn/js/i18n/grid.locale-en.js" />
<!-- The atual jqGrid code -->
<script type="text/javascript" src="/com.epam.trn/js/jquery.jqGrid.src.js" />
</head>
<body>
	<table id="theGrid"></table>
	<script>
		$("document").ready(function() {
			
		$("#theGrid").jqGrid({ 
			  url: "http://localhost:8080/com.epam.trn/students", 
			  datatype: "json", 
			  mtype: "GET", 
			  colNames: ['Column1', 'Column2'], 
			  colModel: [ {name: 'Column1', index: 'id', width: 90}, 
			              {name: 'Column2', index: 'name', width: 100, align: 'center'}
						],
			  sortname: 'Column1', 
			  viewrecords: true, 
			  sortorder: 'desc', 
			  caption: 'My First Grid'
		  });	
		});
	</script>	
</body>
</html>