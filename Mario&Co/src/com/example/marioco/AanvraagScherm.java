package com.example.marioco;


import com.example.marioco.Preferences;  


import android.app.Activity; 
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class AanvraagScherm extends Activity implements OnClickListener{

TextView gekozen;	
String gekozenservice;
EditText naam;
EditText email;
EditText telefoon;
EditText adres; 
Button bevestigen;
Button annuleren;
private ServerCommunicator serverCommunicator;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aanvraag);
        
        
        bevestigen = (Button) findViewById(R.id.bevestigen);
		bevestigen.setOnClickListener(this);
        
        annuleren = (Button)findViewById(R.id.annuleren);
        annuleren.setOnClickListener(this);
        
        Intent aanvraagscherm = getIntent();
        gekozenservice = aanvraagscherm.getStringExtra("gekozen");
        System.out.println(gekozen);
        
        TextView gekozen = (TextView)findViewById(R.id.textView1);
 	    this.gekozen = gekozen;
        gekozen.setText("U heeft gekozen voor de service " + gekozenservice + ", gelieve hier uw naam, adres, telefoonnummer en email in te vullen");
        
		naam = (EditText) findViewById(R.id.naam);
		//naam.setHint("Naam");
		
		adres = (EditText) findViewById(R.id.adres);
		//adres.setHint("Adres");
		
		telefoon = (EditText) findViewById(R.id.telefoon);
		//telefoon.setHint("0611111111");
		
		email = (EditText) findViewById(R.id.email);
		//telefoon.setHint("E-mail");
		
		String[] prefs = Preferences.getInstance(this).getCustomerInfoPreferences();
		if(prefs[0] != null)
			this.naam.setText(prefs[0]);
		if(prefs[1] != null)
			this.adres.setText(prefs[1]);
		if(prefs[2] != null)
			this.telefoon.setText(prefs[2]);
		if(prefs[3] != null)
			this.email.setText(prefs[3]);
    }
    
	@Override
    public void onClick(View v) {
		
		String[] newprefs = { this.naam.getText().toString(), this.adres.getText().toString(), this.telefoon.getText().toString(), this.email.getText().toString() };
		Preferences.getInstance(this).updateCustomerInfoPreferences(newprefs);
		
        switch(v.getId()){
        case R.id.bevestigen:
    		
        	  String message = "De klant: " + naam.getText().toString() + " heeft gekozen voor de service: " + gekozenservice.toString() + ". " + "Adres: " + adres.getText().toString() + ", " + "Telefoon: " + telefoon.getText().toString() + ", " + "Email: " + email.getText().toString() + System.getProperty("line.separator");
        	  System.out.println(message);
        	  
        	  
        	  this.serverCommunicator = new ServerCommunicator( this, "145.101.80.159", 4444, message );
        	  
        	  Toast.makeText(getApplicationContext(), "Uw aanvraag is verzonden, wij nemen zo snel mogelijk contact met u op.", Toast.LENGTH_SHORT).show();

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
		Intent i = new Intent(this, ServiceScherm.class);
		startActivity(i);
		finish();
       
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	
	        	//System.out.println("UP Pressed");
	        	
	        	Intent i = new Intent(this, ServiceScherm.class);
	        	startActivity(i);
	        	
	        	finish();
	        	
	            return(true);
	    }

	    return(super.onOptionsItemSelected(item));
	}

}
