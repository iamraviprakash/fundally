<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>
			FundAlly
		</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="/resources/css/style.css">
        <script src="/resources/js/script.js"></script>
       
       	<style>
       		#active{
       			background: var(--secondary-color);
    			color: var(--primary-color);
       		}
       	</style>
	</head>
<body>
    <!-- header -->
    <div class="container-fluid" id="header">
		<div class="row" >
            <div class="col-xs-1" style="text-align:right;">
                <button onclick="showHideSidepane()">
                    <i class="material-icons">dehaze</i>
                </button>
            </div>
            <div class="col-xs-1" style="text-align:left;">
                <div class="logo-text"><span>Fund</span><span>Ally</span></div>
            </div>
			<div class="col-xs-10">
                <div>Contact</div>
                <div>Help</div>
			</div>
		</div>
    </div>
    
    <!-- sidepane -->
    <div id="sidepane" style="display: block;">
        <div id="profile">
            <div>${ Name }</div>
            <div>${ Department }</div>
        </div>
         <div class="list">
            <a href="/faculty/"><div id="active">Faculty</div></a>
            <a href="/request/"><div>Request</div></a>
            <a href="/update/password"><div>Update Password</div></a>
            <a href="/logout/"><div>Log out</div></a>
        </div>
    </div>
    
    <!-- main -->
    <div class="container-fluid" id="main">
    	<!-- Add Faculty form -->
        <div class="row" >
        	<div class="col-md-3"></div>
        	<div class="col-md-9">
        		<div>${message}</div>
        		<br>
        		<br>
				<div style="display: table; margin: auto; font-weight: bold; font-size: 1.2em; margin-top:25px; margin-bottom:25px;">List of faculties branch wise</div>
				<hr style="width:50%;">
				<div style="font-weight: bold; display: table; margin: auto;">CSE</div>
				<hr style="width:20%;">
				<table>
					<tr>
						<th>User Id</th>
						<th>Name</th>
						<th>Email Id</th>
						<th>Options</th>
					</tr>
					
					<c:forEach var="tempCSEFacultyList" items="${cseFacultyList}">
						<tr>
							<td>${tempCSEFacultyList.getId()}</td>
							<td>${tempCSEFacultyList.getFirstName()} ${tempCSEFacultyList.getLastName()}</td>
							<td>${tempCSEFacultyList.getEmailId()}</td>
							<td><a href="${tempCSEFacultyList.getId()}/view">View</a></td>
						</tr>
					</c:forEach>
				</table>
				<br>
				<br>
				<div style="font-weight: bold; display: table; margin: auto;">ECE</div>
				<hr style="width:20%;">
				<table>
					<tr>
						<th>User Id</th>
						<th>Name</th>
						<th>Email Id</th>
						<th>Options</th>
					</tr>
					<c:forEach var="tempECEFacultyList" items="${eceFacultyList}">
						<tr>
							<td>${tempECEFacultyList.getId()}</td>
							<td>${tempECEFacultyList.getFirstName()} ${tempECEFacultyList.getLastName()}</td>
							<td>${tempECEFacultyList.getEmailId()}</td>
							<td><a href="${tempECEFacultyList.getId()}/view">View</a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
    </div>
    
    
    <!-- footer -->
    <div class="container-fluid" id="footer">
		<div class="row" >
           <div class="col-xs-12 bar"></div>
		</div>
        <div class="row" >
            <div class="col-xs-2 logo">
                <div class="logo-text"><span>Fund</span><span>Ally</span></div>
            </div>
            <div class="col-xs-10">
                <div class="copyright">Copyright 2018</div>
            </div>
		</div>
    </div>
</body>
</html>