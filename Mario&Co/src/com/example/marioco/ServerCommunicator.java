package com.example.marioco;

import java.io.BufferedWriter;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

public class ServerCommunicator  implements Runnable {
	private Activity activity;
	private Thread thread;
	private JSONObject service;
	private JSONObject message;
	private String ip = "145.101.81.212";
	private int port = 4444;
	public static String response;

	public ServerCommunicator(Activity activity, String ip, int port,
			JSONObject aanvraag) {
		// we gebruiken de activity om de userinterface te updaten
		this.activity = activity;
		//klaarMetLaden = false;
		// gegevens om naar de server te verbinden en een message te sturen
		this.message = aanvraag;
		this.ip = ip;
		this.port = port;

		// de nieuwe thread kan tekst verzenden en ontvangen van en naar een
		// server
		this.thread = new Thread(this);
		thread.start();
	}

	// dit is een methode die niet op de UI thread wordt aangeroepen, maar door
	// onze eigen nieuwe thread
	// we kunnen dus niet zomaar ontvangen berichten in een userinterface object
	// stoppen m.b.v. view.setText( message )
	// hier gebruiken we de activity voor: activity.runOnUiThread( activity )
	@Override
	public void run() {
		try {
			Socket serverSocket = new Socket();
			serverSocket.connect(new InetSocketAddress(this.ip, this.port),
					4000);

			// verzend een bericht naar de server
			this.sendMessage(message, serverSocket);

			InputStream input;

			// zorgt voor een respons van de server
			try {
				input = serverSocket.getInputStream();
				BufferedReader responseStreamReader = new BufferedReader(
						new InputStreamReader(input));
				String line = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ((line = responseStreamReader.readLine()) != null) {
					stringBuilder.append(line);
				}
				responseStreamReader.close();

				this.response = stringBuilder.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
			response.replaceAll("null", "");
			System.out.println("Response: " + response);
			//klaarMetLaden = true;
	
			
			
			// gebruik de volgende twee methoden van de activity om informatie
			// naar de UI thread (de activity) te sturen
			// this.activity.setOntvangenBericht(
			// "We hebben het bericht verzonden" );
			// this.activity.runOnUiThread( this.activity );
		}

		catch (UnknownHostException e) {
			Log.d("debug", "can't find host");
		}

		catch (SocketTimeoutException e) {
			Log.d("debug", "time-out");
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ook deze methoden kunnen niet naar de UI direct communiceren, hou hier
	// rekening mee
	private void sendMessage(JSONObject message, Socket serverSocket) {
		OutputStreamWriter outputStreamWriter = null;

		try {

			OutputStream blabla = serverSocket.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(blabla);
			System.out.println("send message");
		}

		catch (IOException e2) {
			e2.printStackTrace();
		}

		if (outputStreamWriter != null) {
			BufferedWriter bufferedWriter = new BufferedWriter(
					outputStreamWriter);
			PrintWriter writer = new PrintWriter(bufferedWriter, true);

			writer.println(message);
		}
	}

	// wacht op server bericht (na versturen)
	private String waitForResponse(Socket serverSocket) {
		String serverMessage = null;

		BufferedReader reader;

		// ... wacht op een bericht van de server, return het antwoord
		try {
			reader = new BufferedReader(new InputStreamReader(
					serverSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return serverMessage;
	}

}