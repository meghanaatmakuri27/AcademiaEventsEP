<%@page import="com.klef.project.models.Student"%>
<%@ page import="java.sql.*"%>
<%
Student stu = (Student) session.getAttribute("stu");
if (stu == null) {
    response.sendRedirect("sessionexpiry.html");
    return; // Ensure the rest of the JSP doesn't execute
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<a href="studenthome.jsp">Home</a>&nbsp;&nbsp;&nbsp;
<a href="studentupdate.jsf">Update Profile</a>&nbsp;&nbsp;&nbsp;
<a href="profile.jsp">My Profile</a>&nbsp;&nbsp;&nbsp;
<a href="studentlogout.jsp">Logout</a>&nbsp;&nbsp;&nbsp;</br></br>
Welcome <%= stu.getName() %>
</body>
</html>
