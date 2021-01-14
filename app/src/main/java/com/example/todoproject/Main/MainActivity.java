  package com.example.todoproject.Main;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoproject.DataBase.Room.Tasks;
import com.example.todoproject.FastAddTask.FastAddTask;
import com.example.todoproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainContract.MainView, MainPresenter.OnTaskReceived {

    private static final String TAG = "myLogs";
    //List<Tasks> tasks = new ArrayList<>();
    //public RecyclerView.Adapter recyclerAdapter;
    //private RecyclerView.LayoutManager layoutManager;
    public List<Tasks> allTasks;
    private RecyclerView recyclerView;
    final private MainPresenter mainPresenter = new MainPresenter(this);
    private static final int REQUEST_CODE = 200;
    final private boolean falseVisible = false;
    final private boolean trueVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        Toolbar menuToolbar = findViewById(R.id.toolbar_main);
        recyclerView = findViewById(R.id.tasks_recycler_list);
        TextView tvFirstTask = findViewById(R.id.tv_create_first_task);

        try {
            mainPresenter.onPassTaskMainView();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setSupportActionBar(menuToolbar);

        //при нажатии на FAB выводим keyboard (Activity FastAddTAsk)
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
        public void onClick(View view){
            Intent fastTaskIntent = new Intent(MainActivity.this, FastAddTask.class);
            startActivityForResult(fastTaskIntent, REQUEST_CODE);
        }
    });

        RecyclerAdapter adapter = new RecyclerAdapter(allTasks);
        if(adapter.getItemCount() > 0) {
            tvFirstTask.setVisibility(View.GONE);
        }

        //код для инициализации NavigationView
        /*
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);*/
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                mainPresenter.onPassTaskMainView();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        RecyclerAdapter adapter = new RecyclerAdapter(allTasks);
        TextView tvFirstTask = findViewById(R.id.tv_create_first_task);
        if(adapter.getItemCount() > 0) {
            tvFirstTask.setVisibility(View.GONE);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    //создание меню в Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_toolbar, menu);
        return true;
    }

    //обработчик нажатий пунктов в ToolBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.delete_all:
                mainPresenter.onDeleteAllDB();
                recreate();
                Toast toast = Toast.makeText(getApplicationContext(), R.string.delete_all, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //update меню в Toolbar
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem deleteAllItem = menu.findItem(R.id.delete_all);

        RecyclerAdapter adapter = new RecyclerAdapter(allTasks);
        if(adapter.getItemCount() != 0){
            deleteAllItem.setVisible(trueVisible);
        } else {
            deleteAllItem.setVisible(falseVisible);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    //обработчик нажатий пунктов в NavigationView
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        /*DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.openDrawer(GravityCompat.START);*/
        return true;
    }



    //показ задачи в MainActivity, пока не актуален метод. Метод onReceive получает tasks с помощью CallBack в interface OnTaskReceived
    @Override
    public void onViewTask(List<Tasks> tasks) {
        //MainActivity нужно связать с RecyclerView

        //layoutManager для работы с внешним видом RecyclerView
        /*layoutManager = new LinearLayoutManager(this);
        recyclerTasks.setLayoutManager(layoutManager);*/

        //RecyclerAdapter recyclerAdapter = new RecyclerAdapter(tasks);
        //adapter.notifyDataSetChanged();
        //recyclerTasks.setAdapter(recyclerAdapter);

        Log.d(TAG,"onViewTask in class MainActivity" + tasks + Thread.currentThread().getName());
    }


    @Override
    public void onReceive(List<Tasks> tasks) {
        allTasks = tasks;
        RecyclerAdapter adapter = new RecyclerAdapter(allTasks);
        recyclerView.setAdapter(adapter);

        Log.d(TAG,"onReceive in class MainActivity" + tasks + Thread.currentThread().getName());

    }


}



