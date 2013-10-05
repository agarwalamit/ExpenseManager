package com.expense.manager;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DetailsAdapter extends ArrayAdapter<Details>{
	Context context;
	List<Details> mylist;
	int layout;
	public DetailsAdapter(Context c,int res,List<Details> l){
		super(c,res,l);
		context=c;
		layout=res;
		mylist=l;
	}
	public View getView(int position, View convertView, ViewGroup parent) {int ajsdf=0;
	     
	    // First let's verify the convertView is not null
	    if (convertView == null) {
	        // This a new view we inflate the new layout
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(layout, parent, false);
	    }
	    String color=mylist.get(position).getColor();
	    convertView.setBackgroundColor(Color.parseColor(color));
	    TextView name=(TextView)convertView.findViewById(R.id.name);
	    TextView bal=(TextView)convertView.findViewById(R.id.balance);		    
	    name.setText(mylist.get(position).getName());
	    String temp_bal=mylist.get(position).getAmount();
	    Integer temp_int=Integer.parseInt(temp_bal);
	    if(temp_int<0)
	    	bal.setTextColor(Color.RED);
	    else bal.setTextColor(Color.BLACK);
	    bal.setText("Rs."+temp_bal);
	    return convertView;
	}
}
