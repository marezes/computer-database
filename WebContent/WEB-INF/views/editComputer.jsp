<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
	<head>
		<title>Computer Database</title>
		
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta charset="utf-8">
		
		<!-- Bootstrap -->
		<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
		<link href="css/font-awesome.css" rel="stylesheet" media="screen">
		<link href="css/main.css" rel="stylesheet" media="screen">
	</head>
	<body>
	    <header class="navbar navbar-inverse navbar-fixed-top">
	        <div class="container">
	            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
	        </div>
	    </header>
	    <section id="main">
	        <div class="container">
	            <div class="row">
	                <div class="col-xs-8 col-xs-offset-2 box">
	                    <div class="label label-default pull-right">
	                        id: ${ id }
	                    </div>
	                    <h1>Edit Computer</h1>
	
	                    <form action="editComputer" method="POST">
	                        <input type="hidden" value="${ id }" id="id"/>
	                        <fieldset>
	                            <div class="form-group">
	                                <label for="computerName">Computer name</label>
	                                <input type="text" class="form-control" id="computerName" placeholder="Computer name" value="${ computerDetails.name }">
	                            </div>
	                            <div class="form-group">
	                                <label for="introduced">Introduced date</label>
	                                <input type="date" class="form-control" id="introduced" placeholder="Introduced date" value="${ computerDetails.introduced }">
	                            </div>
	                            <div class="form-group">
	                                <label for="discontinued">Discontinued date</label>
	                                <input type="date" class="form-control" id="discontinued" placeholder="Discontinued date" value="${ computerDetails.discontinued }">
	                            </div>
	                            <div class="form-group">
	                                <label for="companyId">Company</label>
	                                <select class="form-control" id="companyId" >
	                                    <option value="null" <c:if test="${ computerDetails.companyId == null }">selected</c:if>>--</option>
	                                    <c:forEach items="${ companyListObject }" var="v">
	                                    	<option value="${ v.id }" <c:if test="${ computerDetails.companyId == v.id }">selected</c:if>>${ v.name }</option>
	                                    </c:forEach>
	                                </select>
	                            </div>            
	                        </fieldset>
	                        <div class="actions pull-right">
	                            <input type="submit" value="Edit" class="btn btn-primary">
	                            or
	                            <a href="dashboard" class="btn btn-default">Cancel</a>
	                        </div>
	                    </form>
	                </div>
	            </div>
	        </div>
	    </section>
	
		<script src="${ pageContext.request.contextPath }/js/jquery.min.js"></script>
		<script src="${ pageContext.request.contextPath }/js/bootstrap.min.js"></script>
		
	</body>
</html>