<!DOCTYPE html>
<html>
<head>
<!-- JQuery CSS -->
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.14/themes/redmond/jquery-ui.css" type="text/css" />
<!-- jqGrid CSS -->
<link rel="stylesheet" href="resources/css/ui.jqgrid.css" type="text/css" />
<!-- The actual JQuery code -->
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js" ></script>
<!-- The JQuery UI code -->
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.min.js" ></script>
<!-- The jqGrid language file code-->
<script type="text/javascript" src="resources/js/i18n/grid.locale-en.js" ></script>
<!-- The atual jqGrid code -->
<script type="text/javascript" src="resources/js/jquery.jqGrid.src.js" ></script>
<title>tit</title>
	<script>
		$(document).ready(function() {
			$("#theGrid").jqGrid({ 
	  			url: "students", 
	  		    editurl:"students",
				datatype: "json",
			  	mtype: "GET", 
			  	colNames: ['id', 'login'], 
			  	colModel: [ {name: 'id', index: 'id', width: 590}, 
			              {name: 'login', index: 'login'}
				],
				rowNum:10,
			   	rowList:[10,20,30],
			   	pager: '#pager2',
			  	sortname: 'id', 
			  	viewrecords: true, 
			  	multiselect: true,
	            multiboxonly: true,
			  	sortorder: 'desc', 
			  	caption: 'My First Grid'
	  		});
			$("#theGrid").jqGrid('navGrid','#pager2',{edit:true,add:true,del:true});
		});
	</script>	
</head>
<body>
	<table id="theGrid"></table>
	<div id="pager2"></div>
</body>
</html>