package com.example.mobilebanking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import model.Result;
import model.ResultArray;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//import android.support.v7.internal.widget.AdapterViewICS.OnItemSelectedListener;

public class LoginActivity extends Activity{

//======================== global attribute declaration=======================================================================
	 EditText etAccountEt, EpinEt;
	 String SagentIdEt;
	 String passepinno;
	 @SuppressWarnings("unused")
	private TextView attempts;
	 Button login;
	 int counter = 3;
	  ProgressDialog pd;
	 Spinner spnr;
	 String[] celebrities = {
	      "Agent",
	      "Customer"
	     };

		@Override
//======================================= onCreate methods ==============================================================
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.login_activity);
			init();
//========================linking layout components to java component========================================
			spnr=(Spinner)findViewById(R.id.spinner);
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, celebrities);
		    spnr.setAdapter(adapter);
//===============================================verify item selected in spinner==================================================
		    spnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		 
		                  @Override
		                  public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
		                  @SuppressWarnings("unused")
						int position = spnr.getSelectedItemPosition();
		                    //Toast.makeText(getApplicationContext(),"You have selected "+celebrities[+position],Toast.LENGTH_LONG).show();
		                      
		                  }
		 
		                  @Override
		                  public void onNothingSelected(AdapterView<?> arg0) {
		                     
		 
		                  }
		 
		              }
		          );
		  }
	
		
//==================================================initializalion====================================================================	
		    private void init() {
			// TODO Auto-generated method stub
			etAccountEt = (EditText) findViewById(R.id.etAccount);
			EpinEt = (EditText) findViewById(R.id.Epin);
			//agentIdEt = (EditText) findViewById(R.id.agentId);
		     @SuppressWarnings("unused")
			String passedaccounlogout;
			pd = new ProgressDialog(this);
		}
//================================login options ===============================================================================	
		public void LoginClick(View v) { 

			if(spnr.getSelectedItem().toString().equals("Agent")){
				 if(!etAccountEt.getText().toString().equals("") && !EpinEt.getText().toString().equals("")){

					new LoginAgentAsyncTask().execute();
 
				 }else{
					 
					 Toast.makeText(getApplicationContext(),"empty not allowed",Toast.LENGTH_LONG).show();
				}
				}else if(spnr.getSelectedItem().toString().equals("Customer")){
				
					if(!etAccountEt.getText().toString().equals("") && !EpinEt.getText().toString().equals("")){

					new LoginAsyncTask().execute();
	 
					 }else{
						 
						 Toast.makeText(getApplicationContext(),"empty not allowed",Toast.LENGTH_LONG).show();
					}
					
				
			}else{
				
				 Toast.makeText(getApplicationContext(),"choose correct previlege",Toast.LENGTH_LONG).show();
				
			}
}
		
		
		
//=====================================================================================================================================================
		//==========================================================================================main AGENT  TWOOOOO===============================================
				public class LoginAgentAsyncTask extends AsyncTask<String, Void, String> {

					@Override
					protected void onPreExecute() {
						super.onPreExecute();
						
						pd.setTitle("Login Agent");
						pd.setMessage("please wait");
						pd.setCancelable(false);
						pd.setIndeterminate(true);
						pd.show();
					}

					@Override
					// protected String doInBackground(Void... arg0) {
					
		//======================================================do in back ground===============================================================================
					protected String doInBackground(String... params) {

						String accountNumber= etAccountEt.getText().toString();
						String pi = EpinEt.getText().toString();

						StringBuilder builder = new StringBuilder();
						ConnectUtils connector = new ConnectUtils();
						HttpClient client = connector.getNewHttpClient();

						String result = "FAILED";

						// LOAD SERVER PREF
						
						String sendString = "http://192.168.250.1/mobile/tryAgent.php?accountNumber="
								+ accountNumber + "&pin=" + pi;

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

						//

						//

					}
		//=========================================================================on post execute===================================================
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
						ResultArray resultat = resultParser.getResultParsedAgent(result);
//					SagentIdEt=resultat.getAgentId();
//					 Toast.makeText(getApplicationContext(),"error:" + resultat.getError() + ",result:"+ resultat.getResult() + ",message:"
//					 + resultat.getMessage()+",agent id:"+ resultat.getError(), Toast.LENGTH_SHORT).show();
						
						LayoutInflater li = LayoutInflater.from(LoginActivity.this);
						View promptsView;
						promptsView = li.inflate(R.layout.success_dialog, null);

						TextView txtSuccess = (TextView) promptsView.findViewById(R.id.txt_success);

						txtSuccess.setText(resultat.getMessage());

						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								LoginActivity.this);

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
						if(resultat.getResult().contains("success")){
//					Toast.makeText(getApplicationContext(),"byahageze",Toast.LENGTH_LONG).show();
							Intent intent = new Intent("com.example.mobilebanking.MenuListActivity");
							//Intent intent = new Intent(LoginActivity.this,MenuListActivity.class);
							// 3. or you can add data to a bundle
							 intent.putExtra("tempAccountNumber", etAccountEt.getText().toString());
							 intent.putExtra("temppassepinno",EpinEt.getText().toString());
							  intent.putExtra("tempAgentId",resultat.getError());
					        Bundle extras = new Bundle();
					        extras.putString("status", "Data Received!");
					 
					        // 4. add bundle to intent
					        intent.putExtras(extras);
					 
					        // 5. start the activity
					        startActivity(intent);
							
							//startActivityForResult(intent, 1);
						
						
							//Intent intent =new Intent(getApplicationContext(),MenuListActivity.class);
							//startActivity(intent);
						}

					}
				}

				
				
				
				
		//==========================================================================================================================================
//===================================================================async task class  CUSTOMER========================================================
		public class LoginAsyncTask extends AsyncTask<String, Void, String> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				
				pd.setTitle("Login Customer");
				pd.setMessage("please wait");
				pd.setCancelable(false);
				pd.setIndeterminate(true);
				pd.show();
			}

			@Override
			// protected String doInBackground(Void... arg0) {
			
//======================================================do in back ground===============================================================================
			protected String doInBackground(String... params) {

				String accountNumber= etAccountEt.getText().toString();
				String pi = EpinEt.getText().toString();

				StringBuilder builder = new StringBuilder();
				ConnectUtils connector = new ConnectUtils();
				HttpClient client = connector.getNewHttpClient();

				String result = "FAILED";

				// LOAD SERVER PREF
				
				String sendString = "http://192.168.250.1/mobile/tryThis.php?accountNumber="
						+ accountNumber + "&pin=" + pi;

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

				//

				//

			}
//=========================================================================on post execute===================================================
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
				
				LayoutInflater li = LayoutInflater.from(LoginActivity.this);
				View promptsView;
				promptsView = li.inflate(R.layout.success_dialog, null);

				TextView txtSuccess = (TextView) promptsView.findViewById(R.id.txt_success);

				txtSuccess.setText(resultat.getMessage());

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						LoginActivity.this);

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
				if(resultat.getResult().contains("success")){
					 Toast.makeText(getApplicationContext(),"byahageze",Toast.LENGTH_LONG).show();
					Intent intent = new Intent("com.example.mobilebanking.CustomerMenuListActivity");
					//Intent intent = new Intent(LoginActivity.this,MenuListActivity.class);
					// 3. or you can add data to a bundle
					intent.putExtra("tempAccountNumber", etAccountEt.getText().toString());
					intent.putExtra("temppassepinno",EpinEt.getText().toString());
			        Bundle extras = new Bundle();
			        extras.putString("status", "Data Received!");
			 
			        // 4. add bundle to intent
			        intent.putExtras(extras);
			 
			        // 5. start the activity
			        startActivity(intent);
					
					//startActivityForResult(intent, 1);
					//Intent intent = new Intent(LoginActivity.this, CustomerMenuListActivity.class);
					//intent.putExtra("tempAccountNumber", etAccountEt.getText().toString());
					//startActivity(intent);
				}

			}
		}

		
		
//===========================================================login agent asynch  AGENT==================================================		
		


}//end of super class
