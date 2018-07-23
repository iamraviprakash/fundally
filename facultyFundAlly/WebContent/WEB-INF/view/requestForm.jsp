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
            <a href="/institute/"><div ${activeInstitute}>Institute</div></a>
            <a href="/project/"><div ${activeProject}>Project</div></a>
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
        		${backUrl}
        		<br>
        		<br>
        		${message}
        		<br>
        		<br>
	        	<form:form method="post" action="new/add" modelAttribute="newResourceRequestFormData">
	        		<div>Resource Details -------------</div>
	        		<form:input type="text" placeholder="Resource Name" path="name"/>
	        		
	        		<form:textarea placeholder="Resource Details .." path="description"/>
	        		Resource Type: 
	        		<form:select path="type">
	        			<form:option value="TANGIBLE">Tangible</form:option>
	        			<form:option value="INTANGIBLE">Intangible</form:option>
	        		</form:select>
	        		
	        		<form:input type="text" placeholder="Resource Amount" path="amount"/>
	        	
	        		<form:textarea placeholder="Why do you want this?" path="reason"/>
	        		
	        		<form:input placeholder="YYYY-MM-DD" path="deadline"/>
	        		
	        		<input type="submit" value="Submit Request"/>
	        	</form:form>
   
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