package com.pg.fileeditor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomButtons() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // Added padding for better visual appearance
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Added space between buttons
        ) {
            ActionButton(text = "Save", modifier = Modifier.weight(1f))
            ActionButton(text = "Open", modifier = Modifier.weight(1f))
            ActionButton(text = "History", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ActionButton(text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = { /*TODO*/ },
        shape = RectangleShape,
        modifier = modifier.height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = text, color = Color.White)
    }
}


@Preview(showBackground = true)
@Composable
fun BottomButtonsPreview() {
    BottomButtons()
}