package com.zachrawi.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    TodoAdapter mTodoAdapter;
    ArrayList<Todo> mTodos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);

        mTodos = new ArrayList<>(); // kosong

        // isi data todo
        mTodos.add(new Todo("Sarapan pagi", true));
        mTodos.add(new Todo("Makan siang", false));
        mTodos.add(new Todo("Makan malam", false));
        mTodos.add(new Todo("Mandi pagi", true));

        mTodoAdapter = new TodoAdapter(this, R.layout.item_todo, mTodos);

        mRecyclerView.setAdapter(mTodoAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }
}
