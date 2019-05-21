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
				<a class="navbar-brand" href="dashboard"> Application - Computer
					Database </a>
			</div>
		</header>
	
		<section id="main">
			<div class="container">
				<h1 id="homeTitle">${ totalNumberOfComputer } Computers found <c:if test="${ search != null }">for "${ search }"</c:if></h1>
				<div id="actions" class="form-horizontal">
					<div class="pull-left">
						<form id="searchForm" action="#" method="GET" class="form-inline">
	
							<input type="search" id="searchbox" name="search"
								class="form-control" placeholder="Search name" /> <input
								type="submit" id="searchsubmit" value="Filter by name"
								class="btn btn-primary" />
						</form>
					</div>
					<div class="pull-right">
						<a class="btn btn-success" id="addComputer" href="addComputer">Add
							Computer</a> <a class="btn btn-default" id="editComputer" href="#"
							onclick="$.fn.toggleEditMode();">Edit</a>
					</div>
				</div>
			</div>
	
			<form id="deleteForm" action="#" method="POST">
				<input type="hidden" name="selection" value="">
			</form>
	
			<div class="container" style="margin-top: 10px;">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<!-- Variable declarations for passing labels as parameters -->
							<!-- Table header for Computer Name -->
	
							<th class="editMode" style="width: 60px; height: 22px;"><input
								type="checkbox" id="selectall" /> <span
								style="vertical-align: top;"> - <a href="#"
									id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
										class="fa fa-trash-o fa-lg"></i>
								</a>
							</span></th>
							<th>Computer name <button type="button" class="btn glyphicon glyphicon-chevron-up btn-primary btn-sm"></button><button type="button" class="btn glyphicon glyphicon-chevron-down btn-primary btn-sm"></button></th>
							<th>Introduced date <button type="button" class="btn glyphicon glyphicon-chevron-up btn-primary btn-sm"></button><button type="button" class="btn glyphicon glyphicon-chevron-down btn-primary btn-sm"></button></th>
							<!-- Table header for Discontinued Date --> 
							<th>Discontinued date <button type="button" class="btn glyphicon glyphicon-chevron-up btn-primary btn-sm"></button><button type="button" class="btn glyphicon glyphicon-chevron-down btn-primary btn-sm"></button></th>
							<!-- Table header for Company -->
							<th>Company <button type="button" class="btn glyphicon glyphicon-chevron-up btn-primary btn-sm"></button><button type="button" class="btn glyphicon glyphicon-chevron-down btn-primary btn-sm"></button></th>
	
						</tr>
					</thead>
					<!-- Browse attribute computers -->
					<tbody id="results">
						<c:forEach items="${ computerListObject }" var="v">
							<tr id="container">
								<td class="editMode"><input type="checkbox" name="cb"
									class="cb" value="${ v.id }"></td>
								<td><a href="editComputer?id=${ v.id }" onclick=""> <c:out
											value="${ v.name }" /></a></td>
								<td><c:out value="${ v.introduced }" /></td>
								<td><c:out value="${ v.discontinued }" /></td>
								<td><c:out value="${ v.companyName }" /></td>
	
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</section>
	
		<footer class="navbar-fixed-bottom">
			<div class="container text-center">
				<ul class="pagination">
					<c:if test="${ pageNumber != 1 }">
						<li><a href="?page=${ pageNumber - 1 }" aria-label="Previous"> <span
								aria-hidden="true">&laquo;</span>
						</a></li>
					</c:if>
					
					<c:forEach var="i" begin="1" end="${ totalNumberOfPages }">
						<c:if test="${ i == 1 or i == totalNumberOfPages or i == pageNumber or (i <= (pageNumber + 2) and i >= (pageNumber - 2))}">
							<li><a href="?page=${ i }<c:if test="${ search != null }">&search=${ search }</c:if>" <c:if test="${ i == pageNumber }">class="noclick"</c:if>>${ i }</a></li>
							<c:if test="${ i < (pageNumber - 2) or (i == (pageNumber + 2) and i != totalNumberOfPages and (i + 1) != totalNumberOfPages) }">
								<li><a href="" class="noclick">...</a></li>
							</c:if>
						</c:if>
					</c:forEach>
					
					<c:if test="${ pageNumber != totalNumberOfPages }">
						<li><a href="?page=${ pageNumber + 1 }" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
							</a></li>
					</c:if>
				</ul>
	
				<div class="btn-group btn-group-sm pull-right" role="group">
					<button type="button" class="btn btn-default" id="tenElements" value="10" onClick="$.fn.toggleNumberOfElementPrinted(id);" 
						<c:if test="${ numberOfElementsToPrint == '10' }">disabled</c:if>>10</button>
					<button type="button" class="btn btn-default" id="fiftyElements" value="50" onClick="$.fn.toggleNumberOfElementPrinted(id);" 
						<c:if test="${ numberOfElementsToPrint == '50' }">disabled</c:if>>50</button>
					<button type="button" class="btn btn-default" id="hundredElements" value="100" onClick="$.fn.toggleNumberOfElementPrinted(id);" 
						<c:if test="${ numberOfElementsToPrint == '100' }">disabled</c:if>>100</button>
				</div>
			</div>
		</footer>
	
		<script src="${ pageContext.request.contextPath }/js/jquery.min.js"></script>
		<script src="${ pageContext.request.contextPath }/js/bootstrap.min.js"></script>
		<script src="${ pageContext.request.contextPath }/js/dashboard.js"></script>
	
	</body>
</html>