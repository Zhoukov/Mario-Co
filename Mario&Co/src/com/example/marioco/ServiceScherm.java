package com.example.marioco;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class ServiceScherm extends Activity implements OnClickListener{

	Button bevestigen;
	Button annuleren;
	String gekozenservice;
	TextView titel;
	TextView info;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
   
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        
       bevestigen = (Button) findViewById(R.id.bevestigen);
       bevestigen.setOnClickListener(this);
		
		
       annuleren = (Button) findViewById(R.id.annuleren);
       annuleren.setOnClickListener(this);
       
       Intent hoofdscherm = getIntent();
       gekozenservice = hoofdscherm.getStringExtra("gekozenservice");
       
       
       TextView titel = (TextView)findViewById(R.id.textView2);
	   this.titel = titel;
       titel.setText(gekozenservice);
       
       System.out.println(gekozenservice);
       
       TextView info = (TextView)findViewById(R.id.textView1);
       this.info = info;
       info.setText("LOREM IPSUM EEND");
       
       

       
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
	@Override
    public void onClick(View v) {
		
        switch(v.getId()){
        case R.id.bevestigen:
    		    		
    		Intent i = new Intent(this, AanvraagScherm.class );
    		i.putExtra("gekozen", (String)gekozenservice);
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
