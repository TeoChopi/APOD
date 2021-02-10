package com.tapp.apod_app.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tapp.apod_app.repository.model.Apod

@Database(entities = [Apod::class], version = 1, exportSchema = false)
abstract class ApodRoomDatabase : RoomDatabase() {

    abstract fun apodDao(): ApodDao

    companion object {

        const val NUM_MAX_ROWS_TABLE = 10
        private var instance: ApodRoomDatabase? = null

        fun getInstance(context: Context): ApodRoomDatabase {

            if (instance == null) {

                synchronized(ApodRoomDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext, ApodRoomDatabase::class.java, "apod_db")
                        .allowMainThreadQueries()
                        .addCallback(roomCallback)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return instance!!
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                // Keep only 10 rows deleting old rows using triggers for each test table
                listOf(Apod.TABLE_NAME)
                    .forEach {table ->
                        // drop trigger if exist (avoid conflict)
                        db.execSQL("DROP TRIGGER IF EXISTS remove_old_rows_$table")
                        // create trigger that delete old rows after insert a new one
                        db.execSQL("""
                            CREATE TRIGGER remove_old_rows_$table AFTER INSERT ON $table
                                BEGIN
                                    DELETE FROM $table
                                    WHERE id NOT IN (SELECT id FROM $table ORDER BY id DESC LIMIT $NUM_MAX_ROWS_TABLE);
                                END;
                        """)
                    }
            }
        }
    }
}