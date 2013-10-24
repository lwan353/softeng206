package lwan.softeng206.contactsmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ViewContact extends Activity {
	
	private ListView listView1;
	private ListView listView2;
	private Button buttonBack;
	private Button buttonEdit;
	private byte[] imageb;
    
	private String[] listTitle1 = { "First Name", "Last Name"};
	private String[] listTitle2 = { "Date of Birth", "Moblie Phone", "Home Phone", "Work Phone","Email Address", "Home Adress"};
	
	private ArrayList<Map<String,Object>> Data1= new ArrayList<Map<String,Object>>();;
    private ArrayList<Map<String,Object>> Data2= new ArrayList<Map<String,Object>>();;
    
    long id;
    
    databaseHelper helper = new databaseHelper(ViewContact.this);
    
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_contact);
		
		Intent intent1 = getIntent();
		id = intent1.getLongExtra("contactId", -1);	
		Cursor cursor = helper.getSelectedData(String.valueOf(id));
		
		listView1 = (ListView)findViewById(R.id.activity_view_contact_listview1);
		listView2 = (ListView)findViewById(R.id.activity_view_contact_listview2);
		buttonBack = (Button)findViewById(R.id.activity_view_contact_button_3);
		buttonEdit = (Button)findViewById(R.id.activity_view_contact_button_4);
		
		if(cursor.moveToFirst()) {  
			
			String FirstName = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1)));
			String LastName = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2)));
			String DateOfBirth = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))); 
			String MobilePh = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4)));
			String HomePh = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(5)));
			String WorkPh = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(6)));
			String EmailAddress = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(7)));
			String Address = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(8)));
			imageb = cursor.getBlob(cursor.getColumnIndex(cursor.getColumnName(9)));
			
			String[] listStr1 = { FirstName, LastName };
			String[] listStr2 = { DateOfBirth,  MobilePh,  HomePh,  WorkPh, EmailAddress, Address };
			
			int lengh1 = listTitle1.length;
			for(int i =0; i < lengh1; i++) {
				Map<String,Object> item = new HashMap<String,Object>();
				item.put("view_list_title1", listTitle1[i]);
				item.put("view_list_text1", listStr1[i]);
				Data1.add(item); 
			}
			
			int lengh2 = listTitle2.length;
			for(int i =0; i < lengh2; i++) {
				Map<String,Object> item = new HashMap<String,Object>();
				item.put("view_list_title1", listTitle2[i]);
				item.put("view_list_text1", listStr2[i]);
				Data2.add(item); 
			}
			
			SimpleAdapter adapter1 = new SimpleAdapter(ViewContact.this,Data1,android.R.layout.simple_list_item_2,
					new String[]{"view_list_title1","view_list_text1"},
					new int[]{android.R.id.text1, android.R.id.text2});
			listView1.setAdapter(adapter1);
			
			SimpleAdapter adapter2 = new SimpleAdapter(ViewContact.this,Data2,android.R.layout.simple_list_item_2,
					new String[]{"view_list_title1","view_list_text1"},
					new int[]{android.R.id.text1, android.R.id.text2});
			listView2.setAdapter(adapter2);
			
		}
		
		buttonBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent();
				intent.setClass(ViewContact.this, MainActivity.class);
				startActivity(intent);
			}
		});
		
		buttonEdit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent();
				intent.setClass(ViewContact.this, EditContact.class);
				intent.putExtra("contactId", id);
				startActivity(intent);
				
			}
		});
		
	}
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_contact, menu);
		
		return true;
	}
	

	public boolean onOptionsItemSelected(MenuItem item) {
		
		if(item.getItemId() == R.id.action_edit){
			
			Intent intent = new Intent();
			intent.setClass(ViewContact.this, EditContact.class);
			startActivity(intent);
		}
		
		return super.onOptionsItemSelected(item);
		
	}
	
}

