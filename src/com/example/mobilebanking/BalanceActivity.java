package com.example.mobilebanking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import model.Result;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;

import utils.ResultParser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class BalanceActivity extends Activity{

	 
	 EditText etAccountEt, EpinEt;
	 Intent intes;
	 String passedAccount,passedpinno;
		ProgressDialog pd;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.checkbalance_activity);
			intes=getIntent();
			 passedAccount = intes.getStringExtra("tempAccountNumber");
			 passedpinno=intes.getStringExtra("temppassepinno");
			init();
		}

		private void init() {
			// TODO Auto-generated method stub
			etAccountEt = (EditText) findViewById(R.id.etAccount);
			etAccountEt.setText(passedAccount);
			
			etAccountEt.setEnabled(false);
			
			EpinEt = (EditText) findViewById(R.id.Epin);
			EpinEt.setText(passedpinno);
			EpinEt.setEnabled(false);
			pd = new ProgressDialog(this);

		}

		
		public void CheckBalanceClick(View v) {

//			Toast.makeText(
//					getApplicationContext(),
//					"Account iS:" + etAccountEt.getText().toString() + " and "
//							+ "Pin : " + EpinEt.getText().toString(),
//					Toast.LENGTH_SHORT).show();
			
			new CheckBalAsyncTask().execute();

		}

		public class CheckBalAsyncTask extends AsyncTask<String, Void, String> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				
				pd.setTitle("Check Balance");
				pd.setMessage("please wait....");
				pd.setCancelable(false);
				pd.setIndeterminate(true);
				pd.show();
			}

			@Override
			// protected String doInBackground(Void... arg0) {
			protected String doInBackground(String... params) {

				String senderAccount= etAccountEt.getText().toString();
				String pin = EpinEt.getText().toString();

				StringBuilder builder = new StringBuilder();
				ConnectUtils connector = new ConnectUtils();
				HttpClient client = connector.getNewHttpClient();

				String result = "FAILED";

				// LOAD SERVER PREF
				
				String sendString = "http://192.168.250.1/mobile/checkBalance.php?senderAccount="
						+senderAccount+"&pin="+pin;

				HttpGet httpGet = new HttpGet(sendString);
;

				
				try {
					HttpResponse response = client.execute(httpGet);
					StatusLine statusLine = response.getStatusLine();
					int statusCode = statusLine.getStatusCode();
					if (statusCode == 200) {
						HttpEntity entity = response.getEntity();
						InputStream content = entity.getContent();
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(content));
						String line;
						while ((line = reader.readLine()) != null) {
							builder.append(line);
						}
						result = builder.toString();

					} else {

					}
				} catch (ConnectTimeoutException e) {

					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				return result;

				//

				//

			}

			@SuppressLint("InflateParams")
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				// REMOVE DIALOG
				if (pd != null) {
					pd.dismiss();
					// b.setEnabled(true);
				}
				
				ResultParser resultParser = new ResultParser();
				Result resultat = resultParser.getResultParsed(result);

				// Toast.makeText(
				// getApplicationContext(),
				// "error:" + resultat.getError() + ", result:"
				// + resultat.getResult() + ",message:"
				// + resultat.getMessage(), Toast.LENGTH_SHORT).show()
				
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						BalanceActivity.this);

				
				

				
	
//========================================================================================
				
				LayoutInflater li = LayoutInflater.from(BalanceActivity.this);
				View promptsView;
				promptsView = li.inflate(R.layout.success_dialog, null);

				TextView txtSuccess = (TextView) promptsView.findViewById(R.id.txt_success);

				txtSuccess.setText(resultat.getMessage());

				
				// create alert dialog
				
				
				alertDialogBuilder.setView(promptsView);
				// set dialog message
				alertDialogBuilder.setCancelable(false).setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
				
				

				AlertDialog alertDialog = alertDialogBuilder.create();
				// show it
				alertDialog.show();
				
		
			}
		}


}
