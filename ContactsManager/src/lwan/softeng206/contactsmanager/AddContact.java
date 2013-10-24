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
import android.widget.ImageButton;
import android.widget.ImageView;

public class AddContact extends Activity {

	private static int RESULT_LOAD_IMAGE = 1;
	private ImageView imageView;
	private ImageButton buttonDeletePhoto;
	byte[] imageb;
	private ImageButton buttonAddPhoto;
	private Button buttonBack;
	private Button buttonSave;

	databaseHelper helper = new databaseHelper(AddContact.this);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		
		imageView = (ImageView)findViewById(R.id.activity_add_contact_image);
		buttonAddPhoto = (ImageButton)findViewById(R.id.activity_add_contact_button_1);
		buttonBack = (Button)findViewById(R.id.activity_add_contact_button_2);
		buttonSave = (Button)findViewById(R.id.activity_add_contact_button_3);
		buttonDeletePhoto = (ImageButton)findViewById(R.id.activity_add_contact_button_4);
		
		
		//add photo
		buttonAddPhoto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(
					Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, RESULT_LOAD_IMAGE);

			}
		});
		
		buttonDeletePhoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				imageView.setImageDrawable(null);
				buttonAddPhoto.setVisibility(View.VISIBLE);
				buttonDeletePhoto.setVisibility(View.INVISIBLE);
			}
		});
		
		
		//back to the main screen
		buttonBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddContact.this);
				
				dialogBuilder.setTitle("Exit?");
				dialogBuilder.setMessage("This cannot be undone!");
				
				dialogBuilder.setNegativeButton("No", null);
				dialogBuilder.setPositiveButton("Yes, exit without save", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(AddContact.this, MainActivity.class);
						startActivity(intent);
						
					}
				});
				
				dialogBuilder.create().show();
			}
		});
		
		//save the contact
		buttonSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				EditText addFirstName = (EditText)findViewById(R.id.activity_add_contact_editText1);
				EditText addLastName = (EditText)findViewById(R.id.activity_add_contact_editText2);
				EditText addDateOfBirth = (EditText)findViewById(R.id.activity_add_contact_editText3);
				EditText addMobilePh = (EditText)findViewById(R.id.activity_add_contact_editText4);
				EditText addHomePh = (EditText)findViewById(R.id.activity_add_contact_editText5);
				EditText addWorkPh = (EditText)findViewById(R.id.activity_add_contact_editText6);
				EditText addEmailAddress = (EditText)findViewById(R.id.activity_add_contact_editText7);
				EditText addAddress = (EditText)findViewById(R.id.activity_add_contact_editText8);
				
				String FirstName = addFirstName.getText().toString();
				String LastName = addLastName.getText().toString();
				String DateOfBirth = addDateOfBirth.getText().toString();
				String MobilePh = addMobilePh.getText().toString();
				String HomePh = addHomePh.getText().toString();
				String WorkPh = addWorkPh.getText().toString();
				String EmailAddress = addEmailAddress.getText().toString();
				String Address = addAddress.getText().toString();
				
				helper.insertData(FirstName, LastName, DateOfBirth, MobilePh, HomePh, WorkPh, EmailAddress, Address, imageb);
				Intent intent = new Intent();
				intent.setClass(AddContact.this, MainActivity.class);
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
			
            buttonAddPhoto.setVisibility(View.INVISIBLE);
            buttonDeletePhoto.setVisibility(View.VISIBLE);
            
        }
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}

}
