package com.example.quickidenti.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickidenti.R
import com.example.quickidenti.ui.theme.BgColor
import com.example.quickidenti.ui.theme.Primary
import com.example.quickidenti.ui.theme.Secondary
import com.example.quickidenti.ui.theme.TextColor
import com.example.quickidenti.ui.theme.TextLinkColor

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
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default){
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
        singleLine = true,
        onValueChange = onValueChange
    )
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
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(2000, easing = LinearEasing),
            RepeatMode.Restart
        ), label = ""
    )
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter= painterResource(R.drawable.loading),
            contentDescription = "Loading",
            modifier = Modifier
                .height(128.dp)
                .width(128.dp)
                .rotate(angle))
    }
}