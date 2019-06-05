<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="app.header.title"/></title>
		
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
	            <a class="navbar-brand" href="dashboard"> <spring:message code="app.title"/> </a>
	        </div>
	    </header>
	    <section id="main">
	        <div class="container">
	            <div class="row">
	                <div class="col-xs-8 col-xs-offset-2 box">
	                    <div class="label label-default pull-right">
	                        id: ${ id }
	                    </div>
	                    <h1><spring:message code="app.editComputer.title"/></h1>
						<div class="alert alert-danger" id="errorContainer">
							<div id="errorMessage"></div>
						</div>
	
	                    <form action="editComputer" method="POST">
	                        <input type="hidden" value="${ id }" id="id" name="id"/>
	                        <fieldset>
	                            <div class="form-group">
	                                <label for="computerName"><spring:message code="app.list.computerName"/></label>
	                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="<spring:message code="app.list.computerName"/>" value="${ computerDetails.name }">
	                            </div>
	                            <div class="form-group">
	                                <label for="introduced"><spring:message code="app.list.introduced"/></label>
	                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="<spring:message code="app.list.introduced"/>" value="${ computerDetails.introduced }">
	                            </div>
	                            <div class="form-group">
	                                <label for="discontinued"><spring:message code="app.list.discontinued"/></label>
	                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="<spring:message code="app.list.discontinued"/>" value="${ computerDetails.discontinued }">
	                            </div>
	                            <div class="form-group">
	                                <label for="companyId"><spring:message code="app.list.companyName"/></label>
	                                <select class="form-control" id="companyId" name="companyId">
	                                    <option value="" <c:if test="${ computerDetails.companyId == null }">selected</c:if>>--</option>
	                                    <c:forEach items="${ companyListObject }" var="v">
	                                    	<option value="${ v.id }" <c:if test="${ computerDetails.companyId == v.id }">selected</c:if>>${ v.name }</option>
	                                    </c:forEach>
	                                </select>
	                            </div>            
	                        </fieldset>
	                        <div class="actions pull-right">
	                            <input type="submit" id="submit" value="<spring:message code="app.button.edit"/>" class="btn btn-primary">
	                            <spring:message code="app.or"/>
	                            <a href="dashboard" class="btn btn-default"><spring:message code="app.button.cancel"/></a>
	                        </div>
	                    </form>
	                </div>
	            </div>
	        </div>
	    </section>
	
		<script src="${ pageContext.request.contextPath }/js/jquery.min.js"></script>
		<script src="${ pageContext.request.contextPath }/js/bootstrap.min.js"></script>
		<script src="${ pageContext.request.contextPath }/js/validation.js"></script>
		
	</body>
</html>