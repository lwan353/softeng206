package lwan.softeng206.contactsmanager;

import java.util.Calendar;

public class Date {

	public static void main(String[] args){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println(year + " " + (month+1) + " " + day);
	}
	
}
