	package cs198.cis2;
	
	import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
	
	public class FileStatsDataSource {
	
	  // Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  
	  public static final String TABLE_FILESTATS = "filestats";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_USERID = "userid";
	  public static final String COLUMN_FILENAME = "filename";
	  public static final String COLUMN_TYPE = "type";
	  public static final String COLUMN_CONF = "conf";
	  private static final String DATABASE_NAME = "filestats.db";
	  private static final int DATABASE_VERSION = 1;
	
	  private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_USERID, MySQLiteHelper.COLUMN_FILENAME, MySQLiteHelper.COLUMN_TYPE, MySQLiteHelper.COLUMN_CONF };
	
	  public FileStatsDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }
	
	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();

	  }
	
	  public void close() {
	    dbHelper.close();
	  }
	  
	  public void drop(){
		 dbHelper.drop(database);
	  }
	
	  public FileStats createFileStat(String filename) {
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_USERID, "none");
	    values.put(MySQLiteHelper.COLUMN_FILENAME, filename);
	    values.put(MySQLiteHelper.COLUMN_TYPE, "none");
	    values.put(MySQLiteHelper.COLUMN_CONF, "none");
	    
	    long insertId = database.insert(MySQLiteHelper.TABLE_FILESTATS, null, values);
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_FILESTATS, allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
	    cursor.moveToFirst();
	    FileStats newFileStat = cursorToFileStats(cursor);
	    cursor.close();
	    return newFileStat;
	  }
	
	  public int updateFileStat(FileStats x)
	  {
	   ContentValues cv=new ContentValues();
	   cv.put(COLUMN_USERID, x.getUserId());
	   cv.put(COLUMN_FILENAME, x.getFileName());
	   cv.put(COLUMN_TYPE, x.getType());
	   cv.put(COLUMN_CONF, x.getConf());
	   return database.update(TABLE_FILESTATS, cv, COLUMN_ID+"=?", new String []{String.valueOf(x.getId())});   
	  }
	  
	  public void deleteFileStat(FileStats f) {
	    long id = f.getId();
	    System.out.println("Comment deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_FILESTATS, MySQLiteHelper.COLUMN_ID   + " = " + id, null);
	  }
	  
	  public List<FileStats> getAllEmptyFileStats() {
		  	List<FileStats> emptyfilestats = new ArrayList<FileStats>();
		  	 Cursor cursor = database.query(MySQLiteHelper.TABLE_FILESTATS, allColumns, 
                     MySQLiteHelper.COLUMN_CONF +"=?" +" AND " + MySQLiteHelper.COLUMN_TYPE +"=?", 
                     new String[] {"none", "none" }, 
                     null, null, null);		  	
		  	Log.e("norvin", "there are "+cursor.getCount()+"rows");
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
			  FileStats f = cursorToFileStats(cursor);
			  emptyfilestats.add(f);
			  cursor.moveToNext();
			}
			// Make sure to close the cursor
			cursor.close();
			return emptyfilestats;
	  }
	  
	  public List<FileStats> getAllFileStats() {
		  List<FileStats> filestats = new ArrayList<FileStats>();
	
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_FILESTATS, allColumns, null, null, null, null, null);
	    //Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_FILESTATS, null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      FileStats f = cursorToFileStats(cursor);
	      filestats.add(f);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return filestats;
	  }
	
	  private FileStats cursorToFileStats(Cursor cursor) {
	    FileStats filestat = new FileStats();
	    filestat.setId(cursor.getLong(0));
	    filestat.setUserId(cursor.getString(1));
	    filestat.setFileName(cursor.getString(2));
	    filestat.setType(cursor.getString(3));
	    filestat.setConf(cursor.getString(4));
	    return filestat;
	  }
	} 
