package com.cepogretmeni.tarih.core.pdf

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import com.cepogretmeni.tarih.data.local.entities.LessonPlanEntity
import com.cepogretmeni.tarih.data.local.entities.SelfEvaluationEntity
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Android Document & Printing API'leri ile MEB Uyumlu A4 PDF ve Yazdırma Motoru
 */
class PdfExportEngine(private val context: Context) {

    /**
     * Maarif Modeli Günlük Ders Planını Profesyonel MEB Formatında PDF Olarak Oluşturur
     */
    fun createLessonPlanPdf(plan: LessonPlanEntity): File {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // Standard A4 points
        val page = document.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val titlePaint = Paint().apply {
            color = Color.rgb(24, 43, 73) // Gece laciverti
            textSize = 14f
            isFakeBoldText = true
            isAntiAlias = true
        }

        val headerPaint = Paint().apply {
            color = Color.rgb(180, 83, 9) // Altın / kehribar
            textSize = 11f
            isFakeBoldText = true
            isAntiAlias = true
        }

        val bodyPaint = Paint().apply {
            color = Color.rgb(33, 33, 33)
            textSize = 10f
            isAntiAlias = true
        }

        val borderPaint = Paint().apply {
            color = Color.rgb(200, 200, 200)
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }

        // Başlık Çerçevesi ve Antet
        canvas.drawRect(30f, 30f, 565f, 95f, borderPaint)
        canvas.drawText("T.C. MİLLÎ EĞİTİM BAKANLIĞI", 210f, 50f, titlePaint)
        canvas.drawText("TÜRKİYE YÜZYILI MAARİF MODELİ TARİH DERSİ GÜNLÜK PLANI", 110f, 70f, headerPaint)

        // Bilgi Tablosu
        var currentY = 120f
        canvas.drawText("Tema / Öğrenme Alanı: ${plan.themeName}", 40f, currentY, bodyPaint)
        currentY += 20f
        canvas.drawText("Konu: ${plan.topicTitle} (Sınıf: ${plan.gradeLevel}. Sınıf - Süre: ${plan.durationMinutes} dk)", 40f, currentY, bodyPaint)
        currentY += 25f

        // Kök Değerler & Beceriler
        canvas.drawLine(30f, currentY, 565f, currentY, borderPaint)
        currentY += 20f
        canvas.drawText("1. HEDEF ALAN BECERİLERİ & KÖK DEĞERLER", 40f, currentY, headerPaint)
        currentY += 15f
        canvas.drawText("Alan Becerileri: ${plan.primarySkills}", 45f, currentY, bodyPaint)
        currentY += 15f
        canvas.drawText("Kök Değerler (Erdem-Değer-Eylem): ${plan.coreValues}", 45f, currentY, bodyPaint)
        currentY += 25f

        // Güdüleme / Nükte / Köprü Kurma
        canvas.drawLine(30f, currentY, 565f, currentY, borderPaint)
        currentY += 20f
        canvas.drawText("2. GÜDÜLEME & TARİHSEL BAĞLAM (NÜKTE / BEYİT / PROBLEM DURUMU)", 40f, currentY, headerPaint)
        currentY += 15f
        canvas.drawText(plan.motivationHook.take(120), 45f, currentY, bodyPaint)
        currentY += 25f

        // Öğrenme - Öğretme Süreci
        canvas.drawLine(30f, currentY, 565f, currentY, borderPaint)
        currentY += 20f
        canvas.drawText("3. ÖĞRENME-ÖĞRETME YAŞANTILARI & ETKİNLİK AKIŞI", 40f, currentY, headerPaint)
        currentY += 15f
        canvas.drawText(plan.teachingSteps.take(150), 45f, currentY, bodyPaint)
        currentY += 25f

        // Ölçme & Değerlendirme
        canvas.drawLine(30f, currentY, 565f, currentY, borderPaint)
        currentY += 20f
        canvas.drawText("4. SÜREÇ ODAKLI ÖLÇME VE DEĞERLENDİRME (RUBRİK / ÖZ DEĞERLENDİRME)", 40f, currentY, headerPaint)
        currentY += 15f
        canvas.drawText(plan.assessmentMethods.take(150), 45f, currentY, bodyPaint)

        // Alt Bilgi / İmzalar
        val dateStr = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(plan.createdAtTimestamp))
        canvas.drawText("Tarih: $dateStr", 40f, 800f, bodyPaint)
        canvas.drawText("Tarih Öğretmeni / Zümre Başkanı (İmza)", 360f, 800f, bodyPaint)

        document.finishPage(page)

        val outputFile = File(context.cacheDir, "Ders_Plani_${plan.id}_${System.currentTimeMillis()}.pdf")
        document.writeTo(FileOutputStream(outputFile))
        document.close()
        return outputFile
    }

    /**
     * Öğrenci Öz Değerlendirme Formunu PDF Olarak Dışa Aktarır
     */
    fun createSelfEvaluationPdf(evaluation: SelfEvaluationEntity): File {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = document.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val titlePaint = Paint().apply {
            color = Color.rgb(20, 30, 60)
            textSize = 14f
            isFakeBoldText = true
            isAntiAlias = true
        }

        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = 11f
            isAntiAlias = true
        }

        canvas.drawText("TÜRKİYE YÜZYILI MAARİF MODELİ - ÖĞRENCİ ÖZ DEĞERLENDİRME FORMU", 60f, 60f, titlePaint)
        canvas.drawText("Öğrenci: ${evaluation.studentName} (${evaluation.studentNumber}) - Sınıf: ${evaluation.gradeLevel}", 50f, 100f, textPaint)
        canvas.drawText("Konu: ${evaluation.topicTitle}", 50f, 120f, textPaint)
        canvas.drawText("Toplam Yetkinlik Skoru: %${evaluation.totalCompetencyScore.toInt()}", 50f, 140f, textPaint)

        canvas.drawText("1. Tarihsel Sorgulama Seviyesi: ${evaluation.inquirySkillLevel}/3", 50f, 180f, textPaint)
        canvas.drawText("2. Kanıt ve Kaynak Kullanımı: ${evaluation.evidenceSkillLevel}/3", 50f, 205f, textPaint)
        canvas.drawText("3. Kronolojik Düşünme & Değişim: ${evaluation.chronologySkillLevel}/3", 50f, 230f, textPaint)
        canvas.drawText("4. Tarihsel Empati & Zihniyet: ${evaluation.empathySkillLevel}/3", 50f, 255f, textPaint)
        canvas.drawText("5. Mekân ve Harita Okuryazarlığı: ${evaluation.spatialSkillLevel}/3", 50f, 280f, textPaint)

        canvas.drawText("Öğrenci Yansıtıcı Düşünme Notu:", 50f, 320f, textPaint)
        canvas.drawText(evaluation.reflectionNotes, 50f, 345f, textPaint)

        document.finishPage(page)

        val outputFile = File(context.cacheDir, "Oz_Degerlendirme_${evaluation.studentNumber}_${System.currentTimeMillis()}.pdf")
        document.writeTo(FileOutputStream(outputFile))
        document.close()
        return outputFile
    }

    /**
     * Android PrintManager üzerinden doğrudan kablosuz yazıcıya veya PDF kaydetme diyaloğuna yönlendirir
     */
    fun printDocument(jobName: String, pdfFile: File) {
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as? PrintManager ?: return
        val printAdapter = object : PrintDocumentAdapter() {
            override fun onLayout(
                oldAttributes: PrintAttributes?,
                newAttributes: PrintAttributes?,
                cancellationSignal: CancellationSignal?,
                callback: LayoutResultCallback?,
                extras: android.os.Bundle?
            ) {
                if (cancellationSignal?.isCanceled == true) {
                    callback?.onLayoutCancelled()
                    return
                }
                val info = PrintDocumentInfo.Builder(jobName)
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(1)
                    .build()
                callback?.onLayoutFinished(info, true)
            }

            override fun onWrite(
                pages: Array<out PageRange>?,
                destination: ParcelFileDescriptor?,
                cancellationSignal: CancellationSignal?,
                callback: WriteResultCallback?
            ) {
                try {
                    val input = pdfFile.inputStream()
                    val output = FileOutputStream(destination?.fileDescriptor)
                    input.copyTo(output)
                    callback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
                } catch (e: Exception) {
                    callback?.onWriteFailed(e.message)
                }
            }
        }
        printManager.print(jobName, printAdapter, PrintAttributes.Builder().build())
    }
}
