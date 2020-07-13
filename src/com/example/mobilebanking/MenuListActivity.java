package com.example.mobilebanking;

import java.util.ArrayList;

import utils.Item;
import adapters.GridViewAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MenuListActivity extends Activity implements OnItemClickListener {

	// grid
	ListView gridView;
	ArrayList<Item> gridArray = new ArrayList<Item>();
	GridViewAdapter customGridAdapter;
           Intent intes;
           String passedAccount,passedAgentId,passedpinno;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_list_activity);
		 intes=getIntent();
		 passedAccount = intes.getStringExtra("tempAccountNumber");
		 passedpinno=intes.getStringExtra("temppassepinno");
		 passedAgentId=intes.getStringExtra("tempAgentId");
		// action bar styling
		// Toast.makeText(getApplicationContext(),passedAgentId, Toast.LENGTH_SHORT).show();
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f47701")));
		// grid info

		gridArray.add(new Item(BitmapFactory.decodeResource(
				
			this.getResources(), R.drawable.balance1), "Withdraw/Kubikuza"));
		
		
		gridArray.add(new Item(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.balance1), "Transfer/Koherereza"));

		gridArray.add(new Item(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.balance1), " AgentCheck Balance/Kureba Konti Yawe"));
		
		
		gridArray.add(new Item(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.balance1), "Deposit/Kubitsa"));
		
		gridArray.add(new Item(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.balance1), "Change Password/Guhindura Umubare W'Ibanga"));
		
		
		gridArray.add(new Item(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.balance1), "List Of All Messages/Ilisiti Y'Ubutumwa Bwoherejwe"));
		
		
		gridArray.add(new Item(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.balance1), "Buy Electricity/Kugura Umuriro"));
		
		
		gridArray.add(new Item(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.balance1), "Tax Payment/Kwishyura umusoro"));
		
		gridArray.add(new Item(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.balance1), "Logout/Kuva muri Konti Yawe"));
		
		
	
		gridView = (ListView) findViewById(R.id.gridView1);
		customGridAdapter = new GridViewAdapter(this, R.layout.row_grid,
				gridArray);
		gridView.setAdapter(customGridAdapter);

		gridView.setOnItemClickListener(this);

	}

	// ------------------------------------

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			Intent iWithdro = new Intent(getApplicationContext(),WithdrawActivity.class);
			
			// 3. or you can add data to a bundle
			iWithdro.putExtra("tempAccountNumber", passedAccount);
			iWithdro.putExtra("tempAgentId",passedAgentId);
	        Bundle extras = new Bundle();
	        extras.putString("status", "Data Received!");
	 
	        // 4. add bundle to intent
	        iWithdro.putExtras(extras);
	 
	        // 5. start the activity
	        startActivity(iWithdro);
//			
//			Intent iWithdro=new Intent(getApplicationContext(),WithdrawActivity.class);
//		
//			 startActivity(iWithdro);

			break;
		case 1:
			Intent iTranfer = new Intent(getApplicationContext(),TransferActivity.class);
			// 3. or you can add data to a bundle
			iTranfer.putExtra("tempAccountNumber", passedAccount);
			//iTranfer.putExtra("tempAgentId",passedAgentId );
				        Bundle extrastrans = new Bundle();
				        extrastrans.putString("status", "Data Received!");
				 
				        // 4. add bundle to intent
				        iTranfer.putExtras(extrastrans);
				 
				        // 5. start the activity
				       startActivity(iTranfer);
			break;
		case 2:
			
		
			Intent iCheckbal=new Intent(getApplicationContext(),AgentCkekBalanceActivity.class);
			iCheckbal.putExtra("tempAccountNumber", passedAccount);
			iCheckbal.putExtra("temppassepinno", passedpinno);
			 //iCheckbal.putExtra("tempAgentId",passedAgentId );
				        Bundle extracheckbal = new Bundle();
				        extracheckbal.putString("status", "Data Received!");
				 
				        // 4. add bundle to intent
				        iCheckbal.putExtras(extracheckbal);
				 
				        // 5. start the activity
				       startActivity(iCheckbal);
			
			break;
			
		case 3:
			
			  Intent iDepos=new Intent(getApplicationContext(),DepositActivity.class);
			  iDepos.putExtra("tempAccountNumber",passedAccount);
			  iDepos.putExtra("tempAgentId",passedAgentId );
					        Bundle extracdeposi = new Bundle();
					        extracdeposi.putString("status", "Data Received!");
					 
					        // 4. add bundle to intent
					        iDepos.putExtras(extracdeposi);
					 
					        // 5. start the activity
					       startActivity(iDepos);
			
			
			break;
			
		case 4:
			
			Intent iChangepass=new Intent(getApplicationContext(),AgentChangepasswordActivity.class);
			iChangepass.putExtra("tempAccountNumber", passedAccount);
			//iChangepass.putExtra("temppassepinno", passedpinno);
			//iChangepass.putExtra("tempAgentId",passedAgentId );
					        Bundle extrachangepassw= new Bundle();
					        extrachangepassw.putString("status", "Data Received!");
					 
					        // 4. add bundle to intent
					        iChangepass.putExtras(extrachangepassw);
					 
					        // 5. start the activity
					       startActivity(iChangepass);
			
	
			break;
			
		case 5:
			
			Intent iMessages=new Intent(getApplicationContext(),ViewMessagesActivity.class);
			iMessages.putExtra("tempAccountNumber", passedAccount);
			//iChangepass.putExtra("temppassepinno", passedpinno);
			//iChangepass.putExtra("tempAgentId",passedAgentId );
					        Bundle extramessages= new Bundle();
					        extramessages.putString("status", "Data Received!");
					 
					        // 4. add bundle to intent
					        iMessages.putExtras(extramessages);
					 
					        // 5. start the activity
					       startActivity(iMessages);
			
			
			break;
			
           case 6:
			
			Intent iUmuriro=new Intent(getApplicationContext(),BuyUmuriroActivity.class);
			iUmuriro.putExtra("tempAccountNumber", passedAccount);
			//iChangepass.putExtra("temppassepinno", passedpinno);
			iUmuriro.putExtra("tempAgentId",passedAgentId );
					        Bundle extraumuriro= new Bundle();
					        extraumuriro.putString("status", "Data Received!");
					 
					        // 4. add bundle to intent
					        iUmuriro.putExtras(extraumuriro);
					 
					        // 5. start the activity
					       startActivity(iUmuriro);
			
			
			break;
           case 7:
   			
   			Intent iUmusoro=new Intent(getApplicationContext(),PayUmusoroActivity.class);
   			iUmusoro.putExtra("tempAccountNumber", passedAccount);
   			//iChangepass.putExtra("temppassepinno", passedpinno);
   			iUmusoro.putExtra("tempAgentId",passedAgentId );
   					        Bundle extraumusoro= new Bundle();
   					     extraumusoro.putString("status", "Data Received!");
   					 
   					        // 4. add bundle to intent
   					  iUmusoro.putExtras(extraumusoro);
   					 
   					        // 5. start the activity
   					 startActivity(iUmusoro);
   			
   			
   			break;
			
         case 8:
			
			Intent iLogout=new Intent(getApplicationContext(),LoginActivity.class);
			iLogout.putExtra("tempAccountNumber",passedAccount);
			
			startActivity(iLogout);
			
			break;
		

		}

	}

}
