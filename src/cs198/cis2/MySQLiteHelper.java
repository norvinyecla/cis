package cs198.cis2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_FILESTATS = "filestats";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_USERID = "userid";
  public static final String COLUMN_FILENAME = "filename";
  public static final String COLUMN_TYPE = "type";
  public static final String COLUMN_CONF = "conf";
  private static final String DATABASE_NAME = "filestats.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_FILESTATS + "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_USERID + " text not null, " + COLUMN_FILENAME + " text unique not null, " + 
		  COLUMN_TYPE + " text not null, "+ COLUMN_CONF + " text not null);";
  private static final String DATABASE_DROP = "DROP TABLE IF EXISTS "+TABLE_FILESTATS;
  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
  

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  public void drop(SQLiteDatabase database){
	  database.execSQL(DATABASE_DROP);
  }
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILESTATS);
    onCreate(db);
  }

  
} 
