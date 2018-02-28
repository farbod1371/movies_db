package com.example.elessar1992.movies_db.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.elessar1992.movies_db.Model.User;
import com.example.elessar1992.movies_db.R;

import java.util.List;

/**
 * Created by elessar1992 on 2/26/18.
 */

public class ShowAllUserRecycler extends RecyclerView.Adapter<ShowAllUserRecycler.AllUserViewHolder>
{
    private List<User> listUsers;

    public ShowAllUserRecycler(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    @Override
    public AllUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_all_users, parent, false);

        return new AllUserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AllUserViewHolder holder, int position)
    {
        holder.textViewName.setText(listUsers.get(position).getUsername());
        holder.textViewEmail.setText(listUsers.get(position).getEmail());
        holder.textViewPassword.setText(listUsers.get(position).getPassword());
    }

    @Override
    public int getItemCount()
    {
        Log.v(ShowAllUserRecycler.class.getSimpleName(),""+listUsers.size());
        return listUsers.size();
    }


    /**
     * ViewHolder class
     */
    public class AllUserViewHolder extends RecyclerView.ViewHolder
    {

        public AppCompatTextView textViewName;
        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewPassword;

        public AllUserViewHolder(View view)
        {
            super(view);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewEmail = (AppCompatTextView) view.findViewById(R.id.textViewEmail);
            textViewPassword = (AppCompatTextView) view.findViewById(R.id.textViewPassword);
        }
    }
}
