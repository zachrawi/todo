package com.zachrawi.todo;

public class Todo {
    String activity;
    boolean done;

    public Todo(String activity, boolean done) {
        this.activity = activity;
        this.done = done;
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
