package com.expense.manager;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;

public class ShowTransactionList extends Activity{
	ArrayList<TransactionDetails> str;
	TransactionAdapter adapter;
	String tempamt;
	int posdel;
	CommentsDataSource cddel=new CommentsDataSource(this);
	String username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showtransactionlist);
		ActionBar actionBar = getActionBar();
	    // add the custom view to the action bar
	    actionBar.setCustomView(R.layout.actionbar_view);
	    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		username=getIntent().getStringExtra("username");
		String display_username=username;
		if(display_username.length()>20){
			display_username=display_username.substring(0, 20);
			display_username+="..";
		}
		
		TextView name=(TextView)findViewById(R.id.username);
		name.setText(display_username);
		
		str=new ArrayList<TransactionDetails>();		
		//adapter=new TransactionAdapter(this,R.layout.rowtransactiondetails,str);
		adapter=new TransactionAdapter(this,R.layout.new_rowtransactiondetails,str);
		update_arraylist_fromDatabase_transaction(str,username);	
		adapter.notifyDataSetChanged();
		ListView lv=(ListView)findViewById(R.id.listlv);
		lv.setAdapter(adapter);		
		update_total_balance();	
		registerForContextMenu(lv);
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);  
        menu.setHeaderTitle("Context Menu");            	    
        menu.add(0, v.getId(), 0, "Update Amount");
        
	}
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();		
		if(item.getTitle()=="Update Amount"){
			Intent chamt=new Intent(ShowTransactionList.this,UpdateTListElement.class);
			chamt.putExtra("username",username);
			chamt.putExtra("oldamt",str.get(info.position).getAmount());
			chamt.putExtra("ID",str.get(info.position).getId());
			startActivity(chamt);
		}
		
		return true;
		}	
	public void onResume(){
		super.onResume();
		Log.d("start","onresume");
		update_arraylist_fromDatabase_transaction(str,username);	
		adapter.notifyDataSetChanged();int ds=0;
		update_total_balance();
	}
	public void update_amount(View v){
		Intent updateamount=new Intent(ShowTransactionList.this,UpdateAmount.class);
		updateamount.putExtra("username",username);
		//startActivityForResult(updateamount,1);
		startActivity(updateamount);
	}
	public void onActivityResult(int requestCode, int resultCode,Intent data){		
		if(requestCode==1){		//onClick updateamount,returns data back to mainactivity
			if(resultCode==RESULT_OK){
				setResult(RESULT_OK,data);
				finish();
			}
		}
	}
	public void update_arraylist_fromDatabase_transaction(ArrayList<TransactionDetails> str,String username){		
		CommentsDataSource cd=new CommentsDataSource(this);
		cd.update_arraylist_fromDatabase_transaction(str,username);
	}
	public void update_total_balance(){
		int temp,i,total=0,len=str.size();
		Integer p;
		String s="";
		for(i=0;i<len;i++){
			s=str.get(i).getAmount();
			p=Integer.parseInt(s);
			total+=p.intValue();
		}
		TextView total_balance=(TextView)findViewById(R.id.final_balance);
		if(total<0)total_balance.setTextColor(Color.RED);
		else total_balance.setTextColor(Color.GREEN);
		total_balance.setText("Rs."+total);
	}
}
