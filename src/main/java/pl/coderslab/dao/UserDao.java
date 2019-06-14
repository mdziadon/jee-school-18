package pl.coderslab.dao;


import pl.coderslab.model.User;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(user_group_id, username, email, password, admin) VALUES (?, ?, ?, ?, ?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users where id = ?";
    private static final String READ_USER_BY_EMAIL_QUERY =
            "SELECT * FROM users WHERE email = ?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET user_group_id = ?, username = ?, email = ?, password = ?, admin = ? where id = ?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL_USERS_QUERY =
            "SELECT * FROM users";
    private static final String FIND_ALL_USERS_BY_GROUP_ID_QUERY =
            "SELECT * FROM users WHERE user_group_id = ?";

    public User create(User user) {

        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat =
                    connect.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            preStat.setInt(1, user.getUserGroupId());
            preStat.setString(2, user.getUsername());
            preStat.setString(3, user.getEmail());
            preStat.setString(4, user.getPassword());
            preStat.setString(5, user.getAdmin());
            preStat.executeUpdate();
            ResultSet resultSet = preStat.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User read(int userId) {
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(READ_USER_QUERY);
            preStat.setInt(1, userId);
            ResultSet resultSet = preStat.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserGroupId(resultSet.getInt("user_group_id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setAdmin(resultSet.getString("admin"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User readByEmail(String userEmail){
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(READ_USER_BY_EMAIL_QUERY);
            preStat.setString(1, userEmail);
            ResultSet resultSet = preStat.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setAdmin(resultSet.getString("admin"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user) {
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(UPDATE_USER_QUERY);
            preStat.setInt(1, user.getUserGroupId());
            preStat.setString(2, user.getUsername());
            preStat.setString(3, user.getEmail());
            preStat.setString(4, user.getPassword());
            preStat.setString(5, user.getAdmin());
            preStat.setInt(6, user.getId());
            preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(DELETE_USER_QUERY);
            preStat.setInt(1, userId);
            preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User[] findAll() {
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(FIND_ALL_USERS_QUERY);
            User[] users = findAllgetInf(preStat);
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User[] findAllByGroupID(int groupID) {
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(FIND_ALL_USERS_BY_GROUP_ID_QUERY);
            preStat.setInt(1, groupID);
            User[] users = findAllgetInf(preStat);
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private User[] findAllgetInf (PreparedStatement preStat){
        User[] users = new User[0];
        try (ResultSet resultSet = preStat.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserGroupId(resultSet.getInt("user_group_id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setAdmin(resultSet.getString("admin"));
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
                e.printStackTrace();
                return null;
        }

    }

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = u;
        return tmpUsers;
    }

}
