package com.cepogretmeni.tarih.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cepogretmeni.tarih.data.local.dao.HistoryDao
import com.cepogretmeni.tarih.data.local.entities.LessonPlanEntity
import com.cepogretmeni.tarih.data.local.entities.SelfEvaluationEntity
import com.cepogretmeni.tarih.data.local.entities.TeacherNoteEntity
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

/**
 * Uçtan uca SQLCipher ile şifrelenmiş Room Veritabanı.
 * Tüm veriler yerel diskte AES-256 ile şifreli tutulur, dışarı aktarılmaz.
 */
@Database(
    entities = [
        LessonPlanEntity::class,
        SelfEvaluationEntity::class,
        TeacherNoteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "maarif_tarih_secure.db"

        fun getDatabase(context: Context, passphraseBytes: ByteArray): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // SQLCipher native kütüphanesini yükle
                SQLiteDatabase.loadLibs(context)
                val factory = SupportFactory(passphraseBytes)

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .openHelperFactory(factory)
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
