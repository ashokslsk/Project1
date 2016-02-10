package com.ashokslsk.popularmovies.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.ashokslsk.popularmovies.provider.MoviesSQLiteHelper;

/**
 * Created by ashok.kumar on 10/02/16.
 */
public class TestMoviesSQLiteHelper extends AndroidTestCase {

    private static final String TAG = "TestMoviesSQLiteHelper";
    public static final String DATABASE_NAME = "FavouriteMovies";
    static final String TABLE_NAME = "favourites";
    static final int DATABASE_VERSION = 1;

//Database creation testing
    public void testCreateDB() throws Throwable{
        SQLiteDatabase db = new MoviesSQLiteHelper(this.mContext).getWritableDatabase();

       // checking whether db is open
        assertEquals(true, db.isOpen());
        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        c = db.rawQuery("PRAGMA table_info(" +MoviesSQLiteHelper.TABLE_NAME + ")",
                null);
        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

    }

}
