
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.profile.ProfileBean"%>

<%
    ProfileBean p = (ProfileBean) request.getAttribute("profile");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Student Profile</title>
    <meta charset="UTF-8">

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f0f4f8;
            padding: 30px;
        }

        .container {
            max-width: 600px;
            margin: auto;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        h1 {
            text-align: center;
            color: #234e70;
        }

        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }

        input, textarea {
            width: 100%;
            padding: 10px;
            margin-top: 6px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-family: Arial, sans-serif;
        }

        .btn-update {
            margin-top: 20px;
            padding: 12px 20px;
            background: #234e70;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
        }

        .btn-back {
            display: inline-block;
            margin-top: 20px;
            color: #234e70;
            text-decoration: none;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Edit Student Profile</h1>

    <form action="ProfileServlet" method="POST">

        <input type="hidden" name="action" value="update">

        <label>Student ID</label>
        <input type="text"
               name="studentID"
               value="<%= p.getStudentID() %>"
               readonly>

        <label>Name</label>
        <input type="text"
               name="name"
               value="<%= p.getName() %>"
               required>

        <label>Programme</label>
        <input type="text"
               name="programme"
               value="<%= p.getProgramme() %>"
               required>

        <label>Email</label>
        <input type="email"
               name="email"
               value="<%= p.getEmail() %>"
               required>

        <label>Hobbies</label>
        <input type="text"
               name="hobbies"
               value="<%= p.getHobbies() %>">

        <label>Introduction</label>
        <textarea name="introduction"
                  rows="5"><%= p.getIntroduction() %></textarea>

        <button type="submit" class="btn-update">
            Update Profile
        </button>

    </form>

    <a href="ProfileServlet" class="btn-back">
        Back to All Profiles
    </a>

</div>

</body>
</html>
