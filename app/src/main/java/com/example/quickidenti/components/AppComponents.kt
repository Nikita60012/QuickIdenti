package com.example.quickidenti.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickidenti.R
import com.example.quickidenti.app.humanId
import com.example.quickidenti.dto.People.PeopleList
import com.example.quickidenti.navigation.QuickIdentiAppRouter
import com.example.quickidenti.navigation.Screen
import com.example.quickidenti.ui.theme.BgColor
import com.example.quickidenti.ui.theme.Primary
import com.example.quickidenti.ui.theme.Secondary
import com.example.quickidenti.ui.theme.TextColor
import com.example.quickidenti.ui.theme.TextLinkColor
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import kotlin.math.absoluteValue

@Composable
fun TextComponent(value: String,
                  fontSize: Int = 18,
                  textAlign: TextAlign = TextAlign.Center,
                  fontWeight: FontWeight = FontWeight.Normal,
                  heightIn: Int = 24){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = heightIn.dp),
        style = TextStyle(
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = textAlign
    )
}


@Composable
fun TextFieldComponent(
    labelValue: String,
    painterResource: Painter?,
    textValue: String,
    mask: MaskVisualTransformation = MaskVisualTransformation(""),
    withoutPatternField: Boolean = false,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default){
    if (withoutPatternField){
        if (painterResource != null)
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(4.dp)),
                value = textValue,
                label = { Text(text = labelValue) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor =  Primary,
                    focusedLabelColor = Primary,
                    cursorColor = Primary,
                    backgroundColor = BgColor
                ),
                keyboardOptions = keyboardOptions,
                visualTransformation = VisualTransformation.None,
                singleLine = true,
                onValueChange = onValueChange,
                leadingIcon = {
                    Icon(painter = painterResource, contentDescription = "")
                }
            )
        else
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(4.dp)),
                value = textValue,
                label = { Text(text = labelValue) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor =  Primary,
                    focusedLabelColor = Primary,
                    cursorColor = Primary,
                    backgroundColor = BgColor
                ),
                keyboardOptions = keyboardOptions,
                visualTransformation = VisualTransformation.None,
                singleLine = true,
                onValueChange = onValueChange
            )
    }else{
        if (painterResource != null)
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(4.dp)),
                value = textValue,
                label = { Text(text = labelValue) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor =  Primary,
                    focusedLabelColor = Primary,
                    cursorColor = Primary,
                    backgroundColor = BgColor
                ),
                keyboardOptions = keyboardOptions,
                visualTransformation = mask,
                singleLine = true,
                onValueChange = onValueChange,
                leadingIcon = {
                    Icon(painter = painterResource, contentDescription = "")
                }
            )
        else
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(4.dp)),
                value = textValue,
                label = { Text(text = labelValue) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor =  Primary,
                    focusedLabelColor = Primary,
                    cursorColor = Primary,
                    backgroundColor = BgColor
                ),
                keyboardOptions = keyboardOptions,
                visualTransformation = mask,
                singleLine = true,
                onValueChange = onValueChange
            )
    }

}

@Composable
fun PasswordTextFieldComponent(labelValue: String,
                               painterResource: Painter?,
                               password: String,
                               onPassChange: (String) -> Unit){

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    if(painterResource != null)
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(4.dp)),
            value = password,
            label = { Text(text = labelValue) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Primary,
                focusedLabelColor = Primary,
                cursorColor = Primary,
                backgroundColor = BgColor
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            onValueChange = onPassChange,
            leadingIcon = {

                Icon(painter = painterResource, contentDescription = "")
            },
            trailingIcon = {
                val iconImage = if(passwordVisible.value){
                    Icons.Filled.Visibility
                } else{
                    Icons.Filled.VisibilityOff
                }

                val description = if(passwordVisible.value){
                    stringResource(id = R.string.hide_password)
                }else{
                    stringResource(id = R.string.show_password)
                }

                IconButton(onClick = { passwordVisible.value = !passwordVisible.value}) {
                    Icon(imageVector = iconImage, contentDescription = description)
                }
            },
            visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
        )
    else
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(4.dp)),
            value = password,
            label = { Text(text = labelValue) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Primary,
                focusedLabelColor = Primary,
                cursorColor = Primary,
                backgroundColor = BgColor
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            onValueChange = onPassChange,
            trailingIcon = {
                val iconImage = if(passwordVisible.value){
                    Icons.Filled.Visibility
                } else{
                    Icons.Filled.VisibilityOff
                }

                val description = if(passwordVisible.value){
                    stringResource(id = R.string.hide_password)
                }else{
                    stringResource(id = R.string.show_password)
                }

                IconButton(onClick = { passwordVisible.value = !passwordVisible.value}) {
                    Icon(imageVector = iconImage, contentDescription = description)
                }
            },
            visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
        )
}

@Composable
fun ButtonComponent(value: String,
                    modifier: Modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(48.dp),
                    action: () -> Unit){
    Button(
        modifier = modifier,
        onClick = action,
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(50.dp)
        ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp)
            .background(
                brush = Brush.horizontalGradient(listOf(Secondary, Primary)),
                shape = RoundedCornerShape(50.dp)
            ),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold)
        }
    }
    
}
@Composable
fun ImageButtonComponent(imageVector: ImageVector, description: String, action: () -> Unit){
    Button(
        modifier = Modifier
            .widthIn(48.dp)
            .heightIn(48.dp),
        onClick = action,
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(50.dp)
    ) {
        Box(modifier = Modifier
            .widthIn(48.dp)
            .heightIn(48.dp)
            .background(
                brush = Brush.radialGradient(listOf(Secondary, Primary)),
                shape = RoundedCornerShape(50.dp)
            ),
            contentAlignment = Alignment.Center
        ){
            Icon(imageVector = imageVector, contentDescription = description)
        }
    }

}

@Composable
fun ClickableTextComponent(value: String, onTextSelected: (String) -> Unit){
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = TextLinkColor)){
            pushStringAnnotation(
                tag = value,
                annotation = value)
            append(value)
        }
    }
        ClickableText(
            modifier = Modifier.fillMaxWidth(),
            text = annotatedString,
            softWrap = true,
            style = TextStyle(
                fontSize = 21.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Center
            ),
            onClick = { offset ->
                annotatedString.getStringAnnotations(offset, offset)
                    .firstOrNull()?.also { span ->
//                        Log.d("ClickableTextComponent", "{$span")
                        if(span.item == value){
                            onTextSelected(span.item)
                        }
                    }
            })
}

@Composable
fun LoadingComponent(){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        val infiniteTransition = rememberInfiniteTransition(label = "")
        val angle by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                tween(2000, easing = LinearEasing),
                RepeatMode.Restart
            ), label = ""
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.loading),
                contentDescription = "Loading",
                modifier = Modifier
                    .height(128.dp)
                    .width(128.dp)
                    .rotate(angle)
            )
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun SimpleLazyColumnScreen(element: MutableList<PeopleList>,
                           delete: (Int) -> Unit) {
    val elements by remember { mutableStateOf(element) }
    Box {
        LazyColumn {
            items(elements) { element ->
                PersonView(id = element.id,
                    title = element.fullname,
                    deleteClick = {id ->
                        Log.i("Delete", "Man delete $id")
                        delete(id)
                    },
                    onItemClick = { id ->
                        Log.i("Click", "Man click $id")
                        humanId.intValue = id
                        QuickIdentiAppRouter.navigateTo(Screen.PersonInfoScreen, true)
                    })
            }
        }
    }
}

@Composable
fun PersonView(id: Int,
               title: String,
               deleteClick: (Int) -> Unit,
               onItemClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .clickable {
                    onItemClick(id)
                }
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Person",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Text(
                text = title,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable {
                        deleteClick(id)
                    }
            )

        }
    }
}

fun convertBitmap2File(context: Context, bitmap: Bitmap): String{
    val f = File(context.cacheDir, "human_photo")
    f.createNewFile()
    val bos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
    val bitmapdata = bos.toByteArray()
    val fos = FileOutputStream(f)
    fos.write(bitmapdata)
    fos.flush()
    fos.close()
    val base_str = Base64.encodeToString(bitmapdata, Base64.DEFAULT)
    return base_str
}

class MaskVisualTransformation(private val mask: String): VisualTransformation {
    private val specialSymbolsIndices = mask.indices.filter { mask[it] != 'X' }

    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        var maskIndex = 0
        text.forEach { char ->
            while (specialSymbolsIndices.contains(maskIndex)) {
                out += mask[maskIndex]
                maskIndex++
            }
            out += char
            maskIndex++
        }
        return TransformedText(AnnotatedString(out), offsetTranslator())
    }

    private fun offsetTranslator() = object: OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val offsetValue = offset.absoluteValue
            if (offsetValue == 0) return 0
            var numberOfHashtags = 0
            val masked = mask.takeWhile {
                if (it == 'X') numberOfHashtags++
                numberOfHashtags < offsetValue
            }
            return masked.length + 1
        }

        override fun transformedToOriginal(offset: Int): Int {
            return mask.take(offset.absoluteValue).count { it == 'X' }
        }
    }
}