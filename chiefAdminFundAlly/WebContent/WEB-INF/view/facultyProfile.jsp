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
            <a href="/faculty/"><div id="active" >Faculty</div></a>
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
        		<a href="/faculty/">Back To List</a>
        		<table class="horizontalTable" style="margin: 40px; margin-left: 0px;">
        			<tr>
        				<td>Id</td><td>${faculty.getId()}</td>
        			</tr>
        			<tr>
						<td>Name</td><td>${faculty.getFirstName()} ${faculty.getLastName()}</td> 
					</tr>
					<tr>  
						<td>Email Id</td><td>${faculty.getEmailId()}</td>
					</tr>
					<tr>
						<td>Department</td><td>${faculty.getDepartment()}</td>     		
					</tr>
        		</table>
        		
        		<div style="font-weight: bold; font-size: 1.2em;">Institute Accounts</div>
        		<hr style="width: 50%; margin-left: 0px;">
        		<table>
					<tr>
						<th>Account No</th>
						<th>Account Type</th>
						<th>Balance</th>
						<th>Options</th>
					</tr>
					
					<c:forEach var="tempDisplayInstituteAccountList" items="${displayAccountList}">
						<tr>
							<td>${tempDisplayInstituteAccountList.getAccountNo()}</td>
							<td>${tempDisplayInstituteAccountList.getAccountType()}</td>
							<td>${tempDisplayInstituteAccountList.getBalance()}</td>
							<td>
								<ul type="none">
									<li><a href="account/${tempDisplayInstituteAccountList.getAccountNo()}/transaction/list"> Transactions </a></li>
									<li><a href="account/${tempDisplayInstituteAccountList.getAccountNo()}/request/list"> Requests </a></li>
								</ul>
	        				</td>
						</tr>
					</c:forEach>
				</table>
				
				<div style="display: table; margin: auto; font-weight: bold; font-size: 1.2em; margin-top:25px; margin-bottom:25px;">List of Projects</div>
				<hr style="width:50%;">
				<table>
					<tr>
						<th>Project Id</th>
						<th>Name</th>
						<th>Project In-charge</th>
						<th>Sanctioned Amount</th>
						<th>Options</th>
					</tr>
					
					<c:forEach var="tempProjectList" items="${displayProjectList}">
						<tr>
							<td>${tempProjectList.getId()}</td>
							<td>${tempProjectList.getName()}</td>
							<td>${tempProjectList.getProjectIncharge()}</td>
							<td>${tempProjectList.getSanctionedAmount()}</td>
							<td><a href="project/${tempProjectList.getId()}/view">View</a></td>
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