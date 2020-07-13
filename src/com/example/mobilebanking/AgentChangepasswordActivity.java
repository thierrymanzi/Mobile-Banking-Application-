
package com.example.mobilebanking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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



public class AgentChangepasswordActivity extends Activity {

 ProgressDialog pd;
 EditText  etAccnoEt,etoldPinnoEt,EnewPinEt,retypenewPinEt;
 Intent intes;
 String passedAccount,passeda;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agentpasswordchange);
        intes=getIntent();
		 passedAccount = intes.getStringExtra("tempAccountNumber");
		// passeda=intes.getStringExtra("tempAgentId");
        init();
    }
    
    
    private void init() {
		// TODO Auto-generated method stub
    	etAccnoEt = (EditText) findViewById(R.id.etAccno);
    	etAccnoEt.setText(passedAccount);
    	etAccnoEt.setEnabled(false);
    	
    	etoldPinnoEt = (EditText) findViewById(R.id.etoldPinno);
    	EnewPinEt = (EditText) findViewById(R.id.EnewPin);
    	retypenewPinEt = (EditText) findViewById(R.id.retypenewPin);
		pd = new ProgressDialog(this);
		
	}

   

	public void AgentChangeClick(View v) {
		new AgentChangepasswordAsyncTask().execute();
	}

	public class AgentChangepasswordAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd.setTitle("Agent Changing Password");
			pd.setMessage("Tegereza.....");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}

		@Override
		// protected String doInBackground(Void... arg0) {
		protected String doInBackground(String... params) {
			
			//etAccnoEt,etoldPinnoEt,EnewPinEt,retypenewPinEt
			
			String accountNumber =etAccnoEt.getText().toString();
			String oldpin=etoldPinnoEt.getText().toString();
			String newpin=EnewPinEt.getText().toString();
			String newpinrepeat= retypenewPinEt.getText().toString();
			
			

			StringBuilder builder = new StringBuilder();
			ConnectUtils connector = new ConnectUtils();
			HttpClient client = connector.getNewHttpClient();

			String result = "FAILED";
			 String sendString = null;
			// LOAD SERVER PREF
				
//			String sendString ="http://www.posdima.com/mobile/hinduraagent.php?username=" 
//					+accountNumber 
//					+"&password=" 
//					+oldpin
//					+"&newpassword="
//					+newpin
//					+"&confirmnewpassword="
//					+newpinrepeat;
			
	
			 try {
               sendString = "http://192.168.250.1/mobile/hinduraagent.php?username="
                       + URLEncoder.encode(accountNumber,"UTF-8")
                       + "&password="+URLEncoder.encode(oldpin,"UTF-8")
                      + "&newpassword="+URLEncoder.encode(newpin,"UTF-8")
                      + "&confirmnewpassword="+URLEncoder.encode(newpinrepeat,"UTF-8");
                    

           } catch (UnsupportedEncodingException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
          }
			
			
			HttpGet httpGet = new HttpGet(sendString);

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

//			Toast.makeText(
//					getApplicationContext(),
//					"error:" + resultat.getError() + ", result:"
//							+ resultat.getResult() + ",message:"
//							+ resultat.getMessage(), Toast.LENGTH_SHORT).show();

			LayoutInflater li = LayoutInflater.from(AgentChangepasswordActivity.this);
			View promptsView;
			promptsView = li.inflate(R.layout.success_dialog,null);

			TextView txtSuccess = (TextView) promptsView
					.findViewById(R.id.txt_success);
			

			
			txtSuccess.setText(resultat.getMessage());

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					AgentChangepasswordActivity.this);

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
