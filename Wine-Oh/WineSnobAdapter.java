package com.hotuba.WineSnob;

import java.util.ArrayList;

import com.hotuba.WineSnob.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;


public class WineSnobAdapter extends CursorAdapter {
	
	private ArrayList<WineRecord> Wines = new ArrayList<WineRecord>();
	
	
	public WineSnobAdapter(Context context, Cursor cursor){
		super(context,cursor);
		
	}

	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView nameView = (TextView) view.findViewById(R.id.wine_name_view);
		TextView notesView = (TextView) view.findViewById(R.id.wine_notes_view);
		//Grab variables by ids from listviews layout
		
		nameView.setText(cursor.getString(1));
		notesView.setText(cursor.getString(cursor.getColumnIndex("notes")));
		//Pull data from cursor (by index # or getColumnIndex) and set the text in the variables
		//The Adapter handles the interation for us we just need to display the row
	}


	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.wine_list_item, parent, false);
		//Inflate the single list item view to be added to listview and return it
		return view;
	}
	
}
