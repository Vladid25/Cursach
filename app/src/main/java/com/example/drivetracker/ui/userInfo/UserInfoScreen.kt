package com.example.drivetracker.ui.userInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.drivetracker.R
import com.example.drivetracker.ui.order.BottomAppBarWithThreeSections
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserInfoScreen(
    navHostController: NavHostController,
    auth:FirebaseAuth
){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Інформація про користувача",
                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(20.dp)
            )
            Card {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.user_icon),
                        contentDescription = "User image",
                        modifier = Modifier
                            .size(125.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = auth.currentUser?.email.toString(),
                        fontSize = MaterialTheme.typography.displaySmall.fontSize,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
        }
        BottomAppBarWithThreeSections(navHostController)
    }
}

@Preview
@Composable
fun UserInfoScreenPreview(){
    //UserInfoScreen()
}
