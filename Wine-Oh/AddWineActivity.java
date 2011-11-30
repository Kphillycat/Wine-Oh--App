package com.hotuba.WineSnob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.hotuba.WineSnob.R;

public class AddWineActivity extends Activity {

	private static final String TAG = "Eroor";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_wine);
	}
	
	public void onCancel(View view){
		/* instead of Intent intent = new Intent(this, WineSnobActivity.class);
		startActivity(intent) use finish */
		finish(); //stop the AddWineActivity and remove from stack and go to main screen
	}
	
	public void onSave(View view){
		
		Intent intent = getIntent(); //retrieve starting intent from Activity
		
		EditText wineNameView = (EditText)findViewById(R.id.wine_name_entry);
		Log.v(TAG,"onSave got wine view");
		
		intent.putExtra("name", wineNameView.getText().toString()); 
		//Maps Name to text from entered Wine Name & put value in intent
		
		EditText wineNotesView = (EditText)findViewById(R.id.notes_entry);
		intent.putExtra("notes", wineNotesView.getText().toString());
		
		this.setResult(RESULT_OK, intent); //set Result to OK and pass intent
		
		finish();
	}
	
	
	
	
	
	

}
