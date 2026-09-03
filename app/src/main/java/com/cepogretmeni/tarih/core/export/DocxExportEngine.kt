package com.cepogretmeni.tarih.core.export

import android.content.Context
import com.cepogretmeni.tarih.domain.model.MultiHourDailyPlan
import com.cepogretmeni.tarih.domain.model.TeacherProfile
import com.cepogretmeni.tarih.domain.model.ZumreMeetingRecord
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * Microsoft Word (.docx) Dosya Oluşturma ve Dışa Aktarma Motoru
 * Harici kütüphane gerektirmeksizin standart Office OpenXML formatında .docx üretir.
 */
class DocxExportEngine(private val context: Context) {

    /**
     * Günlük Ders Planını (.docx) Olarak Üretir
     */
    fun createLessonPlanDocx(plan: MultiHourDailyPlan, profile: TeacherProfile): File {
        val docXml = buildString {
            append("""<?xml version="1.0" encoding="UTF-8" standalone="yes"?>""")
            append("""<w:document xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">""")
            append("<w:body>")

            // Üst Başlık
            append(renderDocxParagraph("T.C. MİLLÎ EĞİTİM BAKANLIĞI", bold = true, size = 28, center = true))
            append(renderDocxParagraph("${profile.cityDistrict} - ${profile.schoolName}", bold = true, size = 24, center = true))
            append(renderDocxParagraph("TÜRKİYE YÜZYILI MAARİF MODELİ TARİH DERSİ GÜNLÜK PLANI", bold = true, size = 26, center = true, color = "182B49"))
            append(renderDocxParagraph("────────────────────────────────────────────────────────", center = true))

            // Bilgi Tablosu
            append(renderDocxParagraph("Sınıf Düzeyi: ${plan.gradeLevel}. Sınıf  |  Haftalık Ders Saati: ${plan.weeklyHoursCount} Saat  |  Hafta: ${plan.weekNumber}. Hafta", bold = true))
            append(renderDocxParagraph("Tema / Öğrenme Alanı: ${plan.themeName}"))
            append(renderDocxParagraph("Konu: ${plan.topicTitle}"))
            append(renderDocxParagraph("Temel Soru: ${plan.essentialQuestion}"))
            append(renderDocxParagraph("Kök Değerler (Erdem-Değer-Eylem): ${plan.coreValues.joinToString { it.titleTr }}"))

            // Haftalık Ders Saatleri Ayrımı (1. Saat, 2. Saat vb.)
            plan.lessonHours.forEach { hour ->
                append(renderDocxParagraph("────────────────────────────────────────────────────────"))
                append(renderDocxParagraph("📌 ${hour.hourNumber}. DERS SAATİ AKIŞI: ${hour.hourTitle}", bold = true, size = 24, color = "B45309"))
                append(renderDocxParagraph("Öğrenme Çıktısı: ${hour.learningOutcomes}"))
                append(renderDocxParagraph("Güdüleme & Nükte (Giriş): ${hour.hookAndMotivation}"))
                append(renderDocxParagraph("Öğrenme-Öğretme Süreci (İstasyon / 5E): ${hour.instructionalProcess}"))
                append(renderDocxParagraph("Değerlendirme & Çıkış Kartı: ${hour.evaluationAndExitTicket}"))
            }

            // Farklılaştırılmış Eğitim
            append(renderDocxParagraph("────────────────────────────────────────────────────────"))
            append(renderDocxParagraph("FARKLILAŞTIRILMIŞ ÖĞRETİM (ZENGİNLEŞTİRME / DESTEKLEME):", bold = true))
            append(renderDocxParagraph(plan.differentiatedInstruction))

            // İmzalar
            append(renderDocxParagraph("\n\n"))
            append(renderDocxParagraph("Tarih Öğretmeni: ${profile.teacherName} (İmza)                    Okul Müdürü: ${profile.principalName} (İmza)", bold = true))

            append("</w:body></w:document>")
        }

        val outputFile = File(context.cacheDir, "Ders_Plani_${plan.gradeLevel}_Sinif_${System.currentTimeMillis()}.docx")
        writeDocxZip(outputFile, docXml)
        return outputFile
    }

    /**
     * Zümre Tutanağını (.docx) Olarak Üretir
     */
    fun createZumreDocx(record: ZumreMeetingRecord, profile: TeacherProfile): File {
        val docXml = buildString {
            append("""<?xml version="1.0" encoding="UTF-8" standalone="yes"?>""")
            append("""<w:document xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">""")
            append("<w:body>")

            append(renderDocxParagraph("T.C. MİLLÎ EĞİTİM BAKANLIĞI", bold = true, size = 28, center = true))
            append(renderDocxParagraph("${profile.cityDistrict} - ${profile.schoolName}", bold = true, size = 24, center = true))
            append(renderDocxParagraph("${record.schoolYear} ${record.term.uppercase()}", bold = true, size = 26, center = true, color = "182B49"))
            append(renderDocxParagraph("Toplantı Tarihi: ${record.meetingDate}"))

            append(renderDocxParagraph("\nGÜNDEM MADDELERİ:", bold = true, color = "B45309"))
            record.agendaItems.forEach { item ->
                append(renderDocxParagraph(item))
            }

            append(renderDocxParagraph("\nALINAN KARARLAR (TÜRKİYE YÜZYILI MAARİF MODELİ UYGULAMALARI):", bold = true, color = "B45309"))
            record.decisionsTaken.forEach { decision ->
                append(renderDocxParagraph("• $decision"))
            }

            append(renderDocxParagraph("\nORTAÖĞRETİM KURUMLARI SINIF GEÇME VE ÖLÇME ESASLARI:", bold = true, color = "B45309"))
            append(renderDocxParagraph(record.measurementEvaluationCriteria))
            append(renderDocxParagraph(record.passFailRegulationDecisions))

            append(renderDocxParagraph("\n\n"))
            append(renderDocxParagraph("Zümre Başkanı / Öğretmen: ${profile.teacherName} (İmza)        Uygundur - Okul Müdürü: ${profile.principalName} (İmza)", bold = true))

            append("</w:body></w:document>")
        }

        val outputFile = File(context.cacheDir, "Zumre_Tutanagi_${System.currentTimeMillis()}.docx")
        writeDocxZip(outputFile, docXml)
        return outputFile
    }

    private fun renderDocxParagraph(text: String, bold: Boolean = false, size: Int = 22, center: Boolean = false, color: String? = null): String {
        return buildString {
            append("<w:p>")
            append("<w:pPr>")
            if (center) append("<w:jc w:val=\"center\"/>")
            append("</w:pPr>")
            append("<w:r>")
            append("<w:rPr>")
            if (bold) append("<w:b/>")
            append("<w:sz w:val=\"$size\"/>")
            if (color != null) append("<w:color w:val=\"$color\"/>")
            append("<w:rFonts w:ascii=\"Calibri\" w:hAnsi=\"Calibri\"/>")
            append("</w:rPr>")
            append("<w:t xml:space=\"preserve\">${escapeXml(text)}</w:t>")
            append("</w:r>")
            append("</w:p>")
        }
    }

    private fun escapeXml(input: String): String {
        return input.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&apos;")
    }

    private fun writeDocxZip(file: File, documentXml: String) {
        val contentTypesXml = """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
    <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
    <Default Extension="xml" ContentType="application/xml"/>
    <Override PartName="/word/document.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml"/>
</Types>"""

        val relsXml = """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
    <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="word/document.xml"/>
</Relationships>"""

        ZipOutputStream(FileOutputStream(file)).use { zip ->
            // [Content_Types].xml
            zip.putNextEntry(ZipEntry("[Content_Types].xml"))
            zip.write(contentTypesXml.toByteArray())
            zip.closeEntry()

            // _rels/.rels
            zip.putNextEntry(ZipEntry("_rels/.rels"))
            zip.write(relsXml.toByteArray())
            zip.closeEntry()

            // word/document.xml
            zip.putNextEntry(ZipEntry("word/document.xml"))
            zip.write(documentXml.toByteArray())
            zip.closeEntry()
        }
    }
}
