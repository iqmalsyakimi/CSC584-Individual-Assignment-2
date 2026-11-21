<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <h1 style="color:#dc2626;">⚠️ Oops!</h1>
        <div class="card" style="border-left-color:#dc2626;">
            <p><%= request.getAttribute("errorMessage") %></p>
        </div>
        <a href="index.html" class="btn">← Try Again</a>
        <a href="viewProfile.jsp" class="btn">View All Profiles</a>
    </div>
</body>
</html>