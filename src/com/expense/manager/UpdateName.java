package com.expense.manager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class UpdateName extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_name);
	}
	public void update_name(View v){
		EditText name=(EditText)findViewById(R.id.updatename);
		String newname=name.getText().toString();
		if(newname.equalsIgnoreCase("")){
			new AlertDialog.Builder(this).setTitle("Error" )
			.setMessage("cant be blank")
			.setPositiveButton("OK", null).show();		
			return;
		}
		if(newname.contains("'")){						
			new AlertDialog.Builder(this).setTitle("Error" )
			.setMessage("name should not have ' character")
			.setPositiveButton("OK", null).show();		
			return;
		}
		if(newname.contains("\n")){						
			new AlertDialog.Builder(this).setTitle("Error" )
			.setMessage("name should not have new line..")
			.setPositiveButton("OK", null).show();		
			return;
		}
		int conflict;
		conflict=check_name_conflict_fromDatabase(newname);
		if(conflict==1){
			new AlertDialog.Builder(this).setTitle("Error" )
			.setMessage("name exists...try other name")
			.setPositiveButton("OK", null).show();	
			return;
		}
		Intent i=new Intent();
		i.putExtra("updated_name",newname);
		setResult(RESULT_OK,i);
		finish();
	}
	public int check_name_conflict_fromDatabase(String name){
		CommentsDataSource cd=new CommentsDataSource(this);
		int conflict=0;
		conflict=cd.check_name_conflict_fromDatabase(name);
		return conflict;
	}
}
