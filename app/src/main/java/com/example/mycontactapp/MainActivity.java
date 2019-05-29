package com.example.mycontactapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyContactApp", "MainActivity: Setting up layout");
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editText_name);

        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MainActivity: Instantiated DatabaseHelper");
    }

    public void addData (View view){
        Log.d("MyContactApp", "MainActivity: AddContact button pressed");
        boolean isInserted = myDb.insertData(editName.getText().toString());

        if (isInserted == true){
            Toast.makeText(MainActivity.this, "Success - contact inserted", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "FAILED - contact not inserted", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewData (View view){
        Log.d("MyContactApp", "MainActivity: ViewContacts button pressed");
        Cursor res = myDb.getAllData();

        if (res.getCount() == 0){
            showMessage("Error", "No data found in database");
        }

        StringBuffer buffer = new StringBuffer();
        buffer.append("ID: "+ res.getString(0) + "\n");
        //do other entries

        Log.d("MyContactApp", "MainActivity: In viewData - buffer assembled");
        showMessage("Data", buffer.toString());
    }

    public void showMessage(String title, String message){
        Log.d("MyContactApp", "MainActivity: showMessage - building alert dialogue");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
