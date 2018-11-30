package com.wafaaelm3andy.examplusingdb.UI;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wafaaelm3andy.examplusingdb.R;
import com.wafaaelm3andy.examplusingdb.LocalDB.UserContract;
import com.wafaaelm3andy.examplusingdb.LocalDB.UserDbHelper;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
  EditText nameEdit ,ageEdit ;
  RadioGroup genderRadio ;
  SQLiteDatabase sqLiteDatabase ;
  UserDbHelper userDbHelper=new UserDbHelper(MainActivity.this);
  String gender ;
  FirebaseFirestore db = FirebaseFirestore.getInstance();





  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    nameEdit = findViewById(R.id.name_field);
    ageEdit =findViewById(R.id.age_field);
    genderRadio =findViewById(R.id.gender_radio);
    genderRadio.setOnCheckedChangeListener(this);
    open();


  }



  public void open() throws SQLException {
    sqLiteDatabase = userDbHelper.getWritableDatabase();
  }



  public long addUserLocally(String name , int age , String gender ){
    ContentValues cv = new ContentValues();
    cv.put(UserContract.UserListEntry.COLUMN_NAME,name);
    cv.put(UserContract.UserListEntry.COLUMN_AGE,age);
    cv.put(UserContract.UserListEntry.COLUMN_GENDER,gender);
    return sqLiteDatabase.insert(UserContract.UserListEntry.TABLE_NAME,null, cv);



  }








  public void saveLocally(View view) {
    if (nameEdit.getText().length() == 0|ageEdit.getText().length() == 0) {
      return;
    }
    else {
      String name = nameEdit.getText().toString();
      int age = Integer.parseInt (ageEdit.getText().toString()) ;
      addUserLocally(name,age,gender);

      //clear UI text fields
      nameEdit.clearFocus();
      ageEdit.clearFocus();
      Toast.makeText(this,"saved",Toast.LENGTH_LONG).show();
      Intent i = new Intent(this,DetailsActivity.class);
      startActivity(i);}
  }

  public void saveRemotely(View view) {
    Map<String, Object> user = new HashMap<>();
    if (nameEdit.getText().toString().length() == 0 | ageEdit.getText().toString().length() == 0) {
      return;
    } else {
      String name = nameEdit.getText().toString();
      int age = Integer.parseInt(ageEdit.getText().toString());
      user.put("name", name);
      user.put("age", age);
      user.put("gender", gender);
      Toast.makeText(MainActivity.this, "test ",
              Toast.LENGTH_SHORT).show();
      db.collection("users").document(name).set(user)
              .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                  Toast.makeText(MainActivity.this, "User Registered",
                          Toast.LENGTH_SHORT).show();
                  startActivity(new Intent(MainActivity.this, DetailsActivity.class));

                }
              })
              .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                  Toast.makeText(MainActivity.this, "ERROR" + e.toString(),
                          Toast.LENGTH_SHORT).show();
                }
              });
    }
  }
  @Override
  public void onCheckedChanged(RadioGroup radioGroup, int i) {

    switch (i){

      case R.id.female_radio:
        gender = "female" ;
        break;

      case R.id.male_radio:
        gender = "male" ;
        break;

    }
  }

}

