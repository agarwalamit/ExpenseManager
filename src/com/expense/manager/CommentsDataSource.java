package com.expense.manager;
import java.text.DateFormatSymbols;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import android.util.Log;

public class CommentsDataSource {

  // Database fields
  private SQLiteDatabase database;
  String default_color=new String("#FFFFFF");
  private MySQLiteHelper dbHelper;
  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
      MySQLiteHelper.COLUMN_NAME,MySQLiteHelper.COLUMN_AMOUNT,MySQLiteHelper.COLUMN_PRESENT };

  public CommentsDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
    Log.d("start", "opened");
  }

  public void close() {
    dbHelper.close();
    Log.d("start", "closed");
  }
  public int check_name_conflict_fromDatabase(String name){
	  try{
		  open();
   	  	  Cursor result=database.query(MySQLiteHelper.ACCOUNTS_TABLE,new String[]{MySQLiteHelper.COLUMN_NAME},MySQLiteHelper.COLUMN_NAME + " = '" + name + "' and "+MySQLiteHelper.COLUMN_PRESENT +"='1'" , null,null,null,null);
		  result.moveToFirst();
   	  	  if(result.isAfterLast()){
			  Log.d("start","no entry exists");
			  return 0;
		  }
		  else  Log.d("start","entry already exists");
   	  	  close();		  	
	  }
	  catch(Exception e){
		  Log.d("start","check_conflict");
	  }
	  return 1;
  }  
  public void update_arraylist_fromDatabase(ArrayList<Details> str){
	  str.clear();
  	  open();
	  Cursor c=database.query(MySQLiteHelper.ACCOUNTS_TABLE,new String[]{MySQLiteHelper.COLUMN_NAME,MySQLiteHelper.COLUMN_AMOUNT,MySQLiteHelper.COLUMN_PRESENT,MySQLiteHelper.COLUMN_TIME,MySQLiteHelper.COLUMN_COLOR},null,null,null,null,null);
	  c.moveToFirst();
	  while(!c.isAfterLast()){
		  if(c.getString(2).equalsIgnoreCase("1"))
			  str.add(new Details(c.getString(0),c.getString(1),c.getString(3),c.getString(4)));
		  c.moveToNext();
	  }
	  reverse(str);	  
	  close();
	  Log.d("start","update_arraylist_fromDatabase");
  }
  public void reverse(ArrayList<Details> str){
	  Details[] d=new Details[1000];
	  int i,j=str.size();
	  for(i=0;i<j;i++)
		  d[i]=str.get(i);
	  str.clear();	  
	  for(i=j-1;i>=0;i--)
		  str.add(d[i]);
  }
  public void updateamount(int total,String username,String time){
	  open();
	  ContentValues args = new ContentValues();
      args.put(MySQLiteHelper.COLUMN_AMOUNT,total);
      args.put(MySQLiteHelper.COLUMN_TIME,time);
      database.update(MySQLiteHelper.ACCOUNTS_TABLE, args, MySQLiteHelper.COLUMN_NAME+"='" + username+"'", null);
      //take care for same username but deleted..extra processing useless
	  close();
  }
  public void new_updateamount(int total,String username,String time){
	  open();
	  Cursor c=database.query(MySQLiteHelper.ACCOUNTS_TABLE,new String[]{MySQLiteHelper.COLUMN_AMOUNT},
			  MySQLiteHelper.COLUMN_NAME + " = '" + username + "' and "+MySQLiteHelper.COLUMN_PRESENT +"='1'" ,null,null,null,null);
	  c.moveToFirst();
	  String amtt="";
	  while(!c.isAfterLast()){
		  amtt=c.getString(0);
		  c.moveToNext();
	  }
	  ContentValues args = new ContentValues();
      args.put(MySQLiteHelper.COLUMN_AMOUNT,total);
      args.put(MySQLiteHelper.COLUMN_TIME,time);
      database.update(MySQLiteHelper.ACCOUNTS_TABLE, args, MySQLiteHelper.COLUMN_NAME+"='" + username+"'", null);
      //take care for same username but deleted..extra processing useless
	  close();
  }
  public void delete_user(String username){
	  open();
	  ContentValues args = new ContentValues();
      args.put(MySQLiteHelper.COLUMN_PRESENT,"0");
      database.update(MySQLiteHelper.ACCOUNTS_TABLE, args, MySQLiteHelper.COLUMN_NAME+"='" + username+"'", null);
      database.update(MySQLiteHelper.TRANSACTION_TABLE, args, MySQLiteHelper.COLUMN_NAME+"='" + username+"'", null);
	  close();
  }
  public int insert_to_database_registration(String name,String amount,String[] time){
	  open();
	  ContentValues cv=new ContentValues();
	  cv.put(MySQLiteHelper.COLUMN_NAME,name);
	  cv.put(MySQLiteHelper.COLUMN_AMOUNT,amount);
	  cv.put(MySQLiteHelper.COLUMN_PRESENT,1);
	  cv.put(MySQLiteHelper.COLUMN_COLOR,default_color);
	  time[0]=getTime();	
	  cv.put(MySQLiteHelper.COLUMN_TIME,time[0]);
	  try{
		  database.insert(MySQLiteHelper.ACCOUNTS_TABLE, null, cv);
	  }catch(Exception e){
		  Log.d("start","errro insert");
		  return 0;
		  }
	  close();
	  Log.d("start","register insert success");
	  return 1;
  }
  public void insert_to_database_registration_two(String name,String amount,String userreason,String[] time){	  
	  open();String temp=name+"  "+amount+" "+userreason+" "+time[0];
	  ContentValues cv=new ContentValues();
	  cv.put(MySQLiteHelper.COLUMN_NAME,name);	  	  
	  cv.put(MySQLiteHelper.COLUMN_AMOUNT,amount);
	  cv.put(MySQLiteHelper.COLUMN_PRESENT,1);	 
	  cv.put(MySQLiteHelper.COLUMN_TIME,time[0]);
	  Integer amt=Integer.parseInt(amount);
	  if(amt>0)
		  cv.put(MySQLiteHelper.COLUMN_GIVEN_TAKEN,"1");
	  else
		  cv.put(MySQLiteHelper.COLUMN_GIVEN_TAKEN,"0");
	  cv.put(MySQLiteHelper.COLUMN_REASON,userreason);
	  try{
		  database.insert(MySQLiteHelper.TRANSACTION_TABLE, null, cv);	
		  Log.d("start",cv.toString());
	  }catch(Exception e){
		  Log.d("start","errro insert");		  
		  }
	  close();
	  
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
  public int getcurrenttotal(String name){
	  open();
	  Cursor c=null;
	  try{
		  c=database.query(MySQLiteHelper.ACCOUNTS_TABLE,new String[]{MySQLiteHelper.COLUMN_AMOUNT},MySQLiteHelper.COLUMN_PRESENT+"='1' and "+MySQLiteHelper.COLUMN_NAME+"='"+name+"'" ,null,null,null,null);		  
	  }catch(Exception e){Log.d("start","exception");}
	  c.moveToFirst();
	  String temp="";
	  while(!c.isAfterLast()){		 
		 temp =c.getString(0);			  
		  c.moveToNext();
	  }
	  close();
	  Log.d("start",temp);
	  Integer amt=Integer.parseInt(temp);
	  return amt.intValue();
  }
  public void update_arraylist_fromDatabase_transaction(ArrayList<TransactionDetails> str,String username){
	  str.clear();
	  open();
	  Cursor c=null;int fds=0;
	  try{
		  c=database.query(MySQLiteHelper.TRANSACTION_TABLE,new String[]{MySQLiteHelper.COLUMN_AMOUNT,MySQLiteHelper.COLUMN_REASON,MySQLiteHelper.COLUMN_TIME,MySQLiteHelper.COLUMN_ID},MySQLiteHelper.COLUMN_PRESENT+"='1' and "+MySQLiteHelper.COLUMN_NAME+"='"+username+"'" ,null,null,null,null);		  
	  }catch(Exception e){Log.d("start","exception");}
	  c.moveToFirst();
	  String temp="";
	  while(!c.isAfterLast()){		 
		 temp =temp+c.getString(0)+"  "+c.getString(1)+"\n";
			  str.add(new TransactionDetails(c.getString(0),c.getString(1),c.getString(2),c.getString(3)));
		  c.moveToNext();
	  }
	  reverseagain(str);
	  close();
	  Log.d("start",temp);
  }
  public int getAmount(String username){
	  open();
	  Cursor c=database.query(MySQLiteHelper.ACCOUNTS_TABLE,new String[]{MySQLiteHelper.COLUMN_AMOUNT},
			  MySQLiteHelper.COLUMN_PRESENT+"='1' and "+MySQLiteHelper.COLUMN_NAME+"='"+username+"'" ,null,null,null,null);
	  c.moveToFirst();
	  String amt="";
	  while(!c.isAfterLast()){
		  amt=c.getString(0);
		  c.moveToNext();
	  }	  
	  close();
	  Integer x=Integer.parseInt(amt);
	  return x.intValue();
	  
  }
  public void updateamountTransactionDB_IdGiven(String id,String am){
	  open();
	  ContentValues args = new ContentValues();
      args.put(MySQLiteHelper.COLUMN_AMOUNT,am);
      database.update(MySQLiteHelper.TRANSACTION_TABLE, args, MySQLiteHelper.COLUMN_ID+"='" + id+"'", null);
	  close();
	  
  }
  public void reverseagain(ArrayList<TransactionDetails> str){
	  TransactionDetails[] d=new TransactionDetails[1000];
	  int i,j=str.size();
	  for(i=0;i<j;i++)
		  d[i]=str.get(i);
	  str.clear();	  
	  for(i=j-1;i>=0;i--)
		  str.add(d[i]);
  }
  public void display_registration_database(){
		open();
		String temp="<>";
		  Cursor c=null;
		  try{
			  c=database.query(MySQLiteHelper.TRANSACTION_TABLE,new String[]{MySQLiteHelper.COLUMN_AMOUNT,MySQLiteHelper.COLUMN_NAME,MySQLiteHelper.COLUMN_REASON},null,null,null,null,null);		  
		  }catch(Exception e){Log.d("start","exceptiondddddddddd");}
		  c.moveToFirst();
		  while(!c.isAfterLast()){		 
			 temp =temp+c.getString(0)+"  "+c.getString(1)+"\n";
			  c.moveToNext();
		  }
		  close();
		  Log.d("start",temp);
	}
  public void update_name_in_database(String realname,String updated_name){
	  open();
	  ContentValues args = new ContentValues();
      args.put(MySQLiteHelper.COLUMN_NAME,updated_name);
      database.update(MySQLiteHelper.ACCOUNTS_TABLE, args, MySQLiteHelper.COLUMN_NAME+"='" + realname+"'", null);
      database.update(MySQLiteHelper.TRANSACTION_TABLE, args, MySQLiteHelper.COLUMN_NAME+"='" + realname+"'", null);
	  close();	  
  }
  public void clearAmount(String name){
	  open();
	  ContentValues args = new ContentValues();
      args.put(MySQLiteHelper.COLUMN_AMOUNT,"0");
      database.update(MySQLiteHelper.ACCOUNTS_TABLE, args, MySQLiteHelper.COLUMN_NAME+"='" + name+"'", null);
      ContentValues cv = new ContentValues();
      cv.put(MySQLiteHelper.COLUMN_PRESENT,"0");
      database.update(MySQLiteHelper.TRANSACTION_TABLE, cv, MySQLiteHelper.COLUMN_NAME+"='" + name+"'",null);
	  close();
  }
  public void updateColor(String name,String color){	  
	  open();
	  ContentValues args = new ContentValues();
      args.put(MySQLiteHelper.COLUMN_COLOR,color);
      database.update(MySQLiteHelper.ACCOUNTS_TABLE, args, MySQLiteHelper.COLUMN_NAME+"='" + name+"'", null);
	  close();
  }
  public void delete_All(){
	  open();
	  ContentValues args = new ContentValues();
      args.put(MySQLiteHelper.COLUMN_PRESENT,"0");
      database.update(MySQLiteHelper.ACCOUNTS_TABLE, args, null, null);
      database.update(MySQLiteHelper.TRANSACTION_TABLE, args, null, null);  int asjdh=0;   
	  close();
  }
  public void getAllUsers(String users[],int []len){
	  open();
	  int i=-1;
	  Cursor c=null;
	  try{
		 c=database.query(MySQLiteHelper.ACCOUNTS_TABLE,new String[]{MySQLiteHelper.COLUMN_NAME},
				 MySQLiteHelper.COLUMN_PRESENT+"= '1'",
				  null,null,null,null); 
		 }catch(Exception e){Log.d("start","exceptiondddddddddd");}
	  c.moveToFirst();
	  while(!c.isAfterLast()){		 
		 users[++i] =c.getString(0);
		  c.moveToNext();
	  }
	  len[0]=i+1;
	  close();
  }
  
  
  
  
}