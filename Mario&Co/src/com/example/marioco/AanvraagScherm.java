package com.example.marioco;

import android.app.Activity; 
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class AanvraagScherm extends Activity implements OnClickListener{

TextView gekozen;	
String gekozenservice;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aanvraag);
        
        
        Intent aanvraagscherm = getIntent();
        gekozenservice = aanvraagscherm.getStringExtra("gekozenservice");
        
        
        TextView gekozen = (TextView)findViewById(R.id.textView1);
 	    this.gekozen = gekozen;
        gekozen.setText("U heeft gekozen voor de service" + gekozenservice + ", gelieve hier uw naam, adres, telefoonnummer en email in te vullen");
        
        
    }
    
	@Override
    public void onClick(View v) {
		
        switch(v.getId()){
        case R.id.bevestigen:
    		    		
    		Intent i = new Intent(this, AanvraagScherm.class );
    		startActivity(i);

    		finish();
        break;
        case R.id.annuleren:
        	Intent j = new Intent(this, MainActivity.class);
        	startActivity(j);
        	
        	finish();
        break;
        }
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
