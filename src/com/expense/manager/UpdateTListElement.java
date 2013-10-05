package com.expense.manager;
import java.text.DateFormatSymbols;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class UpdateTListElement extends Activity{
	String username;
	String id,oldamt;
	int radioselected=0;
	int tosubtract,oldamount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updatetlistelement);
		id=getIntent().getStringExtra("ID");
		username=getIntent().getStringExtra("username");
		oldamt=getIntent().getStringExtra("oldamt");
		Integer u=Integer.parseInt(oldamt);
		oldamount=u.intValue();
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.new_radiogroup_registration);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) 
            {
            	radioselected=1;
                RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
                String text = checkedRadioButton.getText().toString();
                if(text.equalsIgnoreCase("Given"))
                	tosubtract=0;
                else
                	tosubtract=1;
            }
        });		
	}
	public void changeamount(View v){
		if(radioselected==0){
			new AlertDialog.Builder(this).setTitle("Error" )
			.setMessage("select taken or given radiobutton")
			.setPositiveButton("OK", null).show();		
			return;
		}		
		CommentsDataSource cd=new CommentsDataSource(this);
		int amt=cd.getAmount(username);
		EditText edamt=(EditText)findViewById(R.id.new_amount_edittext);
		String am=edamt.getText().toString();
		try{
			Integer temp=Integer.parseInt(am);
		}
		catch(Exception e){			
			new AlertDialog.Builder(this).setTitle("Error" )
			.setMessage("fill correcct amount")
			.setPositiveButton("OK", null).show();	
			return;
		}
		
		if(tosubtract==1)am="-"+am;
		Integer in=Integer.parseInt(am);
		
		int total=in.intValue()+amt-oldamount;
		cd.updateamount(total, username, getTime());	
		cd.updateamountTransactionDB_IdGiven(id,am);	
		finish();
	}
	public String getTime(){
		String time="";
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		//time+=today.monthDay+"/"+today.month;
		String[] shortMonths = new DateFormatSymbols().getShortMonths();			
		time+=today.monthDay+" "+shortMonths[today.month-1];int jsadh=0;
		return time;
	}
}
