package pl.coderslab.dao;

import pl.coderslab.model.Solution;

import java.sql.*;
import java.util.Arrays;

public class SolutionDao {

    private static final String CREATE_SOLUTION_QUERY =
            "INSERT INTO solutions (exercise_id, user_id, created, updated, description) VALUES (?, ?, ?, ?, ?)";
    private static final String READ_SOLUTION_QUERY =
            "SELECT * FROM solutions where id = ?";
    private static final String UPDATE_SOLUTION_QUERY =
            "UPDATE solutions SET exercise_id = ?, user_id = ?, updated = ?, description = ?";
    private static final String DELETE_SOLUTION_QUERY =
            "DELETE FROM solutions WHERE id = ?";
    private static final String FIND_ALL_SOLUTION_QUERY =
            "SELECT * FROM solutions";
    private static final String FIND_ALL_BY_USER_ID_QUERY =
            "SELECT * FROM solutions WHERE user_id = ?";
    private static final String FIND_ALL_BY_EXCLUSION_USER_ID_QUERY =
            "SELECT * FROM solutions WHERE user_id <> ?";
    private static final String FIND_ALL_BY_EXERCISE_ID_QUERY =
            "SELECT * FROM solutions WHERE exercise_id = ?";

    public Solution create(Solution solution) {
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(CREATE_SOLUTION_QUERY, Statement.RETURN_GENERATED_KEYS);
            preStat.setInt(1, solution.getExerciseId());
            preStat.setInt(2, solution.getUserId());
            preStat.setTimestamp(3, solution.getCreated());
            preStat.setTimestamp(4, solution.getUpdated());
            preStat.setString(5, solution.getDescription());
            preStat.executeUpdate();
            ResultSet resultSet = preStat.getGeneratedKeys();
            if (resultSet.next()) {
                solution.setId(resultSet.getInt(1));
            }
            return solution;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Solution read(int solutionID) {
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(READ_SOLUTION_QUERY);
            preStat.setInt(1, solutionID);
            ResultSet resultSet = preStat.executeQuery();
            if (resultSet.next()) {
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solution.setExerciseId(resultSet.getInt("exercise_id"));
                solution.setUserId(resultSet.getInt("user_id"));
                solution.setCreated(resultSet.getTimestamp("created"));
                solution.setUpdated(resultSet.getTimestamp("updated"));
                solution.setDescription(resultSet.getString("description"));
                return solution;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Solution solution) {
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(UPDATE_SOLUTION_QUERY);
            preStat.setInt(1, solution.getExerciseId());
            preStat.setInt(2, solution.getUserId());
            preStat.setTimestamp(3, solution.getUpdated());
            preStat.setString(4, solution.getDescription());
            preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int solutionID) {
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(DELETE_SOLUTION_QUERY);
            preStat.setInt(1, solutionID);
            preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Solution[] findAll() {
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(FIND_ALL_SOLUTION_QUERY);
            Solution[] solutions = findAllgetInf(preStat);
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Solution[] findAllByUserID(int userID) {
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(FIND_ALL_BY_USER_ID_QUERY);
            preStat.setInt(1, userID);
            Solution[] solutions = findAllgetInf(preStat);
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Solution[] findAllByExclusionUserID(int userID) {
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(FIND_ALL_BY_EXCLUSION_USER_ID_QUERY);
            preStat.setInt(1, userID);
            Solution[] solutions = findAllgetInf(preStat);
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Solution[] findAllByExerciseID(int exerciseID) {
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(FIND_ALL_BY_EXERCISE_ID_QUERY);
            preStat.setInt(1, exerciseID);
            Solution[] solutions = findAllgetInf(preStat);
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Solution[] findAllgetInf(PreparedStatement preStat){
        Solution[] solutions = new Solution[0];
        try (ResultSet resultSet = preStat.executeQuery()) {
            while (resultSet.next()){
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solution.setExerciseId(resultSet.getInt("exercise_id"));
                solution.setUserId(resultSet.getInt("user_id"));
                solution.setCreated(resultSet.getTimestamp("created"));
                solution.setUpdated(resultSet.getTimestamp("updated"));
                solution.setDescription(resultSet.getString("description"));
                solutions = addToArray(solution, solutions);
            }
            return solutions;
        } catch (SQLException e) {
                e.printStackTrace();
                return null;
        }
    }

    private Solution[] addToArray(Solution solution, Solution[] solutions) {
        Solution[] tmpSolutions = Arrays.copyOf(solutions, solutions.length + 1);
        tmpSolutions[solutions.length] = solution;
        return tmpSolutions;
    }


}
