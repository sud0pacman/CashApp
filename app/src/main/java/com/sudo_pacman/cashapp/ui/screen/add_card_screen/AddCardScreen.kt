//package com.sudo_pacman.cashapp.ui.screen.add_card_screen
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import com.sudo_pacman.cashapp.ui.main_components.CircleShadowComponent
//import com.sudo_pacman.cashapp.ui.theme.arrowLeftImg
//import com.sudo_pacman.cashapp.ui.theme.semiBold25
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddCardScreen() {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                navigationIcon = {
//                    CircleShadowComponent(
//                        modifier = Modifier.padding(start = 12.dp),
//                        size = 44.dp,
//                        onClick = {},
//                        content = {
//                            Image(
//                                painter = painterResource(arrowLeftImg),
//                                contentDescription = "back"
//                            )
//                        }
//                    )
//                },
//                title = {
//                    Text(
//                        "Add Card",
//                        style = semiBold25,
//                        modifier = Modifier.padding(start = 16.dp)
//                    )
//                },
//            )
//        }
//    ) {  innerPadding ->
//        LazyColumn(
//            modifier = Modifier
//                .padding(innerPadding)
//        ) {
//
//        }
//    }
//}