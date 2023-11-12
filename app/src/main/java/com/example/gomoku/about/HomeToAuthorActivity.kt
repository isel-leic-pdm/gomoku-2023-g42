package com.example.gomoku.about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gomoku.authors.AuthorScreen

class HomeToAuthorActivity : ComponentActivity() {
    private val url = Uri.parse("https://outlook.office.com/mail/")

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, HomeToAuthorActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("AboutActivity", "onCreate")
        setContent {
            AuthorScreen(
                onInfoRequested = { openSendEmail() },
                onHomeRequested = { AuthorsToHomeActivity.navigateTo(this) }
            )
        }
    }

    private fun openSendEmail() {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(AUTHOR_EMAIL))
                putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT)
            }
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e("AboutActivity", "No activity found to handle $url")
        }

    }
}

private const val AUTHOR_EMAIL = "a48259@alunos.isel.pt"

private const val EMAIL_SUBJECT = "Gomoku - Feedback"




