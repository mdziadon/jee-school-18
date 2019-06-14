package pl.coderslab.dao;

import pl.coderslab.model.Group;

import java.sql.*;
import java.util.Arrays;

public class GroupDao {
    private static final String CREATE_USER_GROUPS_QUERY =
            "INSERT INTO user_groups (name) VALUES (?)";
    private static final String READ_USER_GROUPS_QUERY =
            "SELECT * FROM user_groups WHERE id = ?";
    private static final String UPDATE_USER_GROUPS_QUERY =
            "UPDATE user_groups SET name = ? WHERE id = ?";
    private static final String DELETE_USER_GROUPS_QUERY =
            "DELETE FROM user_groups WHERE id = ?";
    private static final String FIND_ALL_USER_GROUPS_QUERY =
            "SELECT * FROM user_groups";

    public Group create (Group group){
        try (Connection connect = DbUtil.getConn()){
            PreparedStatement preStat = connect.prepareStatement(CREATE_USER_GROUPS_QUERY, Statement.RETURN_GENERATED_KEYS);
            preStat.setString(1, group.getName());
            preStat.executeUpdate();
            ResultSet resultSet = preStat.getGeneratedKeys();
            if (resultSet.next()){
                group.setId(resultSet.getInt(1));
            }
            return group;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Group read (int groupID){
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(READ_USER_GROUPS_QUERY);
            preStat.setInt(1, groupID);
            ResultSet resultSet = preStat.executeQuery();
            if (resultSet.next()){
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                return group;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update (Group group){
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(UPDATE_USER_GROUPS_QUERY);
            preStat.setString(1, group.getName());
            preStat.setInt(2, group.getId());
            preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete (int groupID){
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(DELETE_USER_GROUPS_QUERY);
            preStat.setInt(1, groupID);
            preStat.executeUpdate();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }

    public Group[] findAll() {
        try (Connection connect = DbUtil.getConn()) {
            Group[] groups = new Group[0];
            PreparedStatement preStat = connect.prepareStatement(FIND_ALL_USER_GROUPS_QUERY);
            ResultSet resultSet = preStat.executeQuery();
            while (resultSet.next()){
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                groups = addToArray(group, groups);
            }
            return groups;
        } catch (SQLException e) {
                e.printStackTrace();
                return null;
        }
    }

    private Group[] addToArray(Group group, Group[] groups) {
        Group[] tmpGroups = Arrays.copyOf(groups, groups.length + 1);
        tmpGroups[groups.length] = group;
        return tmpGroups;
    }

}
