package com.expense.manager;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    int[] colorList=new int[100];
    public ImageAdapter(Context c) {
        mContext = c;
        int pos=-1;
        // add the color array to the list
        for (int i = 0; i< colors.length; i++) {
        	for (int j = 0; j<colors[i].length; j++) {
        		colorList[++pos]=Color.parseColor("#" + colors[i][j]);
        		Log.d("start","color="+colorList[pos]);
        	}
        }
    }

    public int getCount() {
        return colors.length*colors[0].length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        //imageView.setImageResource(mThumbIds[position]);
        int col=colorList[position];
        imageView.setBackgroundColor(col);
        return imageView;
    }
    
    String colors[][] = { { "822111", "AC2B16", "CC3A21", "E66550", "EFA093", "F6C5BE" },
    		{ "A46A21", "CF8933", "EAA041", "FFBC6B", "FFD6A2", "FFE6C7" },
    		{ "AA8831", "D5AE49", "F2C960", "FCDA83", "FCE8B3", "FEF1D1" },
    		{ "076239", "0B804B", "149E60", "44B984", "89D3B2", "B9E4D0" },
    		{ "1A764D", "2A9C68", "3DC789", "68DFA9", "A0EAC9", "C6F3DE" },
    		{ "1C4587", "285BAC", "3C78D8", "6D9EEB", "A4C2F4", "C9DAF8" },
    		{ "41236D", "653E9B", "8E63CE", "B694E8", "D0BCF1", "E4D7F5" },
    		{ "83334C", "B65775", "E07798", "F7A7C0", "FBC8D9", "FCDEE8" },
    		{ "000000", "434343", "666666", "999999", "CCCCCC", "EFEFEF" } };         
}