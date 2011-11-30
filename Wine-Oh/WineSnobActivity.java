package com.hotuba.WineSnob;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class WineSnobActivity extends ListActivity {
	/** Called when the app is first started. */

	WineSnobAdapter wineAdapter; // For connecting to ListView
	public static final int TIME_ENTRY_REQUEST_CODE = 1; // request code for
															// startActivityforResult
	private final static String TAG = "Err";
	WineListDatabaseHelper databaseHelper;
	long itemPosition;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		databaseHelper = new WineListDatabaseHelper(this);
		// instantiated when app is started to create database

		// (deprecated) ListView listview = (ListView)
		// findViewById(R.id.wine_list);
		wineAdapter = new WineSnobAdapter(this,
				databaseHelper.getAllWineRecords());
		// Create wineAdapter that takes the returned cursor of
		// getAllWineRecords in databaseHelper class

		setListAdapter(wineAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuinflater = getMenuInflater();
		menuinflater.inflate(R.menu.wine_list_menu, menu);
		// Inflate menu and add wine_list_menu to it.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Grab id from selected item and check it against R values
		case R.id.add_wine_menu_item: {
			Intent intent = new Intent(this, AddWineActivity.class);
			startActivityForResult(intent, TIME_ENTRY_REQUEST_CODE);
			// More appropriate than startActivity(intent) since it returns back
			// to Activity with appended intent
			return true;
		}

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TIME_ENTRY_REQUEST_CODE) {
			// requestCode is a Constant to keep track of requests for Activity
			if (resultCode == RESULT_OK) {
				String name = data.getStringExtra("name");
				String notes = data.getStringExtra("notes");
				// intent appended data of the text in the edit text field and
				// mapped it respectively

				databaseHelper.saveWineRecord(name, notes);
				// Save data into database from edit text fields in
				// AddWineActivity's intent date

				wineAdapter.changeCursor(databaseHelper.getAllWineRecords());
				// After record is saved update cursor
			}
		} else
			super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		itemPosition = id;
		registerForContextMenu(l);
		// Listener for ContextMenu so when Item is long pressed menu comes up
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		// menuInfo can change depending on control sending event (not used in this version)
		menu.setHeaderTitle(wineAdapter.getCursor().getString(1));
		// Get name of wine and set to header title
		String[] menuItems = getResources().getStringArray(R.array.WineMenu);
		// Grab menu items (Edit and Delete) from XML
		for (int i = 0; i < menuItems.length; i++)
			menu.add(Menu.NONE, i, i, menuItems[i]);
		// add items to pop up context menu

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int menuItemIndex = item.getItemId();
		String Name = wineAdapter.getCursor().getString(1);
		switch (menuItemIndex) {
		// Just for Delete
		case 1:
			try {
				databaseHelper.deleteWineRecord(itemPosition);
				// Delete Wine Record at itemPosition which is the same as id in
				// database
				Log.v(TAG, "Called delete with row #" + itemPosition
						+ " for name " + Name);
				wineAdapter.changeCursor(databaseHelper.getAllWineRecords());
				// Update the cursor
			} catch (Exception ex) {
			}
		}
		return true;
	}
}
