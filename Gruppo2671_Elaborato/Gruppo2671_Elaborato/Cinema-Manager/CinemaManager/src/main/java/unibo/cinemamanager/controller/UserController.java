package unibo.cinemamanager.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unibo.cinemamanager.DatabaseConnection;
import unibo.cinemamanager.Model.User;

/**
 * UserController is responsible for handling user-related operations,
 * such as registering users, retrieving user information, updating and deleting users.
 */
public class UserController {

    private static final int FIRST_NAME_INDEX = 1;
    private static final int LAST_NAME_INDEX = 2;
    private static final int EMAIL_INDEX = 3;
    private static final int PASSWORD_INDEX = 4;
    private static final int PREFERRED_GENRES_INDEX = 5;
    private static final int PRIORITY_LEVEL_INDEX = 6;
    private static final int USER_TYPE_INDEX = 7;
    private static final int ID_INDEX = 7; // For update query

    /**
     * Registers a new user in the database.
     *
     * @param user the User object containing user information
     * @throws SQLException if any SQL error occurs
     */
    public void registerUser(final User user) throws SQLException {
        String query = "INSERT INTO users (first_name, last_name, email, password, "
                     + "preferred_genres, priority_level, user_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(FIRST_NAME_INDEX, user.getFirstName());
            stmt.setString(LAST_NAME_INDEX, user.getLastName());
            stmt.setString(EMAIL_INDEX, user.getEmail());
            stmt.setString(PASSWORD_INDEX, user.getPassword());
            stmt.setString(PREFERRED_GENRES_INDEX, user.getPreferredGenres());
            stmt.setInt(PRIORITY_LEVEL_INDEX, user.getPriorityLevel());
            stmt.setString(USER_TYPE_INDEX, user.getUserType());
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves a user from the database by their email.
     *
     * @param email the email of the user to be retrieved
     * @return a User object if found, null otherwise
     * @throws SQLException if any SQL error occurs
     */
    public User getUserByEmail(final String email) throws SQLException {
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

    /**
     * Retrieves all users from the database.
     *
     * @return a list of User objects
     * @throws SQLException if any SQL error occurs
     */
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

    /**
     * Updates the information of an existing user.
     *
     * @param user the User object containing updated user information
     * @throws SQLException if any SQL error occurs
     */
    public void updateUser(final User user) throws SQLException {
        String query = "UPDATE users SET first_name = ?, last_name = ?, email = ?, "
                     + "preferred_genres = ?, priority_level = ?, user_type = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(FIRST_NAME_INDEX, user.getFirstName());
            stmt.setString(LAST_NAME_INDEX, user.getLastName());
            stmt.setString(EMAIL_INDEX, user.getEmail());
            stmt.setString(PREFERRED_GENRES_INDEX, user.getPreferredGenres());
            stmt.setInt(PRIORITY_LEVEL_INDEX, user.getPriorityLevel());
            stmt.setString(USER_TYPE_INDEX, user.getUserType());
            stmt.setInt(ID_INDEX, user.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a user from the database.
     *
     * @param id the ID of the user to be deleted
     * @throws SQLException if any SQL error occurs
     */
    public void deleteUser(final int id) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
/**
 * Blocks a user in the database.
 *
 * @param userId the ID of the user to be blocked
 * @throws SQLException if any SQL error occurs
 */
public void blockUser(int userId) throws SQLException {
    String query = "UPDATE users SET user_type = 'blocked' WHERE id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, userId);
        stmt.executeUpdate();
    }
}

/**
 * Unblocks a user in the database.
 *
 * @param userId the ID of the user to be unblocked
 * @throws SQLException if any SQL error occurs
 */
public void unblockUser(int userId) throws SQLException {
    String query = "UPDATE users SET user_type = 'User' WHERE id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, userId);
        stmt.executeUpdate();
    }
}

}
