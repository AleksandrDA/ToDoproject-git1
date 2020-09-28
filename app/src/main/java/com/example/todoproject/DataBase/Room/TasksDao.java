package com.example.todoproject.DataBase.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TasksDao {

    @Query("SELECT * FROM tasks")
    List<Tasks> getAll();

    @Query("SELECT * FROM tasks WHERE id = :id")
    Tasks getById(long id);

    @Query("DELETE FROM tasks")
    void deleteAll();

    @Insert
    void insertTask(Tasks tasks);

    @Update
    void updateTasks(Tasks tasks);

    @Delete
    void deleteTasks(Tasks tasks);
}
