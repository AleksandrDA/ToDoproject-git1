package com.example.todoproject.Main;

import android.os.AsyncTask;

import com.example.todoproject.DataBase.Room.AppDataBase;
import com.example.todoproject.DataBase.Room.StoreObjectDatabase;
import com.example.todoproject.DataBase.Room.Tasks;
import com.example.todoproject.DataBase.Room.TasksDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainRepository implements MainContract.MainRepository{
    //получение Базы данных
    AppDataBase db = StoreObjectDatabase.getInstance().getDatabase();
    //из Database объекта получаем Dao
    TasksDao tasksDao = db.tasksDao();


    @Override
    public void deleteAllTasks() {
        new MainRepository.DeleteAllDb(tasksDao).execute();
    }

    public List<Tasks> onRepositoryPassTask() throws ExecutionException, InterruptedException {
        /*AppDataBase db = StoreObjectDatabase.getInstance().getDatabase();
        TasksDao tasksDao = db.tasksDao();
        List<Tasks> allTasks = tasksDao.getAll();*/
        //mainPresenter.onPassTaskMainView(allTasks);

        //Log.d(TAG,"onRepositoryPassTask work in class FastTaskRepository " + tasksDao + Thread.currentThread().getName());
        return new MainRepository.GetTasks().execute().get();

    }

    static class GetTasks extends AsyncTask<Void, Void, List<Tasks>> {

        @Override
        protected List<Tasks> doInBackground(Void... voids) {
            List<Tasks> getAllTasks = StoreObjectDatabase.getInstance().getDatabase().tasksDao().getAll();
            return getAllTasks;
        }
    }

    static class DeleteAllDb extends AsyncTask<Void, Void, Void> {
        private TasksDao tasksDao;

        public DeleteAllDb(TasksDao dao) {
            tasksDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            tasksDao.deleteAll();
            return null;
        }
    }
}

