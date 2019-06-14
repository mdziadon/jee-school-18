package pl.coderslab.model;

import java.sql.Timestamp;

public class Solution {

    private int id;
    private int exerciseId;
    private int userId;
    private Timestamp created;
    private Timestamp updated;
    private String description;

    public Solution () {

    }

    public Solution(int exerciseId, int userId, String description) {
        this.exerciseId = exerciseId;
        this.userId = userId;
        this.created = new Timestamp(System.currentTimeMillis());
        this.updated = this.created;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public void setCreated() {
        this.created = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public void setUpdated() {
        this.updated = new Timestamp(System.currentTimeMillis());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
