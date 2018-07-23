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
       		table{
       			margin: 20px;
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
            <a href="/institute/"><div ${activeInstitute}>Institute</div></a>
            <a href="/project/"><div ${activeProject}>Project</div></a>
            <a href="/update/password"><div>Update Password</div></a>
            <a href="/logout/"><div>Log out</div></a>
        </div>
    </div>
    
    <!-- main -->
    <div class="container-fluid" id="main">
        <div class="row" >
        	<div class="col-md-3"></div>
        	<div class="col-md-9">
        		${backUrl}
        		<br>
        		<br>
        		${message}
        		<br>
        		<br>
        		<br>
	        	<!-- request profile -->
	        	<div>Resource Details</div>
	        	<hr style="width:50%; margin-left: 0px;">
	        	<table class="horizontalTable">
	        		<tr><td>Resource Id</td><td>${resource.getId()}</td></tr>
	        		<tr><td>Name</td><td>${resource.getName()}</td></tr>
	        		<tr><td>Description</td><td>${resource.getDescription()}</td></tr>
	        		<tr><td>Amount</td><td>${resource.getAmount()}</td></tr>
	        		<tr><td>Deadline</td><td>${resource.getDeadlineDate()}</td></tr>
	        	</table>
	        
	        	<div>Request Details</div>
	        	<hr style="width:50%; margin-left: 0px;">
	        	<table class="horizontalTable">
	        		<tr><td>Request Id</td><td>${request.getId()}</td></tr>
	        		<tr><td>Account No</td><td>${request.getAccount().getAccountNo()}</td></tr>
	        		<tr><td>Reason</td><td>${request.getDescription()}</td></tr>
	        		<tr><td>Date of Creation</td><td>${request.getStartDate()}</td></tr>
	        		<tr><td>Date of Completion</td><td>${request.getEndDate()}</td></tr>
	        		<tr><td>Status</td><td>${request.getStatus().name()}</td></tr>
	   				<tr><td>Options</td><td>${issueUrl}</td></tr>
	        	</table>
	        	
	        	
	        	<div>Transaction Details</div>
	        	<hr style="width:50%; margin-left: 0px;">
	        	<table>
					<tr>
						<th>Transaction Id</th>
						<th>Opening Balance</th>
						<th>Closing Balance</th>
						<th>Time</th>
					</tr>
					<tr>
						<td>${transaction.getId()}</td>
						<td>${transaction.getOpeningBalance()}</td>
						<td>${transaction.getClosingBalance()}</td>
						<td>${transaction.getDate()}</td>
					</tr>
				</table>
	        	
	        	<div>Request Timeline</div>
	        	<hr style="width:50%; margin-left: 0px;">
	        	<table>
					<tr>
						<th>S.No</th>
						<th>Stage Name</th>
						<th>Date</th>
					</tr>
					<% int var=1; %>
					<c:forEach var="tempRequestStageList" items="${requestStageList}">
						<tr>
							<td><%= var %>.</td>
							<td>${tempRequestStageList.getStageType().getName()}</td>
							<td>${tempRequestStageList.getDate()}</td>
							<% var = var + 1; %>
						</tr>
					</c:forEach>
				</table>
				
				        	
	        	<div>Quotation Details</div>
	        	<hr style="width:50%; margin-left: 0px;">
	        	<table>
					<tr>
						<th>S.No</th>
						<th>Quotation Id</th>
						<th>Name</th>
						<th>Status</th>
					</tr>
					
					<% var=1; %>
					
					<c:forEach var="tempQuotationList" items="${quotationList}">
					
						<tr>
							<td><%= var %>.</td>
							<td>${tempQuotationList.getId()}</td>
							<c:if test="${source == 'institute' }">
								<td><a href="http://procurement.fundally.iiits.ac.in/static/pdf/institute/quotation/${tempQuotationList.getId()}/session/${sessionId}">Quotation_<%=var %></a></td>
							</c:if>
							<c:if test="${source == 'project' }">
								<td><a href="http://procurement.fundally.iiits.ac.in/static/pdf/project/quotation/${tempQuotationList.getId()}/session/${sessionId}">Quotation_<%=var %></a></td>
							</c:if>
							<td>${tempQuotationList.getStatus().toString()}</td>
						</tr>
						<% var = var + 1; %>
					</c:forEach>
				</table>
	        	
	        	<div>Resource Document</div>
	        	<hr style="width:50%; margin-left: 0px;">
	        	<!-- path where file has been uploaded -->
	        	<table>
					<tr>
						<th>S.No</th>
						<th>Resource Document Id</th>
						<th>Name</th>
					</tr>
					
					<% var=1; %>
					
					<c:forEach var="tempResourceDocumentList" items="${resourceDocumentList}">
					
						<tr>
							<td><%= var %>.</td>
							<td>${tempResourceDocumentList.getId()}</td>
							<c:if test="${source == 'institute' }">
								<td><a href="http://procurement.fundally.iiits.ac.in/static/pdf/institute/resourceDocument/${tempResourceDocumentList.getId()}/session/${sessionId}">Document_<%=var %></a></td>
							</c:if>	
							<c:if test="${source == 'project' }">
								<td><a href="http://procurement.fundally.iiits.ac.in/static/pdf/project/resourceDocument/${tempResourceDocumentList.getId()}/session/${sessionId}">Document_<%=var %></a></td>
							</c:if>						
						</tr>
						<% var = var + 1; %>
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