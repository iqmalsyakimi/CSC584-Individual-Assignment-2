
package com.profile;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/ProfileServlet"})
public class ProfileServlet extends HttpServlet {

    // ---------- CREATE AND UPDATE ----------
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        String name = request.getParameter("name");
        String studentId = request.getParameter("studentID");
        String programme = request.getParameter("programme");
        String email = request.getParameter("email");
        String hobbies = request.getParameter("hobbies");
        String introduction = request.getParameter("introduction");

        try (Connection conn = DBConnection.getConnection()) {

            // UPDATE PROFILE
            if ("update".equals(action)) {

                String sql = "UPDATE Profile SET name=?, programme=?, "
                           + "email=?, hobbies=?, introduction=? "
                           + "WHERE studentID=?";

                try (PreparedStatement ps = conn.prepareStatement(sql)) {

                    ps.setString(1, name);
                    ps.setString(2, programme);
                    ps.setString(3, email);
                    ps.setString(4, hobbies);
                    ps.setString(5, introduction);
                    ps.setString(6, studentId);

                    ps.executeUpdate();
                }

                response.sendRedirect("ProfileServlet");
                return;
            }

            // CREATE PROFILE
            String checkSql =
                "SELECT studentID FROM Profile WHERE studentID=?";

            try (PreparedStatement check = conn.prepareStatement(checkSql)) {

                check.setString(1, studentId);

                try (ResultSet rs = check.executeQuery()) {

                    if (rs.next()) {
                        request.setAttribute(
                            "errorMessage",
                            "Student ID '" + studentId + "' already exists."
                        );

                        request.getRequestDispatcher("error.jsp")
                               .forward(request, response);
                        return;
                    }
                }
            }

            String insertSql =
                "INSERT INTO Profile "
                + "(studentID, name, programme, email, hobbies, introduction) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {

                ps.setString(1, studentId);
                ps.setString(2, name);
                ps.setString(3, programme);
                ps.setString(4, email);
                ps.setString(5, hobbies);
                ps.setString(6, introduction);

                ps.executeUpdate();
            }

            response.sendRedirect(
                "ProfileServlet?action=viewOne&studentID="
                + URLEncoder.encode(studentId, "UTF-8")
            );

        } catch (SQLException e) {
            throw new ServletException("Database error occurred.", e);
        }
    }

    // ---------- READ, SEARCH, FILTER AND DELETE ----------
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try (Connection conn = DBConnection.getConnection()) {

            // DELETE PROFILE
            if ("delete".equals(action)) {

                String id = request.getParameter("studentID");

                String sql = "DELETE FROM Profile WHERE studentID=?";

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, id);
                    ps.executeUpdate();
                }

                response.sendRedirect("ProfileServlet");
                return;
            }

            // EDIT PROFILE - LOAD EXISTING DATA
            if ("edit".equals(action)) {

                String id = request.getParameter("studentID");

                String sql = "SELECT * FROM Profile WHERE studentID=?";

                try (PreparedStatement ps = conn.prepareStatement(sql)) {

                    ps.setString(1, id);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (rs.next()) {

                            ProfileBean profile = new ProfileBean();

                            profile.setStudentID(rs.getString("studentID"));
                            profile.setName(rs.getString("name"));
                            profile.setProgramme(rs.getString("programme"));
                            profile.setEmail(rs.getString("email"));
                            profile.setHobbies(rs.getString("hobbies"));
                            profile.setIntroduction(rs.getString("introduction"));

                            request.setAttribute("profile", profile);

                            request.getRequestDispatcher("editProfile.jsp")
                                   .forward(request, response);
                            return;
                        }
                    }
                }

                response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Profile not found."
                );
                return;
            }

            // VIEW ONE PROFILE
            if ("viewOne".equals(action)) {

                String id = request.getParameter("studentID");

                String sql = "SELECT * FROM Profile WHERE studentID=?";

                try (PreparedStatement ps = conn.prepareStatement(sql)) {

                    ps.setString(1, id);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (rs.next()) {

                            ProfileBean profile = new ProfileBean();

                            profile.setStudentID(rs.getString("studentID"));
                            profile.setName(rs.getString("name"));
                            profile.setProgramme(rs.getString("programme"));
                            profile.setEmail(rs.getString("email"));
                            profile.setHobbies(rs.getString("hobbies"));
                            profile.setIntroduction(rs.getString("introduction"));

                            request.setAttribute("profile", profile);

                            request.getRequestDispatcher("profile.jsp")
                                   .forward(request, response);
                            return;
                        }
                    }
                }

                response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Profile not found."
                );
                return;
            }

            // SEARCH AND FILTER / VIEW ALL
            List<ProfileBean> profiles = new ArrayList<>();

            String search = request.getParameter("search");
            String programmeFilter = request.getParameter("programme");
            String hobbiesFilter = request.getParameter("hobbies");

            StringBuilder sql = new StringBuilder(
                "SELECT * FROM Profile WHERE 1=1"
            );

            if (search != null && !search.trim().isEmpty()) {
                sql.append(" AND (studentID LIKE ? OR name LIKE ?)");
            }

            if (programmeFilter != null && !programmeFilter.trim().isEmpty()) {
                sql.append(" AND programme LIKE ?");
            }

            if (hobbiesFilter != null && !hobbiesFilter.trim().isEmpty()) {
                sql.append(" AND hobbies LIKE ?");
            }

            sql.append(" ORDER BY studentID");

            try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {

                int index = 1;

                if (search != null && !search.trim().isEmpty()) {
                    ps.setString(index++, "%" + search.trim() + "%");
                    ps.setString(index++, "%" + search.trim() + "%");
                }

                if (programmeFilter != null && !programmeFilter.trim().isEmpty()) {
                    ps.setString(index++, "%" + programmeFilter.trim() + "%");
                }

                if (hobbiesFilter != null && !hobbiesFilter.trim().isEmpty()) {
                    ps.setString(index++, "%" + hobbiesFilter.trim() + "%");
                }

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {

                        ProfileBean profile = new ProfileBean();

                        profile.setStudentID(rs.getString("studentID"));
                        profile.setName(rs.getString("name"));
                        profile.setProgramme(rs.getString("programme"));
                        profile.setEmail(rs.getString("email"));
                        profile.setHobbies(rs.getString("hobbies"));
                        profile.setIntroduction(rs.getString("introduction"));

                        profiles.add(profile);
                    }
                }
            }

            request.setAttribute("profiles", profiles);

            request.getRequestDispatcher("viewProfiles.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error occurred.", e);
        }
    }
}
