package com.wafaaelm3andy.examplusingdb.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wafaaelm3andy.examplusingdb.Model.User;
import com.wafaaelm3andy.examplusingdb.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.NoteHolder> {
    Context context ;
    List<User>users;






    public MyAdapter(List<User> users , Context context){
        this.users=users;
        this.context=context ;
    }


    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item ;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToPerentImmediatly =false ;
        View view =inflater.inflate(layoutIdForListItem,parent,shouldAttachToPerentImmediatly);
        NoteHolder noteHolder = new NoteHolder(view);
        return noteHolder;

    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {

        String name= users.get(position).getName();
        int age= users.get(position).getAge();

        String gender= users.get(position).getGender();
        holder.nameTV.setText("Name   :"+name);
        holder.genderTV.setText("Gender :"+gender);
        holder.ageTV.setText("Age    :"+age+"");

    }

    @Override
    public int getItemCount() {
        return
        users.size();
    }



    public  class  NoteHolder extends RecyclerView.ViewHolder   {
        TextView nameTV ,ageTV ,genderTV ;

        public NoteHolder(View itemView){
            super(itemView);
            nameTV =itemView.findViewById(R.id.name_tv);
            ageTV=itemView.findViewById(R.id.age_tv);
            genderTV=itemView.findViewById(R.id.gender_tv);


        }





    }
}


