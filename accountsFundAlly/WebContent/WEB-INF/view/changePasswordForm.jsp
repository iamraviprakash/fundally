<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
        <link rel="stylesheet" href="/resources/css/loginStyle.css">
	</head>
<body>
    <!-- header -->
    <div class="container-fluid" id="header">
		<div class="row" >
            <!-- <div class="col-xs-1" style="text-align:right;">
                <button onclick="showHideSidepane()">
                    <i class="material-icons">dehaze</i>
                </button>
            </div>
            -->
            <div class="col-xs-2" style="text-align:center;">
                <div class="logo-text"><span>Fund</span><span>Ally</span></div>
            </div>
			<div class="col-xs-10">
                <div>Contact</div>
                <div>Help</div>
			</div>
		</div>
    </div>
    
   
    <!-- main -->
    <div class="container-fluid" id="main">
        <div class="row">
        	<div class="col-xs-3"></div>
            <div class="col-xs-6" style="text-align: center;">
            	<div class="logo">
            		<div class="logo-text"><span style="color: black;">Fund</span><span>Ally</span></div>
            	</div>
            	<br>
            	<br>
            	<div style="display:table; margin:auto;margin-top:10px; margin-bottom:10px;">${message}</div>
            	<!-- form -->
                <form method="post" action="${userType}/submit?sessionId=${sessionId}">
               		<input type="password" name="newPassword" placeholder="Enter New Password"/>
               		<input type="password" name="confirmPassword" placeholder="Enter Confirm Password"/>
                    <input type="submit" value="Update Password" />
                </form>
            </div>
            <div class="col-xs-3"></div>
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