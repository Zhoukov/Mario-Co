package com.example.marioco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.marioco.Preferences;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import server.ServiceData;

public class MainActivity extends Activity implements OnItemSelectedListener,
		OnClickListener {

	Spinner spinner;
	ListView service;
	ArrayAdapter<String> adapter;
	static ArrayList<String> list;
	static ArrayList<JSONObject> infoList;
	static MainActivity activity;
	TextView serviceinfo;
	EditText ipadres;
	public static String ip = "145.101.81.212";
	public static int port = 4444;
	public String informatiebeknopt = null;
	public static String serviceNaam;
	Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ipadres = (EditText) findViewById(R.id.ipadres);
		this.ip = ipadres.getText().toString();

		list = new ArrayList<String>();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("servicelijst", "");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String response = null;
		try {
			try {

				response = new ServerCommunicator(ip, port,
						jsonObject.toString()).execute().get();

			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if (response == null) {

			Toast.makeText(MainActivity.this,
					"Verbinding met de server niet mogelijk.",
					Toast.LENGTH_LONG).show();
		} else {

			String jsonFix = response.replace("null", "");

			JSONArray JArray = null;
			try {
				JArray = new JSONArray(jsonFix);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			JSONObject jObject = null;
			String value = null;
			list = new ArrayList<String>();

			for (int i = 0; i < JArray.length(); i++) {
				try {
					jObject = JArray.getJSONObject(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					value = jObject.getString("naam");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				list.add(value);

			}

			infoList = new ArrayList<JSONObject>();
			JSONObject beknoptjObject = new JSONObject();
			try {
				for (int i = 0; i < list.size(); i++) {
					beknoptjObject.put("informatiebeknopt", list.get(i));
					try {
						try {
							informatiebeknopt = new ServerCommunicator(ip,
									port, beknoptjObject.toString()).execute()
									.get();

						} catch (ExecutionException e) {
							e.printStackTrace();
						}
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					String infoFix = informatiebeknopt.replace("null", "");
					JSONObject fixedjObject = new JSONObject(infoFix);
					infoList.add(fixedjObject);

					Log.i("informatiebeknopt", infoFix);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		spinner = (Spinner) findViewById(R.id.services);

		adapter = new ArrayAdapter<String>(MainActivity.this,
				android.R.layout.simple_spinner_dropdown_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(this);

		button = (Button) findViewById(R.id.selecteren);
		button.setOnClickListener(this);

		if (Preferences.getInstance(this) == null)
			System.out.println("no instance of preferences");
		String[] pref = Preferences.getInstance(this)
				.getMainActivityPreferences();
		if (pref[0] != null)
			spinner.setSelection(Integer.parseInt(pref[0]));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub

		String[] loc = { "" + position };
		Preferences.getInstance(MainActivity.this)
				.updateMainActivityPreferences(loc);

		TextView beknopteinfo = (TextView) findViewById(R.id.text);

		try {
			beknopteinfo.setText(infoList.get(position).getString(
					"informatiebeknopt"));
			serviceNaam = list.get(position);

		} catch (Exception e) {

		}

	}

	public void onClick(View v) {
		Intent i = new Intent(MainActivity.this, ServiceScherm.class);
		i.putExtra("naam", serviceNaam.toString());
		startActivity(i);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

}
