package com.example.sandy.attendencesystem;

import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUser extends AppCompatActivity {
  private   EditText id;
    private static final String TAG = NewUser.class.getSimpleName();
  private  EditText name;
  private String mUserid;
   private TextInputLayout txtid1;
   private  TextInputLayout txtid2;
    private String mName;
    Button btnAddData;
    Button btnviewAll;
    Button btnDelete;
    Button btnUpdate;
    DBHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        myDb = new DBHelper(this);
        btnAddData =(Button)findViewById(R.id.add);
        btnviewAll =(Button)findViewById(R.id.view);
        btnDelete =(Button)findViewById(R.id.delete);
        btnUpdate =(Button)findViewById(R.id.update);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
         txtid1 = (TextInputLayout)findViewById(R.id.input_layout_id);
        txtid2 = (TextInputLayout)findViewById(R.id.input_name);

         id = (EditText)findViewById(R.id.id);
        name = (EditText)findViewById(R.id.name);
        Button submit =(Button) findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAlmostThere();
                Log.e(TAG,"entering//...");
            }
        });


    }
    public void btnAlmostThere(){
        Log.e(TAG,"sjfdhkjsdhf");
        mUserid = id.getText().toString().trim();
        mName = name.getText().toString().trim();

        if(mUserid.isEmpty() || mName.isEmpty()){
            Toast.makeText(NewUser.this, "please fill all the fields!",
                    Toast.LENGTH_LONG).show();


        }

        else if(mUserid.length()!=5){
            txtid1.setError("id must be 5 letters");
        }
        else if((mName.length()<=2)||(mName.length()>=10)){
            txtid2.setError("name must be above 2 letters and below 10 letters");
        }
        else
        {
           txtid1.setError(null);
           txtid2.setError(null);

        }
    }
    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(id.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(NewUser.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(NewUser.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void UpdateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(id.getText().toString(),
                                name.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(NewUser.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(NewUser.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public  void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(name.getText().toString());
                        if(isInserted == true)
                            Toast.makeText(NewUser.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(NewUser.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Name :"+ res.getString(1)+"\n");
                           // buffer.append("Surname :"+ res.getString(2)+"\n");
                          //  buffer.append("Marks :"+ res.getString(3)+"\n\n");
                        }

                        // Show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
