package com.hotuba.WineSnob;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WineListDatabaseHelper {
	
	private static final int DATABASE_VERSION = 3; //3: when changed to _id
	private static final String DATABASE_NAME = "winesnob.db";
	private static final String TABLE_NAME = "winerecords";
	
	public static final String WINESNOB_ID = "_id"; //That what cursor looks for in id column
	public static final String WINESNOB_COLUMN_NAME = "name";
	public static final String WINESNOB_COLUMN_NOTES 	= "notes";
	
	private WineSnobOpenHelper openHelper;
	private SQLiteDatabase database;
	
	public WineListDatabaseHelper(Context context){
		openHelper = new WineSnobOpenHelper(context);
		database = openHelper.getWritableDatabase();
	}
	
	public class WineSnobOpenHelper extends SQLiteOpenHelper { //inner class
		
		private final String TAG = "Database";

		WineSnobOpenHelper(Context context){
			super(context,DATABASE_NAME,null,DATABASE_VERSION); 
			//pass context, db name, null and version #, updates everytime the version # changes
			Log.v(TAG,"DB Created");
			
		}
		
		@Override
		public void onCreate(SQLiteDatabase database) {
			database.execSQL("CREATE TABLE " + TABLE_NAME + "(" + WINESNOB_ID + " integer primary key, " 
					+ WINESNOB_COLUMN_NAME + " TEXT, "
					+ WINESNOB_COLUMN_NOTES + " TEXT)");
			//database create using constants
			
			
			Log.v(TAG,"WineRecords Created");

		}

		@Override
		public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
			// calls when version # changes
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(database);
			
		}

	}
	
	public void saveWineRecord(String name, String notes){
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(WINESNOB_COLUMN_NAME, name);
		contentvalues.put(WINESNOB_COLUMN_NOTES, notes);
		//Contentvalues is a map of key/value pairs 
		
		database.insert(TABLE_NAME, null, contentvalues);
		//insert takes value of ContentValues
		
	}
	
	public Cursor getAllWineRecords(){
		return database.rawQuery("SELECT * FROM " + TABLE_NAME, null); //Selects all records from table
	}
	
	public void deleteWineRecord(long row){
		int i = database.delete(TABLE_NAME, WINESNOB_ID + "=" + row, null);
		//Deletes item (row) given row #
	}
}
