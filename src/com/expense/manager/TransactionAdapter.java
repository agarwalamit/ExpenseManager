package com.expense.manager;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TransactionAdapter extends ArrayAdapter<TransactionDetails>{
	Context context;
	List<TransactionDetails> mylist;
	public TransactionAdapter(Context c,int res,List<TransactionDetails> l){
		super(c,res,l);
		context=c;
		mylist=l;		
	}
	public View getView(int position, View convertView, ViewGroup parent) {
	     
	    // First let's verify the convertView is not null
	    if (convertView == null) {
	        // This a new view we inflate the new layout
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        //convertView = inflater.inflate(R.layout.rowtransactiondetails, parent, false);
	        convertView = inflater.inflate(R.layout.new_rowtransactiondetails, parent, false);
	    }
	    //if(position%2==0)
	    	//convertView.setBackgroundColor(Color.YELLOW);
	    //else 
	    	//convertView.setBackgroundColor(Color.GRAY);
	    TextView date=(TextView)convertView.findViewById(R.id.datetimes);
	    TextView bal=(TextView)convertView.findViewById(R.id.amount_transaction);	 
	    TextView reason=(TextView)convertView.findViewById(R.id.reason_transaction);	
	    String temp_bal=mylist.get(position).getAmount();
	    Integer temp_int=Integer.parseInt(temp_bal);
	    if(temp_int<0)
	    	bal.setTextColor(Color.RED);
	    else bal.setTextColor(Color.BLACK);
	    bal.setText("Rs."+temp_bal);
	    //bal.setText(mylist.get(position).getAmount());
	    reason.setText("Reason: "+mylist.get(position).getReason());
	    date.setText(mylist.get(position).getTime());
	    Log.d("start","timed="+mylist.get(position).getTime());
	    return convertView;
	}
}
