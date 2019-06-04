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
    EditText editCell;
    EditText editHome;
    EditText editEmail;
    EditText editAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyContactApp", "MainActivity: Setting up layout");
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editText_name);
        editCell = findViewById(R.id.editCell);
        editHome = findViewById(R.id.editHome);
        editEmail = findViewById(R.id.editEmail);
        editAddress = findViewById(R.id.editAddress);

        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MainActivity: Instantiated DatabaseHelper");
    }

    public void addData (View view){
        Log.d("MyContactApp", "MainActivity: AddContact button pressed");
        boolean isInserted = myDb.insertData(editName.getText().toString(), editCell.getText().toString(), editHome.getText().toString(),
                        editEmail.getText().toString(), editAddress.getText().toString());

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
            Log.d("MyContactApp", "MainActivity: In viewData - No data found");
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            //Append res columns to the buffer - see StringBuffer and Cursor api
            buffer.append("ID: " + res.getString(0)+"\n");
            buffer.append("Name: " + res.getString(1)+"\n");
            buffer.append("Home #: " + res.getString(2)+"\n");
            buffer.append("Cell #: " + res.getString(3)+"\n");
            buffer.append("Email: " + res.getString(4)+"\n");
            buffer.append("Address: " + res.getString(5)+"\n\n");
        }

        Log.d("MyContactApp", "MainActivity: In viewData - Buffer assembled");
        showMessage("Data", buffer.toString());
    }

    public void searchData(View view){
        Cursor res = myDb.getAllData();
        Log.d("MyContactApp", "MainActivity: Searching data");

        if(res.getCount() == 0)
        {
            showMessage("Error: No Contacts", "No data found in database");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){

            boolean nameNull = editName.getText().toString().equals("");
            boolean cellNull = editCell.getText().toString().equals("");
            boolean homeNull = editHome.getText().toString().equals("");
            boolean emailNull = editEmail.getText().toString().equals("");
            boolean addressNull = editAddress.getText().toString().equals("");

            if((res.getString(1).equals(editName.getText().toString()) || nameNull) &&
                    (res.getString(2).equals(editCell.getText().toString()) || cellNull) &&
                    (res.getString(3).equals(editHome.getText().toString()) || homeNull) &&
                    (res.getString(4).equals(editEmail.getText().toString()) || emailNull) &&
                    (res.getString(5).equals(editAddress.getText().toString()) || addressNull))
            {
                buffer.append("ID: " + res.getString(0)+"\n");
                buffer.append("Name: " + res.getString(1)+"\n");
                buffer.append("Home #: " + res.getString(2)+"\n");
                buffer.append("Cell #: " + res.getString(3)+"\n");
                buffer.append("Email: " + res.getString(4)+"\n");
                buffer.append("Address: " + res.getString(5)+"\n\n");
            }
        }

        if(buffer.length() == 0)
        {
            showMessage("No Match", "No matches found in database.");
            return;
        }

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

    public void deleteAllData(View view){
        myDb.deleteData();
        Log.d("MyContactApp", "MainActivity: deleted ALL UR CONTACTS smh");
    }

}
