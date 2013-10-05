package com.expense.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String ACCOUNTS_TABLE= "AccountsTable";
	public static final String TRANSACTION_TABLE= "TransactionTable";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_AMOUNT = "amount";
	public static final String COLUMN_PRESENT = "present";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_COLOR = "color";

	public static final String COLUMN_GIVEN_TAKEN = "addorsub";  
	public static final String COLUMN_REASON = "reason";

	private static final String DATABASE_NAME = "mydb";
	private static final int DATABASE_VERSION =2;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ ACCOUNTS_TABLE + "(" + COLUMN_ID
			+ " integer primary key autoincrement , " + COLUMN_NAME
			+ " text not null ," + COLUMN_AMOUNT + " integer ,"+COLUMN_PRESENT+" integer, "+COLUMN_TIME+" text ,"+COLUMN_COLOR+" text);";
	private static final String DATABASE_CREATE_TRANSACTION = "create table "
			+ TRANSACTION_TABLE + "(" + COLUMN_ID
			+ " integer primary key autoincrement , " + COLUMN_NAME
			+ " text not null ," + COLUMN_AMOUNT + " integer ,"+COLUMN_PRESENT+" integer, "+COLUMN_TIME+" text ,"+COLUMN_GIVEN_TAKEN
			+ " text,"+COLUMN_REASON+" text );";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		database.execSQL(DATABASE_CREATE_TRANSACTION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + TRANSACTION_TABLE);
		onCreate(db);
	}

} 