package unibo.cinemamanager.controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import unibo.cinemamanager.DatabaseConnection;
import unibo.cinemamanager.Model.User;

public class UserController {
    public void registerUser(User user) throws SQLException {
        String query = "INSERT INTO users (first_name, last_name, email, password, preferred_genres, priority_level, user_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getPreferredGenres());
            stmt.setInt(6, user.getPriorityLevel());
            stmt.setString(7, user.getUserType());
            stmt.executeUpdate();
        }
    }

    public User getUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPreferredGenres(rs.getString("preferred_genres"));
                user.setPriorityLevel(rs.getInt("priority_level"));
                user.setUserType(rs.getString("user_type"));
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setPreferredGenres(resultSet.getString("preferred_genres"));
                user.setPriorityLevel(resultSet.getInt("priority_level"));
                user.setUserType(resultSet.getString("user_type"));
                users.add(user);
            }
        }
        return users;
    }

    public void updateUser(User user) throws SQLException {
        String query = "UPDATE users SET first_name = ?, last_name = ?, email = ?, preferred_genres = ?, priority_level = ?, user_type = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPreferredGenres());
            stmt.setInt(5, user.getPriorityLevel());
            stmt.setString(6, user.getUserType());
            stmt.setInt(7, user.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteUser(int id) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
