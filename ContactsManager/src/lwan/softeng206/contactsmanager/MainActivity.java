package lwan.softeng206.contactsmanager;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ListView listView;
	private Button buttonDelete;
	private databaseHelper helper;
	private CustomCursorAdapter adapter;
	private final static int menu_sort = 1;
	private final static int submenu1_sort = 2;
	private final static int submenu2_sort = 3;
	private final static int submenu3_sort = 4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listView = (ListView)findViewById(R.id.main_listview);
		buttonDelete = (Button)findViewById(R.id.main_button_2);
		
		setupListView();
		
		databaseHelper helper = new databaseHelper(this);
		CustomCursorAdapter adapter = new CustomCursorAdapter(this, helper.getAllData());
		listView.setAdapter(adapter);
		
		buttonDelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DeleteContact.class);
				startActivity(intent);
				
			}
		});
	}
	

	private void setupListView(){
		
		databaseHelper helper = new databaseHelper(this);
		CustomCursorAdapter adapter = new CustomCursorAdapter(this, helper.getAllData());
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition, long id){
				
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ViewContact.class);
				intent.putExtra("contactId", id);
				startActivity(intent);
				
			}
		});
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		SubMenu subMenu = (SubMenu)menu.addSubMenu(0, menu_sort, 1, "sort ");
		
		subMenu.setHeaderTitle("sort contact");
		subMenu.add(0, submenu1_sort, 2, "by first name").setCheckable(true);
		subMenu.add(0, submenu2_sort, 3, "by last name").setCheckable(true);
		subMenu.add(0, submenu3_sort, 4, "by phone number").setCheckable(true);
		
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		menu.findItem(submenu1_sort).setChecked(true);
		menu.findItem(submenu2_sort).setChecked(false);
		menu.findItem(submenu3_sort).setChecked(false);
		return super.onPrepareOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if(item.getItemId() == submenu1_sort){
			helper = new databaseHelper(MainActivity.this);
			adapter = new CustomCursorAdapter(MainActivity.this, helper.sort("FirstName"));
			listView.setAdapter(adapter);
		}
		
		if(item.getItemId() == submenu2_sort){
			helper = new databaseHelper(MainActivity.this);
			adapter = new CustomCursorAdapter(MainActivity.this, helper.sort("LastName"));
			listView.setAdapter(adapter);
		}
		
		if(item.getItemId() == submenu3_sort){
			helper = new databaseHelper(MainActivity.this);
			adapter = new CustomCursorAdapter(MainActivity.this, helper.sort("MobilePh"));
			listView.setAdapter(adapter);
		}
		
		if(item.getItemId() == R.id.action_add){
			
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, AddContact.class);
			startActivity(intent);
		}
		
		return super.onOptionsItemSelected(item);
		
	}
	
}