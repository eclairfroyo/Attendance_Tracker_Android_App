package com.example.sivakami.attendancetracker;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegPage extends AppCompatActivity {

    AlertDialog alertDialog;
    EditText empId,mobNo;
    String strEmpId,strMobNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_page);

    }
    public void RegProcess(View view)
    {
        empId=(EditText)findViewById(R.id.edEmpId);
        mobNo=(EditText)findViewById(R.id.edMobNo);

        strEmpId=empId.getText().toString();
        strMobNo=mobNo.getText().toString();

        //EMPLOYEE AND A BOOLEAN VALUE IS INITIALIZED USING GLOBAL CLASS INSTANCE FOR FUTURE USE.
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        globalVariable.setGlobalEmpId(strEmpId);
        globalVariable.setIsCheckedIn(false);


        alertDialog=new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Insufficient details");

        //POSSIBLE ERRONEOUS INPUTS ARE IDENTIFIED.
        // IN ABC_CORP EMP_ID IS A 5 CHARACTER STRING THAT BEGINS WITH CAPITAL LETTER E.

        if(TextUtils.isEmpty(strEmpId))
        {
            alertDialog.setMessage("Employee ID is necessary!!");
            alertDialog.show();
        }
        else if(strEmpId.length()>5)
        {
                alertDialog.setMessage("Employee ID cannot exceed 5 characters!");
                alertDialog.show();
        }
        else if(strEmpId.charAt(0)!='E')
        {
                    alertDialog.setMessage("Employee ID must begin with 'E' !");
                    alertDialog.show();
        }
        else     //POSSIBLE ERRONEOUS INPUTS FOR MOBILE NUMBER.
        if(TextUtils.isEmpty(strMobNo))
        {
            alertDialog.setMessage("Mobile Number field is must!");
            alertDialog.show();
        }
        else if(strMobNo.length()<10)
        {
            alertDialog.setMessage("A Mobile number must be 10 digit long!");
            alertDialog.show();
        }
        else if(!TextUtils.isDigitsOnly(strMobNo))
        {
            alertDialog.setMessage("Enter a valid mobile number!!");
            alertDialog.show();
        }
        // IF THE INPUTS ARE PROPER THEY ARE PASSED TO BACKGROUND TASK
        // WHICH PASSES THEM ON TO THE SERVER FOR VALIDATION.
        else
        {

            BackgroundRegTask backgroundRegTask = new BackgroundRegTask(this);
            backgroundRegTask.execute(strEmpId, strMobNo);
        }
    }

}
