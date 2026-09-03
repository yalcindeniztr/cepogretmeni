package com.cepogretmeni.tarih.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cepogretmeni.tarih.data.local.entities.LessonPlanEntity
import com.cepogretmeni.tarih.data.local.entities.SavedDocumentEntity
import com.cepogretmeni.tarih.data.local.entities.SelfEvaluationEntity
import com.cepogretmeni.tarih.data.local.entities.TeacherNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    // === Ders Planları ===
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessonPlan(plan: LessonPlanEntity): Long

    @Query("SELECT * FROM lesson_plans ORDER BY createdAtTimestamp DESC")
    fun getAllLessonPlans(): Flow<List<LessonPlanEntity>>

    // === Öz Değerlendirme Formları (Öğrenci Gelişim Takibi) ===
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSelfEvaluation(evaluation: SelfEvaluationEntity): Long

    @Query("SELECT * FROM self_evaluations ORDER BY createdAtTimestamp DESC")
    fun getAllSelfEvaluations(): Flow<List<SelfEvaluationEntity>>

    // === Uygulama İçi Kaydedilen Belgeler (Planlar, Sınavlar, Zümreler) ===
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedDocument(doc: SavedDocumentEntity): Long

    @Query("SELECT * FROM saved_documents ORDER BY createdAtTimestamp DESC")
    fun getAllSavedDocuments(): Flow<List<SavedDocumentEntity>>

    @Delete
    suspend fun deleteSavedDocument(doc: SavedDocumentEntity)

    // === Öğretmen Notları ===
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeacherNote(note: TeacherNoteEntity): Long

    @Query("SELECT * FROM teacher_notes ORDER BY updatedAtTimestamp DESC")
    fun getAllTeacherNotes(): Flow<List<TeacherNoteEntity>>
}
