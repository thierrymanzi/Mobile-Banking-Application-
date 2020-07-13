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

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class TransferActivity extends Activity {


ProgressDialog pd;
 EditText etAgentaccnoEt,etTransdateEt,EamountEt,etCustomeraccnoEt,etCommnentEt;
 Intent intes;
 String passedAccount,passeda;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_activity);
        intes=getIntent();
		 passedAccount = intes.getStringExtra("tempAccountNumber");
		 //passeda=intes.getStringExtra("tempAgentId");
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
//        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" ); 
//    	etTransdateEt.setText( sdf.format( new Date() ));
//    	etTransdateEt.setEnabled(false);
    	
    	java.util.Date currentdate=new java.util.Date();
    	etTransdateEt.setText(currentdate.toString());
    	etTransdateEt.setEnabled(false);
    	
    	EamountEt = (EditText) findViewById(R.id.Eamount);
		etCustomeraccnoEt = (EditText) findViewById(R.id.etCustomeraccno);
		etCommnentEt = (EditText) findViewById(R.id.etCommnent);
		pd = new ProgressDialog(this);
		
	}



	public void TransferClick(View v) {
		 String senderAccount = etAgentaccnoEt.getText().toString();
         String doneDate = etTransdateEt.getText().toString();
 		String amount = EamountEt.getText().toString();
 		String accoudestination = etCustomeraccnoEt.getText().toString();
 	    String comments = etCommnentEt.getText().toString();
		
		Toast.makeText(
    			getApplicationContext(),"sender"+senderAccount+"amount:"+amount+"accountdest:"+accoudestination+"doneDate:"+doneDate+"comment:"+comments,Toast.LENGTH_SHORT).show();
		
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
	 
        protected String doInBackground(String... params) {

            // Create an HTTP client
            StringBuilder builder = new StringBuilder();
            ConnectUtils connector = new ConnectUtils();
            HttpClient client = connector.getNewHttpClient();

            String result = "FAILED";
            String stringUrl = null;
          //String transId = etTransIDEt.getText().toString();
    		
            String senderAccount =etAgentaccnoEt.getText().toString();
            String doneDate =etTransdateEt.getText().toString();
    		String amount =EamountEt.getText().toString();
    		String accoudestination = etCustomeraccnoEt.getText().toString();
    	    String comments = etCommnentEt.getText().toString();
 
            try {
            	
//           	stringUrl="http://192.168.250.1/mobile/TransferCustomer.php?senderAccount="
//                        +senderAccount
//                       + "&doneDate="
//                        +doneDate
//                        +"&amount="
//                        +amount
//                        +"&accoudestination="+accoudestination
//                        +"&comments="+comments;
            	
            	
               stringUrl="http://192.168.250.1/mobile/TransferCustomer.php?senderAccount="
                       +URLEncoder.encode(senderAccount,"UTF-8")
                       +"&doneDate="+URLEncoder.encode(doneDate,"UTF-8")
                       +"&amount="+URLEncoder.encode(amount,"UTF-8")
                       +"&accoudestination="+URLEncoder.encode(accoudestination,"UTF-8")
                       +"&comments="+URLEncoder.encode(comments,"UTF-8");
          } catch (UnsupportedEncodingException e1) {
               // TODO Auto-generated catch block
                e1.printStackTrace();
            }

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

			LayoutInflater li = LayoutInflater.from(TransferActivity.this);
			View promptsView;
			promptsView = li.inflate(R.layout.success_dialog, null);

			TextView txtSuccess = (TextView) promptsView
					.findViewById(R.id.txt_success);
			

			
			txtSuccess.setText(resultat.getMessage());

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					TransferActivity.this);

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
