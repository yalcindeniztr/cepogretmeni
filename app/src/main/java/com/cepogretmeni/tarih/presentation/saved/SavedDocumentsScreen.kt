package com.cepogretmeni.tarih.presentation.saved

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cepogretmeni.tarih.data.local.entities.SavedDocumentEntity

/**
 * Uygulama İçi Kaydedilen Maarif Modeli Belgeleri (Ders Planları, Sınavlar, Zümreler)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedDocumentsScreen(
    savedDocuments: List<SavedDocumentEntity>,
    onDeleteDocument: (SavedDocumentEntity) -> Unit = {},
    onExportPdf: (SavedDocumentEntity) -> Unit = {},
    onExportWord: (SavedDocumentEntity) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.School, contentDescription = null, tint = Color(0xFF38BDF8))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Kayıtlı Resmî Belgelerim",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0F172A))
            )
        }
    ) { paddingValues ->
        if (savedDocuments.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFF0B0F19)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        tint = Color(0xFF475569),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Henüz kaydedilmiş bir ders planı veya belge yok.",
                        color = Color(0xFF94A3B8),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Tarih Asistanı'ndan hazırlattığınız planları '💾 Kaydet' butonuyla buraya ekleyebilirsiniz.",
                        color = Color(0xFF64748B),
                        fontSize = 12.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFF0B0F19))
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(savedDocuments) { doc ->
                    SavedDocumentCard(
                        doc = doc,
                        onDelete = { onDeleteDocument(doc) },
                        onExportPdf = { onExportPdf(doc) },
                        onExportWord = { onExportWord(doc) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SavedDocumentCard(
    doc: SavedDocumentEntity,
    onDelete: () -> Unit,
    onExportPdf: () -> Unit,
    onExportWord: () -> Unit
) {
    val typeColor = when (doc.documentType) {
        "GÜNLÜK DERS PLANI" -> Color(0xFF2563EB)
        "YILLIK PLAN" -> Color(0xFF7C3AED)
        "ZÜMRE TUTANAĞI" -> Color(0xFFD97706)
        else -> Color(0xFF059669)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = typeColor.copy(alpha = 0.2f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, typeColor)
                ) {
                    Text(
                        text = "${doc.gradeLevel}. SINIF • ${doc.documentType}",
                        color = typeColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                IconButton(onClick = onDelete, modifier = Modifier.size(28.dp)) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Sil",
                        tint = Color(0xFFEF4444),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = doc.title,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Okul: ${doc.schoolName}  •  Öğretmen: ${doc.teacherName}  •  Müdür: ${doc.principalName}",
                fontSize = 11.sp,
                color = Color(0xFF94A3B8)
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Oluşturulma: ${doc.createdAtFormatted}",
                fontSize = 10.sp,
                color = Color(0xFF64748B)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // İndirme ve Yazdırma Butonları
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onExportPdf,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD97706)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Icon(imageVector = Icons.Default.PictureAsPdf, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("PDF İndir", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onExportWord,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Description, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Word (.docx)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
