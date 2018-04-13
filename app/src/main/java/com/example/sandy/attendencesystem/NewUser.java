package com.example.sandy.attendencesystem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUser extends AppCompatActivity {
  private   EditText id;
    private static final String TAG = NewUser.class.getSimpleName();
    private  EditText namee;
    private String mUserid;
    private TextInputLayout txtid1;
    private  TextInputLayout txtid2;
    private String mName;
    Button btnAddData;
    Button btnviewAll;
    Button btnDelete;
    Button btnUpdate;
   private DBHelper myDb;
    Button submit;
    EditText employeidno;
    private boolean valid;
    private String i1;
    private String i2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        myDb = new DBHelper(this);
        btnAddData =(Button)findViewById(R.id.add);
        btnviewAll =(Button)findViewById(R.id.view);
        btnDelete =(Button)findViewById(R.id.delete);
        btnUpdate =(Button)findViewById(R.id.update);
        submit =(Button) findViewById(R.id.submit);
       // AddData();
        viewAll();
        UpdateData();
        DeleteData();

         txtid1 = (TextInputLayout)findViewById(R.id.input_layout_id);
        txtid2 = (TextInputLayout)findViewById(R.id.input_name);

        id = (EditText)findViewById(R.id.iid);
        namee = (EditText)findViewById(R.id.name);
        employeidno = (EditText)findViewById(R.id.employe_no);

      employeidno.addTextChangedListener(new MyTextWatcher(employeidno));
       namee.addTextChangedListener(new MyTextWatcher(namee));
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btnAlmostThere();
//                Log.e(TAG,"entering//...");
//            }
//        });
//

//    }
//    public void btnAlmostThere(){
//        Log.e(TAG,"sjfdhkjsdhf");
//        mUserid = id.getText().toString().trim();
//        mName = name.getText().toString().trim();
//
//        if(mUserid.isEmpty() || mName.isEmpty()){
//            Toast.makeText(NewUser.this, "please fill all the fields!",
//                    Toast.LENGTH_LONG).show();
//
//
//        }
//
//        else if(mUserid.length()!=5){
//            txtid1.setError("id must be 5 letters");
//        }
//        else if((mName.length()<=2)||(mName.length()>=10)){
//            txtid2.setError("name must be above 2 letters and below 10 letters");
//        }
//        else
//        {
//           txtid1.setError(null);
//           txtid2.setError(null);
//
//        }
   }
    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(employeidno.getText().toString());
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
                                namee.getText().toString(),employeidno.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(NewUser.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(NewUser.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public  void addData(View view) {
        i1 = employeidno.getText().toString().trim();
        i2 = namee.getText().toString().trim();

     //   myDb.searching(employeidno.getText().toString());
        //   boolean isInserted = myDb.insertData(name.getText().toString(),employeidno.getText().toString());
        if (validations()) {
            Log.e(TAG, "e1");
            if (!myDb.isExist(employeidno.getText().toString())) {


                Log.e(TAG, "e2");
                myDb.insertData(namee.getText().toString(), employeidno.getText().toString());
                Log.e(TAG, "add data method");
                Toast.makeText(NewUser.this, "Data Inserted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(NewUser.this, "Data already there in db..", Toast.LENGTH_LONG).show();
            }
        }
    }
//    public boolean searching(){
//        Log.e(TAG,"sucess");
//      boolean value =
//if(value){
//   // Log.e(TAG,"sucess");
//    return true;
//
//}else {
//    Log.e(TAG,"fail");
//    return false;
//}
//    }




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
                            buffer.append("Empno :"+ res.getString(2)+"\n");
                           // buffer.append("Marks :"+ res.getString(3)+"\n\n");
                        }

                        // Show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title,String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    public boolean validations(){
        boolean valid=false;
        Log.e(TAG, "3");
        valid = (validateempid());
        valid = (validatename() && valid);


        return valid;
    }
    private boolean validateempid() {
        try {
            Log.e(TAG, "4");
            String idpattern = "^\\d{5}$";
            if (i1.isEmpty()) {
                Log.e(TAG, "6");
                txtid1.setError("please enter id no");
                return false;
            } else if (!i1.matches(idpattern)) {
                txtid1.setError("id must be 5 letters");
                return false;
            } else if (i1.matches("00000")) {

                txtid1.setError("please enter valid id");
                return false;
            } else {
                txtid1.setError(null);
                txtid1.setErrorEnabled(false);
            }



        } catch (Exception e) {

        }
        return true;
    }
private boolean validatename(){
    Log.e(TAG, "5");

    if (i1.isEmpty()) {
        Log.e(TAG, "7");
        txtid2.setError("please enter name");
        return false;

    } else if (employeidno.length()>10 && i1.length()<3){

        txtid2.setError("name must be min 3 letters and max 10 letters");
        return false;
    }
    else {
        txtid2.setError(null);
        txtid2.setErrorEnabled(false);
    }

    return true;

}


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {

            try {


                switch (view.getId()) {
                    case R.id.employe_no:
                        validateempid();
                        //  txtid1.setError(null);
                        break;
                    case R.id.name:
                        validatename();
                        //  txtid2.setError(null);
                        break;
                }
            } catch (Exception e) {

            }
        }

    }

}