package lwan.softeng206.contactsmanager;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CustomCursorAdapter extends CursorAdapter {
	
	public CustomCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}
	
	private Context context;
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View listItemView = inflater.inflate(R.layout.custom_list_item_layout, null);
		
		return listItemView;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		
		TextView FirstName = (TextView)view.findViewById(R.id.list_item_text_first_name);
 		TextView LastName = (TextView)view.findViewById(R.id.list_item_text_last_name);
 		TextView MobilePh = (TextView)view.findViewById(R.id.list_item_text_moblie_ph);
 		
 		
 		FirstName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
 		LastName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
 		MobilePh.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4))));
 		
	}
}
