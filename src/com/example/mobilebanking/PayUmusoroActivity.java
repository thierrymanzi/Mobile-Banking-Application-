package com.example.mobilebanking;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class PayUmusoroActivity extends Activity {


ProgressDialog pd;
 EditText etAgentaccnoEt,etTransdateEt,EamountEt,etCustomeraccnoEt,etCommnentEt,etpin,etAgentidtt;
 Intent intes;
 String passedAccount,passeda;
 
 Spinner spnr;
 
 String[] celebrities = {
	      "UB-9345278"
	  
	      };
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umusoro_activity);
        intes=getIntent();
		 passedAccount = intes.getStringExtra("tempAccountNumber");
		 passeda=intes.getStringExtra("tempAgentId");
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
    
   
  
    @SuppressLint("SimpleDateFormat")
	private void init() {
    	
		// TODO Auto-generated method stub
    	//etTransIDEt = (EditText) findViewById(R.id.etTransID);
    	etAgentaccnoEt = (EditText) findViewById(R.id.etAgentaccno1);
    	etAgentaccnoEt.setText(passedAccount);
    	etAgentaccnoEt.setEnabled(false);
    	
    	etAgentidtt = (EditText) findViewById(R.id.etAgentid);
    	etAgentidtt.setText(passeda);
    	etAgentidtt.setEnabled(false);
    	
    	etTransdateEt = (EditText) findViewById(R.id.etTransdate);
//        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" ); 
//    	etTransdateEt.setText( sdf.format( new Date() ));
//    	etTransdateEt.setEnabled(false);
    	
    	java.util.Date currentdate=new java.util.Date();
    	etTransdateEt.setText(currentdate.toString());
    	etTransdateEt.setEnabled(false);
    	
    	EamountEt = (EditText) findViewById(R.id.Eamount);
		etCustomeraccnoEt = (EditText) findViewById(R.id.etCustomeraccno);
		etCommnentEt = (EditText) findViewById(R.id.etCommnent);
		etpin = (EditText) findViewById(R.id.etCustpin);
		
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

	public void UmusoroClick(View v) {
		new IshyarumusoroAsyncTask().execute();
	}

	public class IshyarumusoroAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd.setTitle("KWISHYURA UMUSORO");
			pd.setMessage("Buretse gato");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		
		}
	 
        protected String doInBackground(String... params) {

            // Create an HTTP client
            StringBuilder builder = new StringBuilder();
            ConnectUtils connector = new ConnectUtils();
            HttpClient client = connector.getNewHttpClient();

            String result = "FAILED";
            String stringUrl = null;
          //String transId = etTransIDEt.getText().toString();
    		
            String senderAccount = etAgentaccnoEt.getText().toString();
            String doneDate = etTransdateEt.getText().toString();
    		String amount = EamountEt.getText().toString();
    		String agentId = etAgentidtt.getText().toString();
    		String accoudestination = etCustomeraccnoEt.getText().toString();
    		String reg=spnr.getSelectedItem().toString();
    	    String pin=etpin.getText().toString();
    	    
    	
     
    	    
//   	    stringUrl = "http://192.168.250.1/mobile/kubikuza.php?senderAccount="
//                    +senderAccount
//                    +"&doneDate"
//                    +doneDate
//                    +"&amount"
//                    +amount
//                   +"&agentId="
//                   +agentId
//                    +"&accoudestination"
//                    +accoudestination
//                    +"&pin"
//                    +pin;
    	    
   	    
   	    
      try {
      stringUrl = "http://192.168.250.1/mobile/kwishyumusoro.php?senderAccount="
              +URLEncoder.encode(senderAccount,"UTF-8")
              +"&doneDate="+URLEncoder.encode(doneDate,"UTF-8")
              +"&amount="+URLEncoder.encode(amount,"UTF-8")
              +"&accoudestination="+URLEncoder.encode(accoudestination,"UTF-8")
              +"&pin="+URLEncoder.encode(pin,"UTF-8")
              +"&reg="+URLEncoder.encode(reg,"UTF-8")
              +"&agentId="+URLEncoder.encode(agentId,"UTF-8");

  } catch (UnsupportedEncodingException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
  }
   	    
    	    
    	
//            try {
//                stringUrl = "http://www.posdima.com/mobile/trykubitsa.php?senderAccount="
//                        + URLEncoder.encode(senderAccount,"UTF-8")
//                        + "&doneDate="+URLEncoder.encode(doneDate,"UTF-8")
//                        + "&amount="+URLEncoder.encode(amount,"UTF-8")
//                        + "&accoudestination="+URLEncoder.encode(accoudestination,"UTF-8")
//                       +"&pin="+URLEncoder.encode(pin,"UTF-8")
//                       +"&agentId="+URLEncoder.encode(agentId,"UTF-8");
//
//            } catch (UnsupportedEncodingException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }

            HttpGet httpGet = new HttpGet(stringUrl);

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

            // return null;
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

			LayoutInflater li = LayoutInflater.from(PayUmusoroActivity.this);
			View promptsView;
			promptsView = li.inflate(R.layout.success_dialog, null);

			TextView txtSuccess = (TextView) promptsView
					.findViewById(R.id.txt_success);
			

			
			txtSuccess.setText(resultat.getMessage());

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					PayUmusoroActivity.this);

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
