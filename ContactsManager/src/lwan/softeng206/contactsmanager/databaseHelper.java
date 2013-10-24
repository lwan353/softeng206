package lwan.softeng206.contactsmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Contact.db";
	public static final String TABLE_CONTACT = "ContactTable";
	
	private static final String CONTACT_FIRST_NAME = "FirstName";
	private static final String CONTACT_LAST_NAME = "LastName";
	private static final String CONTACT_DATE_OF_BIRTH = "DateOfBirth";
	private static final String CONTACT_MOBILEPH = "MobilePh";
	private static final String CONTACT_HOMEPH = "HomePh";
	private static final String CONTACT_WORKPH = "WorkPh";
	private static final String CONTACT_EMAIL_ADDRESS = "EmailAddress";
	private static final String CONTACT_ADDRESS = "Address";
	private static final String CONTACT_IMAGE = "Image";
	
	private static final String CREATE_CONTACT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACT +  "(" + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT," 
			+ CONTACT_FIRST_NAME + " varchar(30),"
			+ CONTACT_LAST_NAME + " varchar(30),"
			+ CONTACT_DATE_OF_BIRTH + " varchar(30),"
			+ CONTACT_MOBILEPH  + " varchar(30),"
			+ CONTACT_HOMEPH + " varchar(30),"
			+ CONTACT_WORKPH + " varchar(30),"
			+ CONTACT_EMAIL_ADDRESS + " varchar(30),"
			+ CONTACT_ADDRESS + " varchar(50),"
			+ CONTACT_IMAGE + " blob);";
	
	private static final String SQL_DELETE_CONTACT_TABLE = "DROP TABLE IF EXISTS" + TABLE_CONTACT;
	
	public databaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(CREATE_CONTACT_TABLE);
		
		final String FIRST_ENTRY = "INSERT INTO " + TABLE_CONTACT + " (FirstName, LastName, DateOfBirth, MobilePh, HomePh, WorkPh, EmailAddress, Address, Image) VALUES('Ivy', 'Tan', '19/09/1994', '111111111', '', '', '', '', null)";
		db.execSQL(FIRST_ENTRY);
		final String SECOND_ENTRY = "INSERT INTO " + TABLE_CONTACT + " (FirstName, LastName, DateOfBirth, MobilePh, HomePh, WorkPh, EmailAddress, Address, Image) VALUES('Gary', 'Lu', '', '', '', '', '', '', null)";
		db.execSQL(SECOND_ENTRY);
		final String THIRD_ENTRY = "INSERT INTO " + TABLE_CONTACT + " (FirstName, LastName, DateOfBirth, MobilePh, HomePh, WorkPh, EmailAddress, Address, Image) VALUES('Eric', 'Hu', '', '', '', '', '', '', null)";
		db.execSQL(THIRD_ENTRY);
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL(SQL_DELETE_CONTACT_TABLE);
		onCreate(db);
	}
	
	public Cursor getAllData() {
		String buildSQL = "SELECT * FROM " + this.TABLE_CONTACT;
		return this.getReadableDatabase().rawQuery(buildSQL, null);
	}
	
	public void insertData(String FirstName, String LastName, String DateOfBirth, String MobilePh, String HomePh,
			String WorkPh, String EmailAddress, String Address, byte[] Imageb) {
		ContentValues contentValues = new ContentValues();
		
		contentValues.put(databaseHelper.CONTACT_FIRST_NAME, FirstName);
		contentValues.put(databaseHelper.CONTACT_LAST_NAME, LastName);
		contentValues.put(databaseHelper.CONTACT_DATE_OF_BIRTH, DateOfBirth);
		contentValues.put(databaseHelper.CONTACT_MOBILEPH, MobilePh);
		contentValues.put(databaseHelper.CONTACT_HOMEPH, HomePh);
		contentValues.put(databaseHelper.CONTACT_WORKPH, WorkPh);
		contentValues.put(databaseHelper.CONTACT_EMAIL_ADDRESS, EmailAddress);
		contentValues.put(databaseHelper.CONTACT_ADDRESS, Address);
		contentValues.put(databaseHelper.CONTACT_IMAGE, Imageb);
		
		this.getWritableDatabase().insert(databaseHelper.TABLE_CONTACT, null, contentValues);
		
	}

	public Cursor getSelectedData(String data) {
		
		String buildSQL = "SELECT * FROM " + this.TABLE_CONTACT + " WHERE _id = " + data;
		return this.getReadableDatabase().rawQuery(buildSQL, null);
		
	}
	
	public void updateData(long id, String FirstName, String LastName, String DateOfBirth, String MobilePh, String HomePh,
			String WorkPh, String EmailAddress, String Address, byte[] Imageb) {
	    
		ContentValues c= new ContentValues();
			
			c.put(databaseHelper.CONTACT_FIRST_NAME, FirstName);
			c.put(databaseHelper.CONTACT_LAST_NAME, LastName);
			c.put(databaseHelper.CONTACT_DATE_OF_BIRTH, DateOfBirth);
			c.put(databaseHelper.CONTACT_MOBILEPH, MobilePh);
			c.put(databaseHelper.CONTACT_HOMEPH, HomePh);
			c.put(databaseHelper.CONTACT_WORKPH, WorkPh);
			c.put(databaseHelper.CONTACT_EMAIL_ADDRESS, EmailAddress);
			c.put(databaseHelper.CONTACT_ADDRESS, Address);
			c.put(databaseHelper.CONTACT_IMAGE, Imageb);
	      
			this.getWritableDatabase().update(TABLE_CONTACT, c, "_id=" + id, null);
	}
	
	
	public void deleteData(long id) {  

		this.getWritableDatabase().execSQL("DELETE FROM " + this.TABLE_CONTACT + " WHERE _id = "+ String.valueOf(id));  
    }
	

	public Cursor sort(String sort) {
		// TODO Auto-generated method stub
		
		String buildSQL = "SELECT * FROM " + this.TABLE_CONTACT + " ORDER BY " + sort;
		return this.getReadableDatabase().rawQuery(buildSQL, null);
	}


}
