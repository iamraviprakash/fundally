<% 

if( request.getParameter("token") != null){
	Cookie sessionId = new Cookie("sessionId", request.getParameter("token"));
	response.addCookie(sessionId);
}

response.sendRedirect("request/"); 

%>