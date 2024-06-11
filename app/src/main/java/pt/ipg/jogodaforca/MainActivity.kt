package pt.ipg.jogodaforca

import android.os.Bundle
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    val palavraMostrada =
        palavra.map { if (letrasAdivinhadas.contains(it)) it else '_' }.joinToString(" ")


    val homemForcaImageResource = when (tentativasErradas) {
        0 -> R.drawable.forca0
        1 -> R.drawable.forca1
        2 -> R.drawable.forca2
        3 -> R.drawable.forca3
        4 -> R.drawable.forca4
        5 -> R.drawable.forca5
        else -> R.drawable.forca6
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(homemForcaImageResource),
            contentDescription = "Forca"
        )
        Spacer(modifier = Modifier.height(16.dp))


        Text(text = "Palavra: $palavraMostrada", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Tentativas restantes: ${tentativasMaximas - tentativasErradas}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = input,
            onValueChange = { input = it.take(1) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (input.isNotEmpty()) {
                    val letra = input[0]
                    if (!letrasAdivinhadas.contains(letra)) {
                        letrasAdivinhadas.add(letra)
                        if (!palavra.contains(letra)) {
                            tentativasErradas++
                        }
                        input = ""
                    }
                }
            }
        ) {
            Text(text = stringResource(R.string.adivinhar))
        }

        Spacer(modifier = Modifier.height(16.dp))


        if (tentativasErradas >= tentativasMaximas) {
            Text(text = "Você perdeu! A palavra era \"$palavra\".", style = MaterialTheme.typography.bodyMedium)
        } else if (palavra.all { letrasAdivinhadas.contains(it) }) {
            Text(text = "Parabéns! Você adivinhou a palavra!", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
fun escolherPalavra(): String{
    TODO("Not yet implemented")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JogoDaForcaTheme {
        ForcaWithButtonAndImage()
    }
}