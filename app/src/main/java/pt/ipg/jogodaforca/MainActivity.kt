package pt.ipg.jogodaforca

import android.os.Bundle
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
    var mensagemErro by remember { mutableStateOf("") }

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

    val jogoTerminado = tentativasErradas >= tentativasMaximas || palavra.all { letrasAdivinhadas.contains(it) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Button(
            onClick = {
                // Reinicia o jogo
                palavra = escolherPalavra()
                letrasAdivinhadas.clear()
                tentativasErradas = 0
                input = ""
            }
        ) {
            Text(text = "Reiniciar")
        }

        Spacer(modifier = Modifier.height(16.dp))



        Image(
            painter = painterResource(homemForcaImageResource),
            contentDescription = "Forca"
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ajuda",
            style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(8.dp))

        if (palavra== "programacao") {
            Text(text = "Área relacionada ao desenvolvimento de software.",style = MaterialTheme.typography.bodyMedium)
        }else if (palavra  == "android" ) {
                Text(text = "Sistema operativo para dispositivos móveis.", style = MaterialTheme.typography.bodyMedium)
        }else if (palavra  == "jogo da forca" ) {
            Text(text = "Jogo que estamos a jogar agora.", style = MaterialTheme.typography.bodyMedium)
        }else if (palavra  == "bicicleta" ) {
            Text(text = "Veículo de duas rodas movido a pedal.", style = MaterialTheme.typography.bodyMedium)
        }else if (palavra == "cinema" ) {
            Text(text = "Lugar onde se assiste a filmes.", style = MaterialTheme.typography.bodyMedium)
        }else if (palavra == "caneta" ) {
            Text(text = "Objeto utilizado para escrever.", style = MaterialTheme.typography.bodyMedium)
        }else if (palavra == "elefante" ) {
            Text(text = "Grande mamífero com tromba.", style = MaterialTheme.typography.bodyMedium)
        }else if (palavra == "universidade" ) {
            Text(text = "Instituição de ensino superior.", style = MaterialTheme.typography.bodyMedium)
        }else if (palavra == "benfica" ) {
            Text(text = "Clube de portugal.", style = MaterialTheme.typography.bodyMedium)
        }else if (palavra == "vermelho" ) {
            Text(text = "Cor do arco-iris.", style = MaterialTheme.typography.bodyMedium)
        }else if (palavra  == "futebol"){
            Text(text = "Desporto que mais se pratica em Portugal.", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))


        Text(text = "Palavra: $palavraMostrada", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Tentativas restantes: ${tentativasMaximas - tentativasErradas}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        EditTexField(
            label = R.string.letra,
            leadingIcon = R.drawable.textfield,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            value = input,
            onValueChange = { input = it.take(1) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            enabled = !jogoTerminado
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
                        mensagemErro = ""
                        input = ""
                    }else {
                        mensagemErro = "A letra $letra já foi adivinhada "
                    }

                }
            },
            enabled = !jogoTerminado
        ) {
            Text(text = stringResource(R.string.adivinhar))
        }

        if (mensagemErro.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = mensagemErro,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }


        Spacer(modifier = Modifier.height(16.dp))


        if (tentativasErradas >= tentativasMaximas) {
            Text(
                text = "Você perdeu! A palavra era \"$palavra\".",
                style = MaterialTheme.typography.bodyMedium)
        } else if (palavra.all { letrasAdivinhadas.contains(it) }) {
            Text(
                text = "Parabéns! Você adivinhou a palavra!",
                style = MaterialTheme.typography.bodyMedium)
        }
    }
}
fun escolherPalavra(): String{
    val palavras = listOf("programacao",
        "android",
        "jogo da forca",
        "bicicleta",
        "cinema",
        "caneta",
        "elefante",
        "universidade",
        "vermelho",
        "benfica",
        "futebol")
    return palavras.random()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JogoDaForcaTheme {
        ForcaWithButtonAndImage()
    }
}


@Composable
fun EditTexField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {

    TextField(
        value = value,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), null) },
        onValueChange = onValueChange,
        label = { Text(stringResource(label))},
        singleLine = true,
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        enabled = enabled

    )

}