
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, com.profile.ProfileBean" %>

<!DOCTYPE html>
<html>
<head>
    <title>All Profiles</title>
    <meta charset="UTF-8">

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f0f4f8;
            padding: 30px;
        }

        .container {
            max-width: 1100px;
            margin: auto;
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            overflow-x: auto;
        }

        h1 {
            text-align: center;
            color: #234e70;
        }

        .search-box {
            background: #f0f4f8;
            padding: 15px;
            border-radius: 8px;
            margin-top: 20px;
        }

        .search-box input {
            padding: 9px;
            margin: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .btn-search {
            background: #234e70;
            color: white;
            padding: 9px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .btn-clear {
            background: #64748b;
            color: white;
            padding: 9px 15px;
            text-decoration: none;
            border-radius: 5px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th {
            background: #234e70;
            color: white;
            padding: 12px;
            text-align: left;
        }

        td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            color: #333;
        }

        tr:hover {
            background: #f9f9f9;
        }

        .btn-delete {
            background: #dc2626;
            color: white;
            padding: 6px 12px;
            text-decoration: none;
            border-radius: 5px;
            font-size: 13px;
        }

        .btn-edit {
            background: #2563eb;
            color: white;
            padding: 6px 12px;
            text-decoration: none;
            border-radius: 5px;
            font-size: 13px;
        }

        .btn-add {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background: #234e70;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>All Student Profiles</h1>

    <!-- SEARCH AND FILTER FORM -->
    <form action="ProfileServlet" method="GET" class="search-box">

        <h3>Search Profile</h3>

        <input type="text"
               name="search"
               placeholder="Search Student ID or Name"
               value="<%= request.getParameter("search") == null ? "" : request.getParameter("search") %>">

        <h3>Filter Profiles</h3>

        <input type="text"
               name="programme"
               placeholder="Enter Programme"
               value="<%= request.getParameter("programme") == null ? "" : request.getParameter("programme") %>">

        <input type="text"
               name="hobbies"
               placeholder="Enter Hobbies"
               value="<%= request.getParameter("hobbies") == null ? "" : request.getParameter("hobbies") %>">

        <br><br>

        <button type="submit" class="btn-search">
            Search / Filter
        </button>

        <a href="ProfileServlet" class="btn-clear">
            Clear
        </a>

    </form>

    <!-- PROFILE TABLE -->
    <table>

        <tr>
            <th>Student ID</th>
            <th>Name</th>
            <th>Programme</th>
            <th>Email</th>
            <th>Hobbies</th>
            <th>Introduction</th>
            <th>Action</th>
        </tr>

        <%
            List<ProfileBean> profiles =
                (List<ProfileBean>) request.getAttribute("profiles");

            if (profiles != null && !profiles.isEmpty()) {

                for (ProfileBean p : profiles) {
        %>

        <tr>
            <td><%= p.getStudentID() %></td>
            <td><%= p.getName() %></td>
            <td><%= p.getProgramme() %></td>
            <td><%= p.getEmail() %></td>
            <td><%= p.getHobbies() %></td>
            <td><%= p.getIntroduction() %></td>

            <td>
                <!-- EDIT BUTTON -->
                <a href="ProfileServlet?action=edit&studentID=<%= p.getStudentID() %>"
                   class="btn-edit">
                    Edit
                </a>

                <!-- DELETE BUTTON -->
                <a href="ProfileServlet?action=delete&studentID=<%= p.getStudentID() %>"
                   class="btn-delete"
                   onclick="return confirm('Are you sure you want to delete this profile?');">
                    Delete
                </a>
            </td>
        </tr>

        <%
                }

            } else {
        %>

        <tr>
            <td colspan="7" style="text-align:center; padding:20px;">
                No profiles found in the database.
            </td>
        </tr>

        <% } %>

    </table>

    <a href="index.html" class="btn-add">
        + Add New Profile
    </a>

</div>

</body>
</html>
