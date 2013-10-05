package com.expense.manager;
import java.text.DateFormatSymbols;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class UpdateAmount extends Activity{
	int tosubtract=0,radioselected=0;
	String username;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addsub);	
		username=getIntent().getStringExtra("username");
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup_update);
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
	public void newTransaction(View v){
		if(radioselected==0){
			new AlertDialog.Builder(this).setTitle("Error" )
			.setMessage("select taken or given radiobutton")
			.setPositiveButton("OK", null).show();		
			return;
		}
		int check=check_submission();
		if(check==0)return;
		EditText name=(EditText)findViewById(R.id.amount_addsub);
		String amount=name.getText().toString();
		String tempamt=amount;
		EditText Rreason=(EditText)findViewById(R.id.reason_addsub);
		String reason=Rreason.getText().toString();
		Integer amt=Integer.parseInt(amount);
		int amtvalue=amt.intValue();
		if(tosubtract==1){
			amtvalue=-1*amtvalue;
			tempamt="-"+amount;
		}
		String time[]=new String[1];
		time[0]=getTime();
		CommentsDataSource cd=new CommentsDataSource(this);		
		int currenttotal=cd.getcurrenttotal(username);
		amtvalue+=currenttotal;
		cd.new_updateamount(amtvalue,username,time[0]);
		//database2 also								
		cd.insert_to_database_registration_two(username,tempamt,reason,time);
		finish();
		/*
		Intent i=new Intent();
		i.putExtra("amount",amount);
		if(tosubtract==0)
			i.putExtra("action","add");
		else i.putExtra("action","sub");
		i.putExtra("reason",reason);
		setResult(RESULT_OK,i);
		finish();*/
	}
	public String getTime(){
		String time="";
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		String[] shortMonths = new DateFormatSymbols().getShortMonths();			
		time+=today.monthDay+" "+shortMonths[today.month-1];
		Log.d("start","oo"+time);
		return time;
	}
	public int check_submission(){
		EditText name=(EditText)findViewById(R.id.amount_addsub);
		String amount=name.getText().toString();
		try{
			Integer i=Integer.parseInt(amount);
			return 1;
		}catch(Exception e){
			new AlertDialog.Builder(this).setTitle("Error" )
			.setMessage("fill correct amount in \n integer format")
			.setPositiveButton("OK", null).show();
		}
		return 0;
	}
}
