<#macro main title="Default" page_name="Default">
<!DOCTYPE html>
<#include "triggering.ftl">
<html lang="en">
<head>
	<!-- meta shit -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- content shit -->
    <title>${title} | The Genie</title>

	<!-- style shit -->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	<!-- internal shit -->
	<div id="internal_data">${internal_data}</div>
	<div id="current_page">${page_name}</div>

	<!-- content shit -->
	<div class="container">
		<!-- header -->
		<div class="header">
			The Genie
		</div>

		<!-- navbar & alerts -->
		<#include "navbar.ftl">
		<#include "alert.ftl">

		<!-- start of actual content -->
		<#nested/>
		<!-- end of actual content -->
	</div>

	<!-- script shit -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
	<script src="js/internal.js"></script>
</body>
</html>
</#macro>