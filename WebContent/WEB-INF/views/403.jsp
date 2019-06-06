<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="app.header.title"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<!-- Bootstrap -->
		<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
		<link href="css/font-awesome.css" rel="stylesheet" media="screen">
		<link href="css/main.css" rel="stylesheet" media="screen">
	</head>
	<body>
		<header class="navbar navbar-inverse navbar-fixed-top">
			<div class="container">
				<a class="navbar-brand" href="dashboard"> <spring:message code="app.title"/> </a>
			</div>
		</header>
	
		<section id="main">
			<div class="container">
				<div class="alert alert-danger">
					<spring:message code="app.error"/> 403: <spring:message code="app.error.403"/>
					<br/>
					<!-- stacktrace -->
				</div>
			</div>
		</section>
		
		<script src="${ pageContext.request.contextPath }/js/jquery.min.js"></script>
		<script src="${ pageContext.request.contextPath }/js/bootstrap.min.js"></script>
		<script src="${ pageContext.request.contextPath }/js/dashboard.js"></script>
	
	</body>
</html>