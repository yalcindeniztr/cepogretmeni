package com.cepogretmeni.tarih.core.export

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import com.cepogretmeni.tarih.domain.model.*
import java.io.File
import java.io.FileOutputStream

/**
 * Öğretmen İdari ve Ders Materyalleri İçin Kapsamlı Belge ve PDF Çıktı Motoru
 * Okul adı, müdür adı, öğretmen adı ve haftalık ders saati bloklarına tam uyumludur.
 */
class DocumentExportService(private val context: Context) {

    /**
     * Haftalık Ders Saati Sayısına Göre Çoklu Saatli Günlük Plan PDF'i Üretir
     */
    fun exportMultiHourLessonPlanPdf(plan: MultiHourDailyPlan, profile: TeacherProfile): File {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // Standart A4
        val page = document.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val titlePaint = Paint().apply {
            color = Color.rgb(24, 43, 73)
            textSize = 12f
            isFakeBoldText = true
            isAntiAlias = true
        }

        val headerPaint = Paint().apply {
            color = Color.rgb(180, 83, 9)
            textSize = 9.5f
            isFakeBoldText = true
            isAntiAlias = true
        }

        val bodyPaint = Paint().apply {
            color = Color.rgb(33, 33, 33)
            textSize = 8.5f
            isAntiAlias = true
        }

        val borderPaint = Paint().apply {
            color = Color.LTGRAY
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }

        // Başlık ve Kurumsal Antet (Okul Adı, Şehir/İlçe)
        canvas.drawRect(30f, 25f, 565f, 85f, borderPaint)
        canvas.drawText("T.C. ${profile.cityDistrict.uppercase()} MİLLÎ EĞİTİM MÜDÜRLÜĞÜ", 160f, 42f, titlePaint)
        canvas.drawText(profile.schoolName.uppercase(), 170f, 58f, titlePaint)
        canvas.drawText("TÜRKİYE YÜZYILI MAARİF MODELİ TARİH DERSİ GÜNLÜK PLANI", 115f, 74f, headerPaint)

        var y = 105f
        canvas.drawText("Sınıf: ${plan.gradeLevel}. Sınıf  |  Haftalık Ders Saati: ${plan.weeklyHoursCount} Saat  |  Hafta: ${plan.weekNumber}. Hafta", 40f, y, titlePaint)
        y += 16f
        canvas.drawText("Tema / Öğrenme Alanı: ${plan.themeName}", 40f, y, bodyPaint)
        y += 14f
        canvas.drawText("Konu: ${plan.topicTitle}", 40f, y, bodyPaint)
        y += 14f
        canvas.drawText("Kök Değerler: ${plan.coreValues.joinToString { it.titleTr }}", 40f, y, bodyPaint)
        y += 18f

        // Ders Saatleri Detayları (1. Ders Saati, 2. Ders Saati)
        plan.lessonHours.forEach { hour ->
            canvas.drawLine(30f, y, 565f, y, borderPaint)
            y += 14f
            canvas.drawText("📌 ${hour.hourNumber}. DERS SAATİ: ${hour.hourTitle}", 40f, y, headerPaint)
            y += 13f
            canvas.drawText("Öğrenme Çıktısı: ${hour.learningOutcomes.take(90)}", 45f, y, bodyPaint)
            y += 13f
            canvas.drawText("Güdüleme / Nükte: ${hour.hookAndMotivation.take(95)}", 45f, y, bodyPaint)
            y += 13f
            canvas.drawText("İşleniş (İstasyon / 5E): ${hour.instructionalProcess.take(95)}", 45f, y, bodyPaint)
            y += 13f
            canvas.drawText("Çıkış Kartı: ${hour.evaluationAndExitTicket.take(95)}", 45f, y, bodyPaint)
            y += 16f
        }

        // Farklılaştırılmış Öğretim
        canvas.drawLine(30f, y, 565f, y, borderPaint)
        y += 14f
        canvas.drawText("FARKLILAŞTIRILMIŞ ÖĞRETİM (Zenginleştirme & Destekleme):", 40f, y, headerPaint)
        y += 13f
        canvas.drawText(plan.differentiatedInstruction.take(110), 45f, y, bodyPaint)

        // Resmî İmzalar (Öğretmen Adı ve Okul Müdürü Adı)
        canvas.drawText("Tarih Öğretmeni: ${profile.teacherName} (İmza)", 50f, 800f, titlePaint)
        canvas.drawText("Uygundur - Okul Müdürü: ${profile.principalName} (İmza)", 320f, 800f, titlePaint)

        document.finishPage(page)
        val outputFile = File(context.cacheDir, "Ders_Plani_${plan.gradeLevel}_Sinif_${System.currentTimeMillis()}.pdf")
        document.writeTo(FileOutputStream(outputFile))
        document.close()
        return outputFile
    }

    /**
     * Resmî MEB Formatında Zümre Öğretmenler Kurulu Tutanağını PDF Olarak Üretir
     */
    fun exportZumrePdf(record: ZumreMeetingRecord, profile: TeacherProfile): File {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = document.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val titlePaint = Paint().apply {
            color = Color.rgb(20, 35, 60)
            textSize = 11f
            isFakeBoldText = true
            isAntiAlias = true
        }

        val headerPaint = Paint().apply {
            color = Color.rgb(180, 83, 9)
            textSize = 9.5f
            isFakeBoldText = true
            isAntiAlias = true
        }

        val bodyPaint = Paint().apply {
            color = Color.rgb(40, 40, 40)
            textSize = 8.5f
            isAntiAlias = true
        }

        val borderPaint = Paint().apply {
            color = Color.LTGRAY
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }

        canvas.drawRect(30f, 25f, 565f, 85f, borderPaint)
        canvas.drawText("T.C. ${profile.cityDistrict.uppercase()} MİLLÎ EĞİTİM MÜDÜRLÜĞÜ", 160f, 42f, titlePaint)
        canvas.drawText(profile.schoolName.uppercase(), 170f, 58f, titlePaint)
        canvas.drawText("${record.schoolYear} ${record.term.uppercase()}", 130f, 74f, headerPaint)

        var currentY = 110f
        canvas.drawText("Toplantı Tarihi: ${record.meetingDate}", 40f, currentY, bodyPaint)
        currentY += 18f

        canvas.drawText("GÜNDEM MADDELERİ:", 40f, currentY, headerPaint)
        currentY += 14f
        record.agendaItems.forEach { item ->
            canvas.drawText(item, 45f, currentY, bodyPaint)
            currentY += 13f
        }

        currentY += 8f
        canvas.drawText("ALINAN KARARLAR (TÜRKİYE YÜZYILI MAARİF MODELİ UYGULAMALARI):", 40f, currentY, headerPaint)
        currentY += 14f
        record.decisionsTaken.forEach { decision ->
            canvas.drawText("• ${decision.take(95)}", 45f, currentY, bodyPaint)
            currentY += 13f
        }

        currentY += 8f
        canvas.drawText("ORTAÖĞRETİM KURUMLARI SINIF GEÇME VE ÖLÇME-DEĞERLENDİRME KRİTERLERİ:", 40f, currentY, headerPaint)
        currentY += 14f
        canvas.drawText(record.measurementEvaluationCriteria.take(105), 45f, currentY, bodyPaint)
        currentY += 13f
        canvas.drawText(record.passFailRegulationDecisions.take(105), 45f, currentY, bodyPaint)

        // İmzalar
        canvas.drawText("Zümre Başkanı / Öğretmen: ${profile.teacherName} (İmza)", 50f, 800f, titlePaint)
        canvas.drawText("Uygundur - Okul Müdürü: ${profile.principalName} (İmza)", 320f, 800f, titlePaint)

        document.finishPage(page)
        val file = File(context.cacheDir, "Zumre_Tutanagi_${System.currentTimeMillis()}.pdf")
        document.writeTo(FileOutputStream(file))
        document.close()
        return file
    }

    /**
     * Resmî MEB Yazılı Sınav Kağıdı ve Cevap Anahtarı (Rubrik) PDF Çıktısı
     */
    fun exportExamPaperPdf(exam: MebExamPaper, profile: TeacherProfile): File {
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
            textSize = 9f
            isAntiAlias = true
        }

        val rubricPaint = Paint().apply {
            color = Color.rgb(15, 118, 110)
            textSize = 8.5f
            isAntiAlias = true
        }

        // Antet & Okul Adı
        canvas.drawText("${profile.schoolName.uppercase()} 1. DÖNEM 1. ORTAK TARİH SINAVI", 40f, 40f, titlePaint)
        canvas.drawText("Adı Soyadı: ...................................   Sınıf/Şube: ..........   No: ..........   Puan: ......./100", 40f, 58f, questionPaint)
        canvas.drawLine(30f, 68f, 565f, 68f, Paint().apply { color = Color.DKGRAY; strokeWidth = 1f })

        var y = 88f
        exam.questions.forEach { q ->
            canvas.drawText("Soru ${q.questionNumber} (${q.pointValue} Puan) [Kazanım: ${q.outcomeCode}]", 40f, y, titlePaint)
            y += 13f
            q.sourceOrPremise?.let { premise ->
                canvas.drawText("Kaynak: ${premise.take(105)}", 45f, y, questionPaint)
                y += 13f
            }
            canvas.drawText(q.questionText.take(105), 45f, y, questionPaint)
            y += 32f
        }

        canvas.drawLine(30f, 660f, 565f, 660f, Paint().apply { color = Color.GRAY; strokeWidth = 1f })
        canvas.drawText("DERECELİ PUANLAMA ANAHTARI (RUBRİK):", 40f, 675f, titlePaint)
        y = 690f
        exam.rubricScoringKey.forEach { key ->
            canvas.drawText("Soru ${key.questionNumber}: ${key.expectedAnswer.take(85)} (${key.maxScore} Puan)", 45f, y, rubricPaint)
            y += 13f
        }

        canvas.drawText("Tarih Öğretmeni: ${profile.teacherName} (İmza)", 50f, 800f, titlePaint)
        canvas.drawText("Okul Müdürü: ${profile.principalName} (İmza)", 360f, 800f, titlePaint)

        document.finishPage(page)
        val file = File(context.cacheDir, "Yazili_Sinav_${exam.gradeLevel}_Sinif_${System.currentTimeMillis()}.pdf")
        document.writeTo(FileOutputStream(file))
        document.close()
        return file
    }

    /**
     * Maarif Modeli Yıllık Planını PDF Olarak Dışa Aktarır
     */
    fun exportAnnualPlanPdf(weeks: List<AnnualPlanWeek>, gradeLevel: Int, profile: TeacherProfile): File {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(842, 595, 1).create() // Yatay A4
        val page = document.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val titlePaint = Paint().apply {
            color = Color.rgb(24, 43, 73)
            textSize = 11f
            isFakeBoldText = true
            isAntiAlias = true
        }

        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = 8.5f
            isAntiAlias = true
        }

        canvas.drawText("T.C. ${profile.schoolName.uppercase()} - TÜRKİYE YÜZYILI MAARİF MODELİ $gradeLevel. SINIF TARİH YILLIK PLANI", 60f, 35f, titlePaint)
        
        var y = 65f
        canvas.drawText("Hafta / Ay | Tema & Öğrenme Çıktısı | Süreç Bileşenleri / Beceriler | Kök Değerler | Ölçme-Değerlendirme", 30f, y, titlePaint)
        canvas.drawLine(30f, y + 5, 812f, y + 5, Paint().apply { color = Color.BLACK })
        y += 18f

        weeks.forEach { week ->
            val rowText = "${week.weekNumber}. Hafta (${week.monthName}) | ${week.learningOutcomeCode}: ${week.learningOutcomeDescription.take(45)} | ${week.skillComponents.take(35)} | ${week.coreValues.joinToString { it.titleTr }} | ${week.measurementAndEvaluation.take(30)}"
            canvas.drawText(rowText, 30f, y, textPaint)
            y += 16f
        }

        canvas.drawText("Tarih Öğretmeni: ${profile.teacherName} (İmza)", 60f, 570f, titlePaint)
        canvas.drawText("Uygundur - Okul Müdürü: ${profile.principalName} (İmza)", 550f, 570f, titlePaint)

        document.finishPage(page)
        val file = File(context.cacheDir, "Yillik_Plan_${gradeLevel}_Sinif_${System.currentTimeMillis()}.pdf")
        document.writeTo(FileOutputStream(file))
        document.close()
        return file
    }

    /**
     * Üretilen herhangi bir dosyayı (PDF / DOCX) doğrudan kullanıcıya sunar, açar veya paylaştırır
     */
    fun shareOrOpenFile(file: File, mimeType: String) {
        try {
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = mimeType
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_SUBJECT, file.nameWithoutExtension)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(Intent.createChooser(intent, "Dosyayı Aç / Paylaş / İndir").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
