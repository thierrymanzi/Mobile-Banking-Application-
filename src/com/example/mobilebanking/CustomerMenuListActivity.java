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

public class CustomerMenuListActivity extends Activity implements OnItemClickListener {

	// grid
	ListView gridView;
	ArrayList<Item> gridArray = new ArrayList<Item>();
	GridViewAdapter customGridAdapter;
	 Intent intes;
     String passedAccount,passedAgentId,passedpinno;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customermenu_list_activity);
		intes=getIntent();
		 passedAccount = intes.getStringExtra("tempAccountNumber");
		 passedpinno=intes.getStringExtra("temppassepinno");
		//passedAgentId=intes.getStringExtra("AgentIDPassed");
		// action bar styling
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f47701")));

		// grid info

		
		
		gridArray.add(new Item(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.balance1), "Transfer/Koherereza"));

		gridArray.add(new Item(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.balance1), "Check Balance/Kureba Konti Yawe"));
		
		
		gridArray.add(new Item(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.balance1), "Change Password/Guhindura Umubare W'Ibanga"));
		
		

		gridArray.add(new Item(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.balance1), "List Messages/Ilisiti Y'Ubutumwa"));
		
		
	
		gridView = (ListView) findViewById(R.id.gridView3);
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
			Intent iTranfercustomer = new Intent(getApplicationContext(),TransferActivity.class);
			iTranfercustomer.putExtra("tempAccountNumber", passedAccount);
			//iTranfercustomer.putExtra("tempAgentId",passedAgentId );
				        Bundle extrastrans = new Bundle();
				        extrastrans.putString("status", "Data Received!");
				 
				        // 4. add bundle to intent
				        iTranfercustomer.putExtras(extrastrans);
				 
				        // 5. start the activity
				     startActivity(iTranfercustomer);
			break;
		case 1:
			
		
			Intent iCheckbal=new Intent(getApplicationContext(),BalanceActivity.class);
			iCheckbal.putExtra("tempAccountNumber",passedAccount);
			iCheckbal.putExtra("temppassepinno", passedpinno);
			 Bundle checkbalance= new Bundle();
			 checkbalance.putString("status", "Data Received!");
		 
		        // 4. add bundle to intent
		        iCheckbal.putExtras(checkbalance);
		 
		        // 5. start the activity
		       startActivity(iCheckbal);
			break;
			
			
		case 2:
			
			Intent iChangepass=new Intent(getApplicationContext(),ChangepasswordActivity.class);
			iChangepass.putExtra("tempAccountNumber",passedAccount);
			//iChangepass.putExtra("temppassepinno", passedpinno);
			//iChangepass.putExtra("tempAgentId",passedAgentId );
					        Bundle extrachangepassw= new Bundle();
					        extrachangepassw.putString("status", "Data Received!");
					 
					        // 4. add bundle to intent
					        iChangepass.putExtras(extrachangepassw);
					 
					        // 5. start the activity
					       startActivity(iChangepass);
			break;
		
case 3:
			
			Intent iMessages=new Intent(getApplicationContext(),ViewMessagesActivity.class);
			startActivity(iMessages);
			
			break;
			

		

		}

	}

}
