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
               <a href="/projects/">
	               <button>
	                    <i class="material-icons">dehaze</i>
	               </button>
               </a>
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
            <div>${name}</div>
            <div>${department}</div>
        </div>
        <div class="list">
            <a href="/institute/"><div>Institute</div></a>
            <a href="/project/"><div  id="active">Project</div></a>
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
	        	<!-- form to add new project -->
	        	<div style="display: table; margin: 20px; margin: auto;">${message}</div>
	        	<br>
	        	<br>
	        	<div style="display: table; margin: auto;font-weight: bold; font-size: 1.2em;">Add Project</div>
				<hr style="width:50%;">
        		<form:form method="post" action="add" modelAttribute="newProjectFormData">
	        		<form:input type="text" placeholder="Project Name" path="name"/>
	        		<form:textarea placeholder="Write about the project." path="description"/>
	        		<form:input placeholder="Deadline(YYYY-MM-DD)" path="closingDate"/>
	        		
	        		<input type="submit" value="Add"/>
	        	</form:form>
        	
        	
        	
        		<!-- Form to add account type -->
        		<div style="display: table; margin: auto;font-weight: bold; font-size: 1.2em;">Add Account Type</div>
				<hr style="width:50%;">
        		<form:form method="post" action="accountType/add" modelAttribute="projectAccountType">
	        		<form:input type="text" placeholder="Enter Id(Acronym. E.g. SGF: Seed Grant Fund)" path="id"/>
	        		<form:input type="text" placeholder="Enter Name(Full Form)" path="name"/>
	        		<form:textarea placeholder="Enter Description" path="description"/>
	        		
	        		<input type="submit" value="Add"/>
	        	</form:form>
        		
        		
        		
        		<!-- form to refill the account -->
        		<div style="display: table; margin: auto;font-weight: bold; font-size: 1.2em;">Refill Account</div>
				<hr style="width:50%;">
        		<form method="post" action="refillAccount">
        			<input type="text" name="accountNo" placeholder="Enter Account No."/>
        			<input type="text" name="amount" placeholder="Enter Amount"/>
        			
        			<input type="submit" value="Refill"/>
        		</form>
        		
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
							<td><a href="${tempProjectList.getId()}/view">View</a></td>
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