<!DOCTYPE html>
<html  manifest="hytt.appcache">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="js/jstorage.js"></script>
<title>Web Application</title>
<script type="text/javascript">

 
function load()
{
		$.jStorage.flush();
		document.getElementById('actionform').action="home";
		document.getElementById('actionform').submit();

}


</script>
</head>
<body onload="load();">
<form id="actionform" action=""></form>
</body>
</html>