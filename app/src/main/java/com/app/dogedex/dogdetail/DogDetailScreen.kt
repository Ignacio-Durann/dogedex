package com.app.dogedex.dogdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.dogedex.R
import com.app.dogedex.model.Dog

@Composable
fun DogDetailScreen() {
        Box (
            modifier = Modifier
                .background(colorResource(id = R.color.secondary_background))
                .padding(start =  8.dp, top = 16.dp, end = 8.dp, bottom = 16.dp),
            contentAlignment = Alignment.TopCenter)
        {
            val dog = Dog(1L,78,"Pug","Herding","","","","","",
                "amistoso","","","","","","", true)
        DogInformation(dog)
        }
}

@Composable
fun DogInformation(dog: Dog) {
    Box(modifier = Modifier.fillMaxWidth()
        .padding(top = 180.dp))
    {
    Surface(modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        colorResource(id = R.color.white)
    ) {
        Column(modifier = Modifier.fillMaxWidth()
            .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.dog_index_format, dog.index),
                fontSize = 32.sp,
                color = colorResource(id = R.color.text_black),
                textAlign = TextAlign.End
            )
        }
    }
    }
}


@Preview
@Composable
fun DogDetailPreview(){
    DogDetailScreen()
}
