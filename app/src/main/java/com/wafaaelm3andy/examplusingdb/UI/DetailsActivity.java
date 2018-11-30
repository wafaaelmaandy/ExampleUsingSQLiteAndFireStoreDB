package com.wafaaelm3andy.examplusingdb.UI;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.wafaaelm3andy.examplusingdb.Model.User;
import com.wafaaelm3andy.examplusingdb.Adapter.MyAdapter;
import com.wafaaelm3andy.examplusingdb.R;
import com.wafaaelm3andy.examplusingdb.LocalDB.UserContract;
import com.wafaaelm3andy.examplusingdb.LocalDB.UserDbHelper;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView usersRec ;
    List<User> usersLocal = new ArrayList<>();
    List<User> usersRemote = new ArrayList<>();

    MyAdapter myAdapter ;
    Cursor cursor ;
    FirebaseFirestore firebaseFirestore ;
    UserDbHelper userDbHelper=new UserDbHelper(DetailsActivity.this);
    SQLiteDatabase sqLiteDatabase ;
    Spinner options ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        open();

        firebaseFirestore = FirebaseFirestore.getInstance();
        myAdapter = new MyAdapter(usersLocal,this);
        usersRec = findViewById(R.id.users_rec);
        usersRec.setLayoutManager(new LinearLayoutManager(this));
        usersRec.setAdapter(myAdapter);
        options = findViewById(R.id.options_spinner);
        ArrayAdapter<CharSequence> settingAdapter = ArrayAdapter.createFromResource(this,
                R.array.setting, android.R.layout.simple_spinner_item);
        settingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        options.setAdapter(settingAdapter);
        options.setOnItemSelectedListener(this);
    }


    public void open() throws SQLException {
        sqLiteDatabase = userDbHelper.getWritableDatabase();
    }

    public List<User> getUsersFromLocal() {
        usersLocal.clear();
        cursor= sqLiteDatabase.rawQuery("select * from "+ UserContract.UserListEntry.TABLE_NAME,null);
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                String name= cursor.getString(cursor.getColumnIndex(UserContract.UserListEntry.COLUMN_NAME));
                int age= cursor.getInt(cursor.getColumnIndex(UserContract.UserListEntry.COLUMN_AGE));
                String gender= cursor.getString(cursor.getColumnIndex(UserContract.UserListEntry.COLUMN_GENDER));
                User user = new User(name,gender,age);
                usersLocal.add(user);
                cursor.moveToNext();
            }
        }
        myAdapter = new MyAdapter(usersLocal,this);
        usersRec.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        return usersLocal;
    }

    public  List<User> getListOfFireStore (){
        usersRemote.clear();
        firebaseFirestore.collection("users")
                .addSnapshotListener( new EventListener< QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if(e!=null){
                        }
                        for(DocumentChange doc :documentSnapshots.getDocumentChanges()){
                            if(doc.getType()== DocumentChange.Type.ADDED){


                                User  user = doc.getDocument().toObject(User.class);
                                usersRemote.add(user);

                            }}}});
        myAdapter = new MyAdapter(usersRemote,this);
        usersRec.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        return usersRemote;}


    public void addUser(View view) {
        startActivity(new Intent(DetailsActivity.this,MainActivity.class));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String sp1= String.valueOf(options.getSelectedItem());

        if(sp1.contentEquals("Load Local DB")) {
            getUsersFromLocal();
        }
        else if (sp1.contentEquals("Load RemoteDB")){
          getListOfFireStore();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

