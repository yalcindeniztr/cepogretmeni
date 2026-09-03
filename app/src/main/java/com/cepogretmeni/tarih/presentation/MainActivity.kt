package com.cepogretmeni.tarih.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import com.cepogretmeni.tarih.presentation.navigation.RootNavigator

/**
 * Shell Activity - Sadece RootNavigator ve Provider'ları barındırır.
 * Mimarî kural gereği doğrudan UI veya iş mantığı içermez.
 */
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootNavigator(activity = this)
        }
    }
}
