package com.cepogretmeni.tarih.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cepogretmeni.tarih.data.local.entities.LessonPlanEntity
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

    @Query("SELECT * FROM lesson_plans WHERE id = :id")
    suspend fun getLessonPlanById(id: Long): LessonPlanEntity?

    // === Öz Değerlendirme Formları (Öğrenci Gelişim Takibi) ===
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSelfEvaluation(evaluation: SelfEvaluationEntity): Long

    @Query("SELECT * FROM self_evaluations ORDER BY createdAtTimestamp DESC")
    fun getAllSelfEvaluations(): Flow<List<SelfEvaluationEntity>>

    @Query("SELECT * FROM self_evaluations WHERE studentNumber = :studentNumber")
    fun getEvaluationsByStudentNumber(studentNumber: String): Flow<List<SelfEvaluationEntity>>

    // === Öğretmen Notları ===
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeacherNote(note: TeacherNoteEntity): Long

    @Query("SELECT * FROM teacher_notes ORDER BY updatedAtTimestamp DESC")
    fun getAllTeacherNotes(): Flow<List<TeacherNoteEntity>>
}
