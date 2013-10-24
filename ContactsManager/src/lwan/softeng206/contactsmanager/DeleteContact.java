package lwan.softeng206.contactsmanager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class DeleteContact extends Activity {

	private ListView listView;
	private Button buttonSelectAll;
	private Button buttonInvertSelect;
	private Button buttonBack;
	private Button buttonDelete;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_contact);
		
		listView = (ListView)findViewById(R.id.activity_delete_contact_listview);
		buttonSelectAll = (Button)findViewById(R.id.activity_delete_contact_button_1);
		buttonInvertSelect = (Button)findViewById(R.id.activity_delete_contact_button_2);
		buttonBack = (Button)findViewById(R.id.activity_delete_contact_button_3);
		buttonDelete = (Button)findViewById(R.id.activity_delete_contact_button_4);
		
//		isCheckedArray[0] = false;
//		isCheckedArray[1] = false;
//		isCheckedArray[2] = false;
//		isCheckedArray[3] = false;
//		isCheckedArray[4] = false;
//		isCheckedArray[5] = false;
//		isCheckedArray[6] = false;
//		isCheckedArray[7] = false;
//		isCheckedArray[8] = false;
		
		setupListView();
		
		final boolean[] isCheckedArray = new boolean[listView.getCount()];
		
		for(int i=0; i<listView.getCount(); i++){
			isCheckedArray[i] = false;
			listView.setItemChecked(i, isCheckedArray[i]);
		}
		
		
		buttonBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
				intent.setClass(DeleteContact.this, MainActivity.class);
				startActivity(intent);
				
			}
		});
		
		buttonDelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DeleteContact.this);
				
				int checkedNum = listView.getCheckedItemCount();
				if (checkedNum == 0){
							
					dialogBuilder.setTitle("ERROR!");
					dialogBuilder.setMessage("No Contact Selected!");
					
					dialogBuilder.setNegativeButton("Ok", null);
					
					dialogBuilder.create().show();
					
				}else{
					dialogBuilder.setTitle("Delete?");
					dialogBuilder.setMessage("This cannot be undone!");
					
					dialogBuilder.setNegativeButton("No", null);
					dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setClass(DeleteContact.this, MainActivity.class);
							startActivity(intent);
							
						}
					});
					
					dialogBuilder.create().show();
				}
			}
		});
		
		buttonSelectAll.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				for(int i=0; i<isCheckedArray.length; i++){
					listView.setItemChecked(i, true);
				}
				
			}
		});
		
		buttonInvertSelect.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();
				for(int i=0; i<sparseBooleanArray.size(); i++){
					if(sparseBooleanArray.get(i) == true){
						listView.setItemChecked(i, false);
					}else{
						listView.setItemChecked(i, true);
					}
				}
				
			}
		});
	}
		
	private void setupListView(){
		
//		List<String> displayList = new ArrayList<String>();
//		
//		displayList.add("Cindy Cai");
//		displayList.add("Emily Wang");
//		displayList.add("Eric Hu");
//		displayList.add("Gary Lu");
//		displayList.add("Ivy Tan");
//		displayList.add("Ivy Tan");
//		displayList.add("Ivy Tan");
//		displayList.add("Ivy Tan");
//		displayList.add("Joy Wang");	
//		
//		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, displayList);
//		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//		listView.setAdapter(adapter);
		
		databaseHelper helper = new databaseHelper(this);
		CustomCursorAdapter adapter = new CustomCursorAdapter(this, helper.getAllData());
		listView.setAdapter(adapter);
//		listView.checkbox.setOnClickListener( new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                CheckBox c = (CheckBox) v;
//
//                int row_id = (Integer) v.getTag();
//
//                checkboxes.put(row_id, c.isChecked());
//
//
//            }
//    });
//		for(int i=0; i<isCheckedArray.length; i++){
//			listView.setItemChecked(i, isCheckedArray[i]);
//		}
		
		
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delete_contact, menu);
		return true;
	}

}