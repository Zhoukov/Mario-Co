package com.example.marioco;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;


public class ServiceScherm extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

       
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
	{
        if ((keyCode == KeyEvent.KEYCODE_BACK)) 
        {
            onBackPressed();
            System.out.println("Back pressed");
        }
       
		return true;
	}
	@Override
    public void onBackPressed()
	{
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		finish();
       
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	
	        	//System.out.println("UP Pressed");
	        	
	        	Intent i = new Intent(this, MainActivity.class);
	        	startActivity(i);
	        	
	        	finish();
	        	
	            return(true);
	    }

	    return(super.onOptionsItemSelected(item));
	}

}
