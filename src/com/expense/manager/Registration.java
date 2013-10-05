package com.expense.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Registration extends Activity{	
	int tosubtract=0,radioselected=0;;
	String[] time=new String[1];
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);	
		setTitle("New Account");
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup_registration);
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) 
			{
				radioselected=1;
				RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
				String text = checkedRadioButton.getText().toString();
				if(text.equalsIgnoreCase("given"))
					tosubtract=0;
				else
					tosubtract=1;
			}
		});
	}
	public void register_create_account(View v){					
		EditText name=(EditText)findViewById(R.id.name_edittext);
		EditText amount=(EditText)findViewById(R.id.amount_edittext);		
		EditText reason=(EditText)findViewById(R.id.reason_edittext);
		String username=name.getText().toString();				
		String useramount=amount.getText().toString();
		String userreason=reason.getText().toString();
		if(tosubtract==1)
			useramount="-"+useramount;
		//form validation like javascript			
		if(username.equalsIgnoreCase("")){						
			new AlertDialog.Builder(this).setTitle("Error" )
			.setMessage("Name cannot be blank")
			.setPositiveButton("OK", null).show();		
			return;
		}
		if(username.contains("'")){						
			new AlertDialog.Builder(this).setTitle("Error" )
			.setMessage("name should not have ' character")
			.setPositiveButton("OK", null).show();		
			return;
		}
		if(username.contains("\n")){						
			new AlertDialog.Builder(this).setTitle("Error" )
			.setMessage("name should not have new line..")
			.setPositiveButton("OK", null).show();		
			return;
		}
		try{
			Integer temp=Integer.parseInt(useramount);
		}
		catch(Exception e){			
			new AlertDialog.Builder(this).setTitle("Error" )
			.setMessage("fill correct amount")
			.setPositiveButton("OK", null).show();	
			return;
		}
		if(radioselected==0){
			new AlertDialog.Builder(this).setTitle("Error" )
			.setMessage("select taken or given radiobutton")
			.setPositiveButton("OK", null).show();		
			return;
		}

		int conflict,status_insert=0;
		conflict=check_name_conflict_fromDatabase(username);
		if(conflict==1){
			new AlertDialog.Builder(this).setTitle("Error" )
			.setMessage("name exists...try other name")
			.setPositiveButton("OK", null).show();	
			return;
		}
		status_insert=insert_to_database_registration(username,useramount,userreason);
		Intent i=new Intent();
		if(status_insert==1)
		{
			i.putExtra("username",username);
			i.putExtra("useramount",useramount);
			i.putExtra("usertime",time[0]);
			i.putExtra("status","OK");
			setResult(RESULT_OK,i);
		}
		else {				
			i.putExtra("status","FAILED");
			setResult(RESULT_CANCELED,i);
		}			
		finish();
	}
	public int check_name_conflict_fromDatabase(String name){
		CommentsDataSource cd=new CommentsDataSource(this);
		int conflict=0;
		conflict=cd.check_name_conflict_fromDatabase(name);
		return conflict;
	}
	public int insert_to_database_registration(String name,String amount,String userreason){
		CommentsDataSource cd=new CommentsDataSource(this);			
		int status_insert=cd.insert_to_database_registration(name,amount,time);
		cd.insert_to_database_registration_two(name,amount,userreason,time);
		return status_insert;
	}	
}
