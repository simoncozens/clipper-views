package uk.co.corvelsoftware.viewsclipper

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView


val clipTestFont =
    FontFamily(
        Font(
            R.font.cliptest,
        )
    )

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                MainWhatsit()
            }
        }
    }
}

@Composable
fun styleToTypeface(style: TextStyle): Typeface {
    val resolver: FontFamily.Resolver = LocalFontFamilyResolver.current

    val typeface: Typeface = remember(resolver, style) {
        resolver.resolve(
            fontFamily = style.fontFamily,
            fontWeight = style.fontWeight ?: FontWeight.Normal,
            fontStyle = style.fontStyle ?: FontStyle.Normal,
            fontSynthesis = style.fontSynthesis ?: FontSynthesis.All,
        )
    }.value as Typeface
    return typeface
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn()
@Composable
fun MainWhatsit() {
    var customFont = clipTestFont;
    var customFontView = styleToTypeface(TextStyle(fontFamily=customFont));
    val fontSize = 80.sp;

    var text by remember { mutableStateOf("AB") }
    Scaffold {

        Column(
            modifier = Modifier
                .padding(PaddingValues(8.dp))
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OutlinedTextField(
                value = text,
                textStyle = TextStyle(
                    fontFamily = customFont,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    ),
                    fontSize=fontSize),
                onValueChange = { text = it },
                label = { Text("Input text (font padding off)") },
                            )
            OutlinedTextField(
                value = text,
                textStyle = TextStyle(
                    fontFamily = customFont,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = true,
                    ),
                    fontSize=fontSize),
                onValueChange = { text = it },
                label = { Text("Input text (font padding on)") },

            )
            Text("Compose Text")
            Text(
                text,
                fontSize = fontSize,
                fontFamily = customFont,
                modifier = Modifier
                    .background(Color.LightGray) // Add background color
            )
            Text("AndroidView, elegantTextHeight=false, includeFontPadding=false")
            AndroidView({ context ->
                var view = TextView(context)
                view.text=text
                view.isElegantTextHeight = false
                view.includeFontPadding = false
                view.typeface = customFontView
                view.textSize = fontSize.value
                view
            }, update = { it.text = text })
            Text("AndroidView, elegantTextHeight=false, includeFontPadding=true")
            AndroidView({ context ->
                var view = TextView(context)
                view.text=text
                view.isElegantTextHeight = false
                view.includeFontPadding = true
                view.typeface = customFontView
                view.textSize = fontSize.value
                view
            }, update = { it.text = text })

            Text("AndroidView, elegantTextHeight=true, includeFontPadding=false")
            AndroidView({ context ->
                var view = TextView(context)
                view.text=text
                view.isElegantTextHeight = true
                view.includeFontPadding = false
                view.typeface = customFontView
                view.textSize = fontSize.value
                view
            }, update = { it.text = text })
            Text("AndroidView, elegantTextHeight=true, includeFontPadding=true")
            AndroidView({ context ->
                var view = TextView(context)
                view.text=text
                view.isElegantTextHeight = true
                view.includeFontPadding = true
                view.textSize = fontSize.value
                view.typeface = customFontView
                view
            }, update = { it.text = text })

            OutlinedButton(onClick = { }) { Text(text, style= TextStyle(
                fontFamily = customFont,
                fontSize=fontSize),

                )}
        }
    }
}
