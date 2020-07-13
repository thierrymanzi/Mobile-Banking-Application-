package com.example.mobilebanking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ViewMessagesActivity extends ListActivity {

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> productsList;

	// url to get all products list
	private static String url_accident="http://192.168.250.1/mobile/ListView1.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ACCNO = "accno";
	private static final String TAG_MESSAGE="message";
	private static final String TAG_CONTENT = "Content";
	private static final String TAG_DATE= "doneDate";
	//private static final String TAG_MESSAGEID = "messageId";
	private static final String TAG_DETAILS="details";


	// products JSONArray
	JSONArray message = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view__message);

		// Hashmap for ListView
		productsList = new ArrayList<HashMap<String, String>>();

		// Loading products in Background Thread
		new LoadAllProducts().execute();

		ListView lv = getListView();


		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
			}
		});

	}


	/**
	 * Background Async Task to Load all product by making HTTP Request
	 * */
	class LoadAllProducts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewMessagesActivity.this);
			pDialog.setMessage("Loading Messages News. Please wait.....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * getting All wanted persons from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_accident, "GET", params);

			// Check your log cat for JSON reponse
			Log.d("All accident news: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					message = json.getJSONArray(TAG_MESSAGE);

					// looping through All Products
					for (int i = 0; i < message.length(); i++) {
						JSONObject c = message.getJSONObject(i);

						// Storing each json item in variable
						String accno = c.getString("accno");
						String Content = c.getString("Content");
						String doneDate=c.getString("doneDate");
						String messageId=c.getString("messageId");
						
						String details="Acount Number: "+accno+" "+"\nContent: "+Content+" \ndDate: "+doneDate+"\nMessage ID:"+messageId; 

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_ACCNO,accno);
						map.put(TAG_CONTENT, Content);
						map.put(TAG_DATE, doneDate);
						//map.put(TAG_MESSAGEID,messageId);
						map.put(TAG_DETAILS, details);

						// adding HashList to ArrayList
						productsList.add(map);
					}
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(ViewMessagesActivity.this);
					builder.setTitle("Information");
					builder.setMessage("No Message news in this time. Thank you for using our application");

					// Use an EditText view to get user input.

					builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int whichButton) {
							Intent in=new Intent(getApplicationContext(),LoginActivity.class);
							startActivityForResult(in,0);
						}
					});


					builder.create().show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							ViewMessagesActivity.this, productsList,
							 R.layout.one_message, new String[] { TAG_ACCNO,
									TAG_DETAILS},
									new int[] { R.id.pid, R.id.name });
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
}