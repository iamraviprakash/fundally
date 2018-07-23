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
            <a href="/faculty/"><div  id="active">Faculty</div></a>
            <a href="/request/"><div>Request</div></a>
            <a href="/update/password"><div>Update Password</div></a>
            <a href="/logout/"><div>Log out</div></a>
        </div>
    </div>
    
    <!-- main -->
    <div class="container-fluid" id="main">
        <div class="row" >
        	<div class="col-md-3"></div>
        	<div class="col-md-9">
        		${message}
        		<br>
        		<br>
        		${backUrl}
        		<table class="horizontalTable" style="margin: 40px; margin-left: 0px;">
        			<tr>
       				<td>Id</td><td>${displayProject.getId()}</td>
        			</tr>
        			<tr>
						<td>Name</td><td>${displayProject.getName()}</td> 
					</tr>
					<tr>  
						<td>Project In-charge</td><td>${displayProject.getProjectIncharge()}</td>
					</tr>
					<tr>
						<td>Sanctioned Amount</td><td>${displayProject.getSanctionedAmount()}</td> 
					</tr>
					<tr>
						<td>Description</td><td>${displayProject.getDescription()}</td>     		
					</tr>
					<tr>
						<td>Opening Date</td><td>${displayProject.getOpeningDate()}</td>     		
					</tr>
					<tr>
						<td>Closing Date</td><td>${displayProject.getClosingDate()}</td>     		
					</tr>
					<tr>
						<td>Status</td><td>${displayProject.getStatus()}</td>     		
					</tr>
					<tr>
						<td>Faculties Involved</td>
						<td>
							<c:forEach var="tempFacultyList" items="${facultyList}">
		        				<span>${tempFacultyList.getEmailId()}</span>, 
		        			</c:forEach>
						</td>     		
					</tr>
        		</table>
        		
	        	
	        	<div style="display: table; margin: auto;font-weight: bold; font-size: 1.2em;">Project Accounts</div>
        		<hr style="width: 50%;">
        		<table>
					<tr>
						<th>Account No</th>
						<th>Account Type</th>
						<th>Balance</th>
						<th>Options</th>
					</tr>
					
					<c:forEach var="tempDisplayProjectAccountList" items="${displayAccountList}">
						<tr>
							<td>${tempDisplayProjectAccountList.getAccountNo()}</td>
							<td>${tempDisplayProjectAccountList.getAccountType()}</td>
							<td>${tempDisplayProjectAccountList.getBalance()}</td>
							<td>
								<ul type="none">
									<li><a href="account/${tempDisplayProjectAccountList.getAccountNo()}/transaction/list"> Transactions </a></li>
									<li><a href="account/${tempDisplayProjectAccountList.getAccountNo()}/request/list"> Requests </a></li>
								</ul>
	        				</td>
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