package com.zachrawi.todo;

public class Todo {
    String id;
    String activity;
    boolean done;

    public Todo(String id, String activity, boolean done) {
        this.id = id;
        this.activity = activity;
        this.done = done;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
