package com.zachrawi.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

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

        mTodoAdapter = new TodoAdapter(this, R.layout.item_todo, mTodos, new TodoAdapter.OnClickListener() {
            @Override
            public void onChecked(int position, final boolean isChecked) {
                Log.d("###", "onChecked: ");
                Todo todo = mTodos.get(position);
                todo.setDone(isChecked);

                ParseQuery<ParseObject> query = ParseQuery.getQuery("MyTodo");
                query.getInBackground(todo.getId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            object.put("done", isChecked);

                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {

                                    } else {
                                        Toast.makeText(MainActivity.this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(MainActivity.this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                mTodos.set(position, todo);
                mTodoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDelete(int position) {
                Log.d("###", "onDelete: ");

                Todo todo = mTodos.get(position);

                ParseQuery<ParseObject> query = ParseQuery.getQuery("MyTodo");

                query.getInBackground(todo.getId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            object.deleteInBackground();
                        }
                    }
                });

                mTodos.remove(position);
                mTodoAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerView.setAdapter(mTodoAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        loadTodo();
    }

    private void loadTodo() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MyTodo");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    mTodos.clear();

                    for (int i=0;i<objects.size();i++) {
                        ParseObject parseObject = objects.get(i);
                        Log.d("###", "id: " + parseObject.getObjectId());
                        boolean done = parseObject.getBoolean("done");
                        String activity = parseObject.getString("activity");

                        mTodos.add(new Todo(parseObject.getObjectId(), activity, done));
                    }

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTodoAdapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menus, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd) {
            Intent intent = new Intent(this, AddActivity.class);
            startActivityForResult(intent, 1);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String activity = data.getStringExtra("activity");

                ParseObject parseObject = new ParseObject("MyTodo");
                parseObject.put("done", false);
                parseObject.put("activity", activity);

                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(MainActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();

                            loadTodo();
                        }
                    }
                });
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
