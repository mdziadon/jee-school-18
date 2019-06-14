package pl.coderslab.dao;

import pl.coderslab.model.Exercise;

import java.sql.*;
import java.util.Arrays;

public class ExerciseDao {

    private static final String CREATE_EXERCISES_QUERY =
            "INSERT INTO exercises(title, description) VALUES (?, ?)";
    private static final String READ_EXERCISES_QUERY =
            "SELECT * FROM exercises where id = ?";
    private static final String UPDATE_EXERCISES_QUERY =
            "UPDATE exercises SET title = ?, description = ? where id = ?";
    private static final String DELETE_EXERCISES_QUERY =
            "DELETE FROM exercises WHERE id = ?";
    private static final String FIND_ALL_EXERCISES_QUERY =
            "SELECT * FROM exercises";

    public Exercise create(Exercise exercise) {
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(CREATE_EXERCISES_QUERY, Statement.RETURN_GENERATED_KEYS);
            preStat.setString(1, exercise.getTitle());
            preStat.setString(2, exercise.getDescription());
            preStat.executeUpdate();
            ResultSet resultSet = preStat.getGeneratedKeys();
            if (resultSet.next()) {
                exercise.setId(resultSet.getInt(1));
            }
            return exercise;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Exercise read(int exerciseID) {
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(READ_EXERCISES_QUERY);
            preStat.setInt(1, exerciseID);
            Exercise exercise = new Exercise();
            ResultSet resultSet = preStat.executeQuery();
            if (resultSet.next()) {
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                return exercise;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Exercise exercise) {
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(UPDATE_EXERCISES_QUERY);
            preStat.setString(1, exercise.getTitle());
            preStat.setString(2, exercise.getDescription());
            preStat.setInt(3, exercise.getId());
            preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int exerciseID) {
        try (Connection connect = DbUtil.getConn()) {
            PreparedStatement preStat = connect.prepareStatement(DELETE_EXERCISES_QUERY);
            preStat.setInt(1, exerciseID);
            preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Exercise[] findAll() {
        try (Connection connect = DbUtil.getConn()) {
            Exercise[] exercises = new Exercise[0];
            PreparedStatement preStat = connect.prepareStatement(FIND_ALL_EXERCISES_QUERY);
            ResultSet resultSet = preStat.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                exercises = addToArray(exercise, exercises);
            }
            return exercises;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Exercise[] addToArray(Exercise exercise, Exercise[] exercises) {
        Exercise[] tmpExercises = Arrays.copyOf(exercises, exercises.length + 1);
        tmpExercises[exercises.length] = exercise;
        return tmpExercises;
    }
}
