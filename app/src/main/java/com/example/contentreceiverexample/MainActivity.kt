package com.example.contentreceiverexample

import android.annotation.SuppressLint
import android.net.Uri

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.contentreceiverexample.ui.theme.ContentReceiverExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContentReceiverExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    button()
                }
            }
        }
    }
}

const val URL = "content://com.example.contentproviderexample.ui.RevatureCP.provider/users"
val CONTENT_URI: Uri =Uri.parse(URL)

@SuppressLint("Range")
@Composable
fun button() {

    var result by remember { mutableStateOf("") }
    var context = LocalContext.current
    Column(Modifier.fillMaxWidth()) {
        Button(onClick = {
            val cursor = context.contentResolver.query(CONTENT_URI,null,null,null,null)

            if(cursor!!.moveToFirst()) {     //move to first row
                val strBuild = StringBuilder()
                while (!cursor.isAfterLast) {       //as long as new row is found
                    result+="${cursor.getString(cursor.getColumnIndex("id"))}-${cursor.getString(cursor.getColumnIndex("name"))}\n"
                    cursor.moveToNext()
                }
                Log.d("Data","$result")
            } else {
                Log.d("Data","No Records Found")
            }

        }) {
            Text(text = "Button")
        }
    }
}

@Preview
@Composable
fun previewScreen() {
    button()
}