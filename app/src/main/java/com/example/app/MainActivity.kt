package com.example.app

import android.content.ContentValues
import android.graphics.Paint.Align
import android.os.Bundle
import android.provider.BaseColumns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app.data.Todo
import com.example.app.database.FeedReaderDbHelper
import com.example.app.ui.theme.AppTheme
import com.example.app.ui.theme.Gray100
import com.example.app.ui.theme.Gray30
import com.example.app.ui.theme.Gray40
import com.example.app.ui.theme.Gray80
import com.example.app.ui.theme.Green100
import com.example.app.ui.theme.Green40
import com.example.app.ui.theme.White100

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "Todos"
    ) {
        composable(route = "Todos") {
            TodoFragment(navController)
        }
        composable(route = "Recent") {
            RecentFragment()
        }
        composable(route = "Done") {
            DoneFragment()
        }
        composable(route = "Auth") {
            AuthFragment(navController)
        }
        composable(route = "Reg") {
            RegFragment(navController)
        }
    }
}

@Composable
fun TodoFragment(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {
        BottomAppBarFragment(navController)
    }
}

@Composable
fun RecentFragment() {}

@Composable
fun DoneFragment() {}

@Composable
fun AuthFragment(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {
        AuthFormColumn()
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Dont have an account?",
                color = Gray30
            )
            ClickableText(
                text = AnnotatedString("Sign up"),
                onClick = { navController.navigate("Reg") },
                style = TextStyle(
                    color = Green40,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
        }
    }
}

@Composable
fun RegFragment(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {
        RegFormColumn()
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Have an account?",
                color = Gray30
            )
            ClickableText(
                text = AnnotatedString("Sign in"),
                onClick = { navController.navigate("Auth") },
                style = TextStyle(
                    color = Green40,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldSample(placeholder: String) {
    var text = remember { mutableStateOf("") }

    TextField(
        value = text.value,
        shape = RoundedCornerShape(20.dp),
        onValueChange = { newValue -> text.value = newValue},
        placeholder = { Text(text = placeholder) },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Gray40,
            textColor = Green100,
            focusedIndicatorColor =  Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .padding(bottom = 10.dp)
            .background(Gray40)
    )
}

@Composable
fun ButtonSample(text: String) {
    Button(
        onClick = {},
        shape = RoundedCornerShape(20),
        colors = ButtonDefaults.buttonColors(Green100),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            color = White100,
            modifier = Modifier
                .padding(all = 10.dp)
        )
    }
}

@Composable
fun RegFormColumn() {
    Column(
        modifier = Modifier
            .width(280.dp)
            .padding(bottom = 15.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription= "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(50.dp)
                .height(60.dp)
                .align(alignment = Alignment.CenterHorizontally)
                .padding(bottom = 10.dp)
        )
        Text(text = "Sign Up", color = Green100,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(bottom = 15.dp)
        )
        TextFieldSample("Email")
        TextFieldSample("Login")
        TextFieldSample("password")
        TextFieldSample("Confirm password")
        ButtonSample("Sign up")
    }
}

@Composable
fun AuthFormColumn() {
    Column(
        modifier = Modifier
            .width(280.dp)
            .padding(bottom = 15.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription= "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(50.dp)
                .height(60.dp)
                .align(alignment = Alignment.CenterHorizontally)
                .padding(bottom = 10.dp)
        )
        Text(text = "Sign in", color = Green100,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(bottom = 15.dp)
        )
        TextFieldSample("Login")
        TextFieldSample("password")
        ButtonSample("Sign in")
    }
}

@Composable
fun BottomAppBarFragment(navController: NavController) {
    BottomAppBar(
        containerColor = Gray80,
        contentColor = Green100,
        contentPadding = PaddingValues(all = 5.dp),
        windowInsets = WindowInsets.navigationBars,
        actions = {
            IconButton(
                onClick = { },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.todos_menu),
                    contentDescription = "Todos"
                )
            }
            IconButton(
                onClick = { },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.recent_menu),
                    contentDescription = "Recent"
                )
            }
            IconButton(
                onClick = { },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.done_menu),
                    contentDescription = "Done"
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {  },
                containerColor = Gray40,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(
                    painter = painterResource(R.drawable.plus),
                    contentDescription = "Localized description",
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                )
            }
        }
    )
}










