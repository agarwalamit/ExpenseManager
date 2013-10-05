package com.expense.manager;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
public class ShowColors extends Activity{
	 String colors[][] = { { "822111", "AC2B16", "CC3A21", "E66550", "EFA093", "F6C5BE" },
	    		{ "A46A21", "CF8933", "EAA041", "FFBC6B", "FFD6A2", "FFE6C7" },
	    		{ "AA8831", "D5AE49", "F2C960", "FCDA83", "FCE8B3", "FEF1D1" },
	    		{ "076239", "0B804B", "149E60", "44B984", "89D3B2", "B9E4D0" },
	    		{ "1A764D", "2A9C68", "3DC789", "68DFA9", "A0EAC9", "C6F3DE" },
	    		{ "1C4587", "285BAC", "3C78D8", "6D9EEB", "A4C2F4", "C9DAF8" },
	    		{ "41236D", "653E9B", "8E63CE", "B694E8", "D0BCF1", "E4D7F5" },
	    		{ "83334C", "B65775", "E07798", "F7A7C0", "FBC8D9", "FCDEE8" },
	    		{ "000000", "434343", "666666", "999999", "CCCCCC", "EFEFEF" } };
	ArrayList<String> colorList;
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.showcolors);
	    colorList=new ArrayList<String>();	    
	    // add the color array to the list
        for (int i = 0; i< colors.length; i++) {
        	for (int j = 0; j<colors[i].length; j++) {
        		colorList.add("#"+colors[i][j]);
        	}
        }
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this));
	
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            
	            Intent i=new Intent();
	            i.putExtra("color", colorList.get(position));
	            setResult(RESULT_OK,i);
	            finish();
	        }
	    });
	}
}