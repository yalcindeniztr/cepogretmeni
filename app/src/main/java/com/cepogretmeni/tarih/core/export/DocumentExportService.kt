package com.cepogretmeni.tarih.core.export

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.cepogretmeni.tarih.domain.model.AnnualPlanWeek
import com.cepogretmeni.tarih.domain.model.DailyLessonPlan
import com.cepogretmeni.tarih.domain.model.MebExamPaper
import com.cepogretmeni.tarih.domain.model.ZumreMeetingRecord
import java.io.File
import java.io.FileOutputStream

/**
 * Öğretmen İdari ve Ders Materyalleri İçin Kapsamlı Belge ve PDF Çıktı Motoru
 */
class DocumentExportService(private val context: Context) {

    /**
     * Resmî MEB Formatında Zümre Öğretmenler Kurulu Tutanağını PDF Olarak Üretir
     */
    fun exportZumrePdf(record: ZumreMeetingRecord): File {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = document.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val titlePaint = Paint().apply {
            color = Color.rgb(20, 35, 60)
            textSize = 12f
            isFakeBoldText = true
            isAntiAlias = true
        }

        val headerPaint = Paint().apply {
            color = Color.rgb(180, 83, 9)
            textSize = 10f
            isFakeBoldText = true
            isAntiAlias = true
        }

        val bodyPaint = Paint().apply {
            color = Color.rgb(40, 40, 40)
            textSize = 9f
            isAntiAlias = true
        }

        val borderPaint = Paint().apply {
            color = Color.LTGRAY
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }

        // Başlık
        canvas.drawRect(30f, 30f, 565f, 85f, borderPaint)
        canvas.drawText("T.C. MİLLÎ EĞİTİM BAKANLIĞI", 215f, 50f, titlePaint)
        canvas.drawText("${record.schoolYear} ${record.term.uppercase()}", 100f, 70f, headerPaint)

        var currentY = 110f
        canvas.drawText("Toplantı Tarihi: ${record.meetingDate}", 40f, currentY, bodyPaint)
        currentY += 20f

        canvas.drawText("GÜNDEM MADDELERİ:", 40f, currentY, headerPaint)
        currentY += 15f
        record.agendaItems.forEach { item ->
            canvas.drawText(item, 45f, currentY, bodyPaint)
            currentY += 14f
        }

        currentY += 10f
        canvas.drawText("ALINAN KARARLAR (TÜRKİYE YÜZYILI MAARİF MODELİ UYGULAMALARI):", 40f, currentY, headerPaint)
        currentY += 15f
        record.decisionsTaken.forEach { decision ->
            canvas.drawText("• ${decision.take(100)}", 45f, currentY, bodyPaint)
            currentY += 14f
        }

        currentY += 10f
        canvas.drawText("ORTAÖĞRETİM KURUMLARI SINIF GEÇME VE ÖLÇME-DEĞERLENDİRME KRİTERLERİ:", 40f, currentY, headerPaint)
        currentY += 15f
        canvas.drawText(record.measurementEvaluationCriteria.take(110), 45f, currentY, bodyPaint)
        currentY += 14f
        canvas.drawText(record.passFailRegulationDecisions.take(110), 45f, currentY, bodyPaint)

        // İmzalar
        canvas.drawText("Zümre Başkanı / Tarih Öğretmeni", 60f, 790f, bodyPaint)
        canvas.drawText("Uygundur - Okul Müdürü", 400f, 790f, bodyPaint)

        document.finishPage(page)
        val file = File(context.cacheDir, "Zumre_Tutanagi_${System.currentTimeMillis()}.pdf")
        document.writeTo(FileOutputStream(file))
        document.close()
        return file
    }

    /**
     * Resmî MEB Yazılı Sınav Kağıdı ve Cevap Anahtarı (Rubrik) PDF Çıktısı
     */
    fun exportExamPaperPdf(exam: MebExamPaper): File {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = document.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val titlePaint = Paint().apply {
            color = Color.rgb(20, 35, 60)
            textSize = 10f
            isFakeBoldText = true
            isAntiAlias = true
        }

        val questionPaint = Paint().apply {
            color = Color.BLACK
            textSize = 9.5f
            isAntiAlias = true
        }

        val rubricPaint = Paint().apply {
            color = Color.rgb(15, 118, 110)
            textSize = 8.5f
            isAntiAlias = true
        }

        // Antet & Öğrenci Bilgi Alanı
        canvas.drawText(exam.examTitle, 50f, 45f, titlePaint)
        canvas.drawText("Adı Soyadı: ...................................   Sınıf/Şube: ..........   No: ..........   Puan: ......./100", 50f, 65f, questionPaint)
        canvas.drawLine(30f, 75f, 565f, 75f, Paint().apply { color = Color.DKGRAY; strokeWidth = 1f })

        var y = 95f
        exam.questions.forEach { q ->
            canvas.drawText("Soru ${q.questionNumber} (${q.pointValue} Puan) [Kazanım: ${q.outcomeCode}]", 40f, y, titlePaint)
            y += 14f
            q.sourceOrPremise?.let { premise ->
                canvas.drawText("Kaynak/Öncül: ${premise.take(110)}", 45f, y, questionPaint)
                y += 14f
            }
            canvas.drawText(q.questionText.take(110), 45f, y, questionPaint)
            y += 35f // Öğrencinin cevap yazması için boşluk
        }

        // Alt tarafa öğretmen puanlama anahtarı (Rubrik) özeti
        canvas.drawLine(30f, 660f, 565f, 660f, Paint().apply { color = Color.GRAY; strokeWidth = 1f })
        canvas.drawText("MEB ÖLÇME VE DEĞERLENDİRME DERECELİ PUANLAMA ANAHTARI (RUBRİK ÖZETİ):", 40f, 675f, titlePaint)
        y = 690f
        exam.rubricScoringKey.forEach { key ->
            canvas.drawText("Soru ${key.questionNumber} Kriteri: ${key.expectedAnswer.take(85)} (${key.maxScore} Puan)", 45f, y, rubricPaint)
            y += 14f
        }

        document.finishPage(page)
        val file = File(context.cacheDir, "Yazili_Sinav_ve_Rubrik_${exam.gradeLevel}_Sinif_${System.currentTimeMillis()}.pdf")
        document.writeTo(FileOutputStream(file))
        document.close()
        return file
    }

    /**
     * Maarif Modeli Yıllık Planını PDF Olarak Dışa Aktarır
     */
    fun exportAnnualPlanPdf(weeks: List<AnnualPlanWeek>, gradeLevel: Int): File {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(842, 595, 1).create() // Yatay A4 (Landscape)
        val page = document.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val titlePaint = Paint().apply {
            color = Color.rgb(24, 43, 73)
            textSize = 12f
            isFakeBoldText = true
            isAntiAlias = true
        }

        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = 8.5f
            isAntiAlias = true
        }

        canvas.drawText("T.C. MİLLÎ EĞİTİM BAKANLIĞI - TÜRKİYE YÜZYILI MAARİF MODELİ $gradeLevel. SINIF TARİH DERSİ YILLIK PLANI", 120f, 40f, titlePaint)
        
        var y = 75f
        canvas.drawText("Hafta / Ay | Tema & Öğrenme Çıktısı | Süreç Bileşenleri / Beceriler | Kök Değerler | Ölçme-Değerlendirme", 30f, y, titlePaint)
        canvas.drawLine(30f, y + 5, 812f, y + 5, Paint().apply { color = Color.BLACK })
        y += 20f

        weeks.forEach { week ->
            val rowText = "${week.weekNumber}. Hafta (${week.monthName}) | ${week.learningOutcomeCode}: ${week.learningOutcomeDescription.take(45)} | ${week.skillComponents.take(35)} | ${week.coreValues.joinToString { it.titleTr }} | ${week.measurementAndEvaluation.take(30)}"
            canvas.drawText(rowText, 30f, y, textPaint)
            y += 18f
        }

        document.finishPage(page)
        val file = File(context.cacheDir, "Yillik_Plan_${gradeLevel}_Sinif_${System.currentTimeMillis()}.pdf")
        document.writeTo(FileOutputStream(file))
        document.close()
        return file
    }
}
