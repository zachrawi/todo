package com.zachrawi.todo;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private Context mContext;
    private int mResource;
    private ArrayList<Todo> mTodos;

    public TodoAdapter(Context context, int resource, ArrayList<Todo> todos) {
        mContext = context;
        mResource = resource;
        mTodos = todos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(mResource, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo todo = mTodos.get(position);

        holder.tvActivity.setText(todo.getActivity());
        holder.cbDone.setChecked(todo.isDone());

        if (todo.isDone()) {
            holder.tvActivity.setPaintFlags(holder.tvActivity.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvActivity.setTypeface(null, Typeface.BOLD_ITALIC);
        } else {
            holder.tvActivity.setPaintFlags(holder.tvActivity.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvActivity.setTypeface(null, Typeface.NORMAL);
        }
    }

    @Override
    public int getItemCount() {
        return mTodos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbDone;
        TextView tvActivity;
        ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cbDone = itemView.findViewById(R.id.cbDone);
            tvActivity = itemView.findViewById(R.id.tvActivity);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
}
