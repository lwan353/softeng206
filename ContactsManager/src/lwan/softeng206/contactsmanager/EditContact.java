package lwan.softeng206.contactsmanager;

import java.io.ByteArrayOutputStream;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class EditContact extends Activity {

	private static final int RESULT_LOAD_IMAGE = 0;
	private Button buttonDelete;
	private Button buttonBack;
	private Button buttonSave;
	private Button buttonEditPhoto;
	private ImageView imageView;
	private EditText editFirstName;
	private EditText editLastName;
	private EditText editDateOfBirth;
	private EditText editMobilePh;
	private EditText editHomePh;
	private EditText editWorkPh;
	private EditText editEmailAddress;
	private EditText editAddress;
	private String newFirstName;
	private String newLastName;
	private String newDateOfBirth;
	private String newMobilePh;
	private String newHomePh;
	private String newWorkPh;
	private String newEmailAddress;
	private String newAddress;
	private byte[] imageb;
	
	long id;
	
	databaseHelper helper = new databaseHelper(EditContact.this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_contact);
		
		imageView = (ImageView)findViewById(R.id.activity_edit_contact_editView1);
		buttonEditPhoto = (Button)findViewById(R.id.activity_edit_contact_button_1);
		buttonDelete = (Button)findViewById(R.id.activity_edit_contact_button_2);
		buttonBack = (Button)findViewById(R.id.activity_edit_contact_button_3);
		buttonSave = (Button)findViewById(R.id.activity_edit_contact_button_4);
		
		editFirstName = (EditText)findViewById(R.id.activity_edit_contact_editText1);
		editLastName = (EditText)findViewById(R.id.activity_edit_contact_editText2);
		editDateOfBirth = (EditText)findViewById(R.id.activity_edit_contact_editText3);
		editMobilePh = (EditText)findViewById(R.id.activity_edit_contact_editText4);
		editHomePh = (EditText)findViewById(R.id.activity_edit_contact_editText5);
		editWorkPh = (EditText)findViewById(R.id.activity_edit_contact_editText6);
		editEmailAddress = (EditText)findViewById(R.id.activity_edit_contact_editText7);
		editAddress = (EditText)findViewById(R.id.activity_edit_contact_editText8);
		
		
		Intent intent1 = getIntent();
		id = intent1.getLongExtra("contactId", -1);	
		Cursor cursor = helper.getSelectedData(String.valueOf(id));
		
		if(cursor.moveToFirst()) {  
			
			String FirstName = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1)));
			String LastName = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2)));
			String DateOfBirth = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))); 
			String MobilePh = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4)));
			String HomePh = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(5)));
			String WorkPh = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(6)));
			String EmailAddress = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(7)));
			String Address = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(8)));

			editFirstName.setText(FirstName);
			editLastName.setText(LastName);
			editDateOfBirth.setText(DateOfBirth);
			editMobilePh.setText(MobilePh);
			editHomePh.setText(HomePh);
			editWorkPh.setText(WorkPh);
			editEmailAddress.setText(EmailAddress);
			editAddress.setText(Address);
		
		}
		
		newFirstName = editFirstName.getText().toString();
		newLastName = editLastName.getText().toString();
		newDateOfBirth = editDateOfBirth.getText().toString();
		newMobilePh = editMobilePh.getText().toString();
		newHomePh = editHomePh.getText().toString();
		newWorkPh = editWorkPh.getText().toString();
		newEmailAddress = editEmailAddress.getText().toString();
		newAddress = editAddress.getText().toString();
		
		buttonEditPhoto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(
					Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, RESULT_LOAD_IMAGE);

			}
		});
			
			
		buttonDelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditContact.this);
				
				dialogBuilder.setTitle("Delete this contact?");
				dialogBuilder.setMessage("This cannot be undone!");
				
				dialogBuilder.setNegativeButton("No", null);
				dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						
						helper.deleteData(id);
						Intent intent = new Intent();
						intent.setClass(EditContact.this, MainActivity.class);
						startActivity(intent);
						
					}
				});
				
				dialogBuilder.setCancelable(true);
				dialogBuilder.create().show();
			}
		});
		
		buttonSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				newFirstName = editFirstName.getText().toString();
				newLastName = editLastName.getText().toString();
				newDateOfBirth = editDateOfBirth.getText().toString();
				newMobilePh = editMobilePh.getText().toString();
				newHomePh = editHomePh.getText().toString();
				newWorkPh = editWorkPh.getText().toString();
				newEmailAddress = editEmailAddress.getText().toString();
				newAddress = editAddress.getText().toString();
				
				helper.updateData(id, newFirstName, newLastName, newDateOfBirth, newMobilePh, newHomePh, newWorkPh, newEmailAddress, newAddress, imageb);
				Intent intent = new Intent();
				intent.putExtra("contactId", id);
				intent.setClass(EditContact.this, ViewContact.class);
				startActivity(intent);
				
			}
		});
		
		buttonBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent();
				intent.putExtra("contactId", id);
				intent.setClass(EditContact.this, MainActivity.class);
				startActivity(intent);
				
			}
		});
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
  
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            
        	Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            
//            ImageView imageView = (ImageView) findViewById(R.id.activity_add_contact_image);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			BitmapFactory.decodeFile(picturePath).compress(Bitmap.CompressFormat.PNG, 100, stream);;
			imageb =  stream.toByteArray();
            
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_contact, menu);
		return true;
	}

}
