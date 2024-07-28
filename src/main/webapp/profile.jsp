<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.klef.project.models.Student"%>
<%@ page import="java.sql.*"%>
<%
    Student stu = (Student) session.getAttribute("stu");
    if (stu == null) {
        response.sendRedirect("sessionexpiry.html");
    }
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
      xmlns:f="http://xmlns.jcp.org/jsf/core">
<head>
    <meta charset="ISO-8859-1"/>
    <title>My Profile</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f0f2f5;
            margin: 0;
            padding: 0;
            display: flex;
            min-height: 100vh;
            flex-direction: row;
        }
        .navbar {
            background: linear-gradient(135deg, #000000, #333333);
            border-right: 4px solid #ffffff;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
            width: 250px;
            padding: 1rem;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            position: fixed;
            top: 0;
            left: 0;
        }
        .navbar a {
            color: #ffffff;
            text-decoration: none;
            font-size: 1em;
            margin: 10px 0;
            padding: 0.5rem;
            border-radius: 4px;
            transition: background-color 0.3s, color 0.3s;
        }
        .navbar a:hover {
            color: #000000;
            background-color: #ffffff;
        }
        .main-content {
            margin-left: 250px;
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 2rem;
        }
        .card {
            background: #ffffff;
            border-radius: 12px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            padding: 2rem;
            width: 100%;
            max-width: 600px;
            text-align: center;
            transition: transform 0.5s, box-shadow 0.5s;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .card:hover {
            transform: scale(1.05);
            box-shadow: 0 24px 48px rgba(0, 0, 0, 0.4);
        }
        .card h1 {
            font-size: 24px;
            color: #333;
            margin-bottom: 1rem;
        }
        .card p {
            font-size: 18px;
            color: #666;
            margin: 0.5rem 0;
        }
        .profile-photo {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            margin-bottom: 1rem;
        }
        .edit-profile-btn {
            margin-top: 1rem;
            padding: 0.5rem 1rem;
            font-size: 16px;
            color: #fff;
            background-color: #333;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .edit-profile-btn:hover {
            background-color: #000000;
        }
    </style>
</head>
<body>
    <div class="main-content">
        <div class="card">
            <h1>My Profile</h1>
            <img src="resources/profle.jpg" alt="Profile Photo" class="profile-photo"/>
            <p>ID: <%= stu.getId() %></p>
            <p>Name: <%= stu.getName() %></p>
            <p>Gender: <%= stu.getGender() %></p>
            <p>Department: <%= stu.getDepartment() %></p>
            <p>Email: <%= stu.getEmail() %></p>
            <p>Contact: <%= stu.getContact() %></p>
            <button class="edit-profile-btn" onclick="window.location.href='home.jsf'">Back to home page</button>
        </div>
    </div>
</body>
</html>
