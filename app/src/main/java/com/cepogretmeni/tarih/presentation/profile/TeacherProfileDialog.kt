package com.cepogretmeni.tarih.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cepogretmeni.tarih.domain.model.TeacherProfile

/**
 * Öğretmen, Okul ve Müdür Bilgileri Ayar Penceresi
 * Ders planları, yıllık planlar ve zümre tutanaklarına otomatik eklenir.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherProfileDialog(
    currentProfile: TeacherProfile,
    onDismiss: () -> Unit,
    onSaveProfile: (TeacherProfile) -> Unit
) {
    var schoolName by remember { mutableStateOf(currentProfile.schoolName) }
    var teacherName by remember { mutableStateOf(currentProfile.teacherName) }
    var principalName by remember { mutableStateOf(currentProfile.principalName) }
    var cityDistrict by remember { mutableStateOf(currentProfile.cityDistrict) }
    var weeklyHours by remember { mutableIntStateOf(currentProfile.defaultWeeklyHours) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onSaveProfile(
                        currentProfile.copy(
                            schoolName = schoolName,
                            teacherName = teacherName,
                            principalName = principalName,
                            cityDistrict = cityDistrict,
                            defaultWeeklyHours = weeklyHours
                        )
                    )
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Ayarları Kaydet", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("İptal", color = Color(0xFF94A3B8))
            }
        },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.School, contentDescription = null, tint = Color(0xFF38BDF8))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Okul & Öğretmen Bilgileri",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = Color.White
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Bu bilgiler tüm günlük/yıllık plan ve zümre tutanaklarının resmî antet ve imza alanlarına otomatik yazılır.",
                    fontSize = 11.sp,
                    color = Color(0xFF94A3B8)
                )

                OutlinedTextField(
                    value = schoolName,
                    onValueChange = { schoolName = it },
                    label = { Text("Okul Adı") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF38BDF8),
                        unfocusedBorderColor = Color(0xFF334155)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = teacherName,
                    onValueChange = { teacherName = it },
                    label = { Text("Tarih Öğretmeni Adı Soyadı") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF38BDF8),
                        unfocusedBorderColor = Color(0xFF334155)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = principalName,
                    onValueChange = { principalName = it },
                    label = { Text("Okul Müdürü Adı Soyadı") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF38BDF8),
                        unfocusedBorderColor = Color(0xFF334155)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = cityDistrict,
                    onValueChange = { cityDistrict = it },
                    label = { Text("İl / İlçe (Örn: Ankara / Çankaya)") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF38BDF8),
                        unfocusedBorderColor = Color(0xFF334155)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Haftalık Ders Saati:", color = Color.White, fontSize = 13.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf(2, 3, 4).forEach { hours ->
                            FilterChip(
                                selected = weeklyHours == hours,
                                onClick = { weeklyHours = hours },
                                label = { Text("$hours Saat", fontSize = 11.sp) }
                            )
                        }
                    }
                }
            }
        },
        containerColor = Color(0xFF1E293B),
        shape = RoundedCornerShape(16.dp)
    )
}
