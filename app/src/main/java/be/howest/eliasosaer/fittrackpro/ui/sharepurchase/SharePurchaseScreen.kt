package be.howest.eliasosaer.fittrackpro.ui.sharepurchase

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val ScreenBg = Color(0xFFF3F3F3)
private val CheckoutBlue = Color(0xFF5B86D9)

@Composable
fun SharePurchaseScreen(
    onShared: () -> Unit,
    onNoThanksClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBg)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Share your purchase?",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Let your friends know about your new FitTrackPro gear.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {
                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "I just bought some new gear in FitTrackPro! 💪"
                    )
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, "Share your purchase")
                context.startActivity(shareIntent)
                onShared()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CheckoutBlue,
                contentColor = Color.White
            )
        ) {
            Text("Share with friends")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onNoThanksClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text("No thanks")
        }
    }
}