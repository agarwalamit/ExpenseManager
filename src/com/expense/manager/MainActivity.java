package com.expense.manager;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	ArrayList<Details> str;
	CommentsDataSource cddel=new CommentsDataSource(this);
	int p1,p2,posdel;
	String default_color=new String("#FFFFFF");
	DetailsAdapter adapter;
	ListView lv;
	int positionclicked;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);					
		str=new ArrayList<Details>();		
		//adapter=new detailsAdapter(this,R.layout.row_layout,str);	
		adapter=new DetailsAdapter(this,R.layout.new_row_layout,str);
		update_arraylist_fromDatabase(str);
		adapter.notifyDataSetChanged();
		lv=(ListView)findViewById(R.id.userlist_listview);
		lv.setAdapter(adapter);		

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				positionclicked=position;
				Intent showtransaction=new Intent(MainActivity.this,ShowTransactionList.class);
				showtransaction.putExtra("username",str.get(position).getName());
				//startActivityForResult(showtransaction,2);
				startActivity(showtransaction);
			}
		});
		registerForContextMenu(lv);
		update_total_balance();
	}
	public void onResume(){
		super.onResume();
		update_arraylist_fromDatabase(str);
		adapter.notifyDataSetChanged();
		update_total_balance();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.createnewaccount:
			new_registration2();	    		
			return true;
		case R.id.sort_by_menuitem:
			final CharSequence[] items = {"sort by name","sort by amount"};
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Make your selection");
			builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					// Do something with the selection
					if(item==0){
						sory_by_name();
					}
					else if(item==1){
						sort_by_amount();
					}
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
			return true;		
		case R.id.delete_all:
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
			alertDialog.setTitle("Confirm Delete...");
			alertDialog.setMessage("Are you sure you want to delete all records?");
			alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int which) {	     
					cddel.delete_All();
					str.clear();
					adapter.notifyDataSetChanged();
					update_total_balance();
				}
			});
			alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			alertDialog.show();	           
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);  
		menu.setHeaderTitle("Context Menu");  

		menu.add(0, v.getId(), 0, "Update Name");
		menu.add(0, v.getId(), 0, "Clear Amount");
		menu.add(0, v.getId(), 0, "Delete Entry");
		menu.add(0, v.getId(), 0, "Change Color");

	}
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		posdel=info.position;
		if(item.getTitle()=="Delete Entry"){
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
			alertDialog.setTitle("Confirm Delete: "+str.get(posdel).getName());
			alertDialog.setMessage("Are you sure you want to delete this user's records?");
			alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int which) {	     

					cddel.delete_user(str.get(posdel).getName());//from both tables
					str.remove(posdel);
					adapter.notifyDataSetChanged();
					update_total_balance();
				}
			});
			alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			alertDialog.show();

		}
		else if(item.getTitle()=="Update Name"){
			positionclicked=info.position;
			Intent updatename=new Intent(MainActivity.this,UpdateName.class);
			startActivityForResult(updatename,3);				
		}
		else if(item.getTitle()=="Clear Amount"){
			positionclicked=info.position;
			str.get(positionclicked).setAmount("0");
			clearAmount(str.get(positionclicked).getName());
			adapter.notifyDataSetChanged();
			update_total_balance();
		}
		else if(item.getTitle()=="Change Color"){
			positionclicked=info.position;
			Intent color=new Intent(MainActivity.this,ShowColors.class);
			startActivityForResult(color, 4);
		}
		else {return false;} 
		return true;
	}
	public void new_registration(View v){	//called when button was there
		Intent register=new Intent(MainActivity.this,Registration.class);		
		startActivityForResult(register,1);		
	}
	public void new_registration2(){//its new....called by action bar options selected
		Intent register=new Intent(MainActivity.this,Registration.class);		
		startActivityForResult(register,1);		
	}
	public void onActivityResult(int requestCode, int resultCode,Intent data){
		if(requestCode==1){	//new registration only UI update here
			if(resultCode==RESULT_OK){
				String status=data.getStringExtra("status");
				if(status.equalsIgnoreCase("OK")){
					Log.d("start","entry _added");
					String username=data.getStringExtra("username");
					String useramount=data.getStringExtra("useramount");
					String time=data.getStringExtra("usertime");
					Details temp=new Details(username,useramount,time,default_color);
					str.add(0,temp);					
					adapter.notifyDataSetChanged();
					update_total_balance();
				}
				else{
					Log.d("start","error_in_entry_add");
				}
			}
		}
		else if(requestCode==3){		//update name
			if(resultCode==RESULT_OK){
				String realname=str.get(positionclicked).getName();
				String updated_name=data.getStringExtra("updated_name");
				updatename(realname,updated_name);//in both tables
				str.get(positionclicked).setName(updated_name);
				adapter.notifyDataSetChanged();
			}
		}
		else if(requestCode==4){		//update color
			if(resultCode==RESULT_OK){
				String color=data.getStringExtra("color");
				String name=str.get(positionclicked).getName();				
				updateColor(name,color);
				str.get(positionclicked).setColor(color);
				adapter.notifyDataSetChanged();
			}
		}			
	}
	public void update_arraylist_fromDatabase(ArrayList<Details> str){
		CommentsDataSource cd=new CommentsDataSource(this);
		cd.update_arraylist_fromDatabase(str);		
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
	public void updatename(String realname,String updated_name){
		CommentsDataSource cd=new CommentsDataSource(this);
		cd.update_name_in_database(realname,updated_name);
	}
	public void clearAmount(String name){
		CommentsDataSource cd=new CommentsDataSource(this);
		cd.clearAmount(name);
	}
	public void sory_by_name(){
		Collections.sort(str, new CustomComparator());
		adapter.notifyDataSetChanged();
	}
	public void sort_by_amount(){
		Collections.sort(str, new CustomComparator_by_amount());
		adapter.notifyDataSetChanged();
	}
	public void showColors(){
		Intent i=new Intent(MainActivity.this,ShowColors.class);
		startActivity(i);
	}
	public void updateColor(String realname,String color){
		CommentsDataSource cd=new CommentsDataSource(this);
		cd.updateColor(realname, color);
	}
	public void update_total_balance(){
		int i,total=0,len=str.size();
		Integer p;
		String s="";
		for(i=0;i<len;i++){
			s=str.get(i).getAmount();
			p=Integer.parseInt(s);
			total+=p.intValue();
		}
		TextView total_balance=(TextView)findViewById(R.id.total_balance);
		if(total<0)total_balance.setTextColor(Color.RED);
		else if (total > 0 )total_balance.setTextColor(Color.GREEN);
		else total_balance.setTextColor(Color.WHITE);
		total_balance.setText("Total Account Balance: "+total);
	}
	public void getAllUsers(String[] users,int[] len){
		CommentsDataSource cd=new CommentsDataSource(this);
		cd.getAllUsers(users,len);
	}
}
