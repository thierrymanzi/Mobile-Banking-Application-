package com.example.mobilebanking;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Result;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;

import utils.ResultParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class CustomerTransferActivity extends Activity {


ProgressDialog pd;
 EditText etAgentaccnoEt,etTransdateEt,EamountEt,etCustomeraccnoEt,etCommnentEt;
 Intent intes;
 String passedAccount,passeda;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customertransfer_activity);
        intes=getIntent();
		 passedAccount = intes.getStringExtra("tempAccountNumber");
		 passeda=intes.getStringExtra("tempAgentId");
        init();
    }
    @SuppressLint("SimpleDateFormat")
	private void init() {
    	
		// TODO Auto-generated method stub
    	//etTransIDEt = (EditText) findViewById(R.id.etTransID);
    	etAgentaccnoEt = (EditText) findViewById(R.id.etAgentaccno1);
    	etAgentaccnoEt.setText(passedAccount);
    	etAgentaccnoEt.setEnabled(false);
        etTransdateEt = (EditText) findViewById(R.id.etTransdate);
       SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" ); 
    	etTransdateEt.setText( sdf.format( new Date() ));
    	etTransdateEt.setEnabled(false);
    	
    	
		
		EamountEt = (EditText) findViewById(R.id.Eamount);
		etCustomeraccnoEt = (EditText) findViewById(R.id.etCustomeraccno);
		
		etCommnentEt = (EditText) findViewById(R.id.etCommnent);
		pd = new ProgressDialog(this);
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}

	public void TransferClick(View v) {
		new TransferAsyncTask().execute();
	}

	public class TransferAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd.setTitle("Transfer");
			pd.setMessage("please wait");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}

		@Override
		// protected String doInBackground(Void... arg0) {
		protected String doInBackground(String... params) {
			

			//String transId = etTransIDEt.getText().toString();
			String senderAccount = etAgentaccnoEt.getText().toString();
			String doneDate = etTransdateEt.getText().toString();
			
			String amount = EamountEt.getText().toString();
			
			String accoudestination = etCustomeraccnoEt.getText().toString();
		
			String comments = etCommnentEt.getText().toString();
			

			StringBuilder builder = new StringBuilder();
			ConnectUtils connector = new ConnectUtils();
			HttpClient client = connector.getNewHttpClient();

			String result = "FAILED";

			// LOAD SERVER PREF
			
			
			String sendString="http://192.168.250.1/mobile/TransferCustomer.php?senderAccount=" 
					+senderAccount 
					+"&doneDate=" 
					+doneDate
					+"&amount="
					+amount
					+"&accoudestination="
					+accoudestination
					+"&comments="
					+comments;
			

			HttpGet httpGet = new HttpGet(sendString.trim());

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

			Toast.makeText(
				getApplicationContext(),
					"error:" + resultat.getError() + ", result:"
						+ resultat.getResult() + ",message:"
						+ resultat.getMessage(), Toast.LENGTH_SHORT).show();

			LayoutInflater li = LayoutInflater.from(CustomerTransferActivity.this);
			View promptsView;
			promptsView = li.inflate(R.layout.success_dialog, null);

			TextView txtSuccess = (TextView) promptsView
					.findViewById(R.id.txt_success);
			

			
			txtSuccess.setText(resultat.getMessage());

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					CustomerTransferActivity.this);

			// set prompts.xml to alertdialog builder
			alertDialogBuilder.setView(promptsView);
			// set dialog message
			alertDialogBuilder.setCancelable(false).setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
						}
					});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			// show it
			alertDialog.show();

		}
	}

}
