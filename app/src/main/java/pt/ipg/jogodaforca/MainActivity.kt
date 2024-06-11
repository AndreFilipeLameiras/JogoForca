package pt.ipg.jogodaforca

import android.os.Bundle
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pt.ipg.jogodaforca.ui.theme.JogoDaForcaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JogoDaForcaTheme {
                ForcaWithButtonAndImage(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center))
            }
        }
    }
}

@Composable
fun ForcaWithButtonAndImage(modifier: Modifier = Modifier) {
    var palavra by remember { mutableStateOf(escolherPalavra()) }
    var letrasAdivinhadas by remember { mutableStateOf(mutableSetOf<Char>()) }
    var tentativasErradas by remember { mutableStateOf(0) }
    val tentativasMaximas = 6
    var input by remember { mutableStateOf("") }

    val homemForcaImageResource = when (tentativasErradas){
        0 -> R.drawable.forca0
        1 -> R.drawable.forca1
        2 -> R.drawable.forca2
        3 -> R.drawable.forca3
        4 -> R.drawable.forca4
        5 -> R.drawable.forca5
        else -> R.drawable.forca6
    }



}

fun escolherPalavra(): Any {
    TODO("Not yet implemented")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JogoDaForcaTheme {
        ForcaWithButtonAndImage()
    }
}