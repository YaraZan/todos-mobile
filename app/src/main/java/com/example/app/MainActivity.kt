package com.example.app

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app.data.TextFieldState
import com.example.app.data.Todo
import com.example.app.data.Variables
import com.example.app.database.SQLiteHelper
import com.example.app.database.SharedPreferencesHelper
import com.example.app.database.SqliteService
import com.example.app.helper.AddFormValidation
import com.example.app.helper.AuthValidation
import com.example.app.helper.RegValidation
import com.example.app.ui.theme.AppTheme
import com.example.app.ui.theme.Gray00
import com.example.app.ui.theme.Gray10
import com.example.app.ui.theme.Gray100
import com.example.app.ui.theme.Gray20
import com.example.app.ui.theme.Gray30
import com.example.app.ui.theme.Gray40
import com.example.app.ui.theme.Gray80
import com.example.app.ui.theme.Green100
import com.example.app.ui.theme.Green40
import com.example.app.ui.theme.White100

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbHelper = SqliteService(this)
        val db = SQLiteHelper(dbHelper)

        val sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val shp = SharedPreferencesHelper(sharedPref)

        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(db, shp)
                }
            }
        }
    }
}

@Composable
fun Navigation(db: SQLiteHelper, shp: SharedPreferencesHelper) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "Auth"
    ) {
        composable(route = "Todos") {
            TodoFragment(navController, db, shp)
        }
        composable(route = "Recent") {
            RecentFragment()
        }
        composable(route = "Done") {
            DoneFragment(navController, db, shp)
        }
        composable(route = "Auth") {
            AuthFragment(navController, db, shp)
        }
        composable(route = "Reg") {
            RegFragment(navController, db, shp)
        }
    }
}

@Composable
fun AuthFragment(navController: NavController, db: SQLiteHelper, shp: SharedPreferencesHelper) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {
        AuthFormColumn(navController, db, shp)
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
fun RegFragment(navController: NavController, db: SQLiteHelper, shp: SharedPreferencesHelper) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {
        RegFormColumn(navController, db, shp)
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
fun TextFieldSample(placeholder: String, textState : TextFieldState = remember { TextFieldState() } ) {

    TextField(
        value = textState.text,
        shape = RoundedCornerShape(20.dp),
        onValueChange = { textState.text = it},
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
fun ButtonSample(text: String, onValidate: () -> Unit) {
    Button(
        onClick = onValidate,
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
fun RegFormColumn(navController: NavController, db: SQLiteHelper, shp: SharedPreferencesHelper) {
    var emailState = remember { TextFieldState() }
    var loginState = remember { TextFieldState() }
    var passwordState = remember { TextFieldState() }
    var confPasswordState = remember { TextFieldState() }

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
        TextFieldSample("Email", emailState)
        TextFieldSample("Login", loginState)
        TextFieldSample("Password", passwordState)
        TextFieldSample("Confirm password", confPasswordState)
        ButtonSample("Sign up", onValidate = {
            if (RegValidation(emailState.text, loginState.text, passwordState.text, confPasswordState.text).validate()) {
                val reg = db.registerUser(emailState.text, loginState.text, passwordState.text)
                if (reg.first) {
                    navController.navigate("Todos")
                    shp.setCurrentUser(reg.second)
                    println(shp.getCurrentUser())
                }
            }
        })
    }
}

@Composable
fun AuthFormColumn(navController: NavController, db: SQLiteHelper, shp: SharedPreferencesHelper) {
    var loginState = remember { TextFieldState() }
    var passwordState = remember { TextFieldState() }

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
        TextFieldSample("Login", loginState)
        TextFieldSample("password", passwordState)
        ButtonSample("Sign in", onValidate = {
            if (AuthValidation(loginState.text, passwordState.text).validate()) {
                val auth = db.authorizeUser(loginState.text, passwordState.text)
                if (auth.first) {
                    navController.navigate("Todos")
                    shp.setCurrentUser(auth.second)
                }
            }
        })
    }
}

@Composable
fun TodoFragment(navController: NavController, db: SQLiteHelper, shp: SharedPreferencesHelper) {
    val paddingTopModifier = Modifier.padding(top = 40.dp)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {
        Row(modifier = paddingTopModifier) {
            Text(
                text = "My todos",
                style = TextStyle(
                    color = Green40,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(600)
                )
            )
        }
        Row(modifier = paddingTopModifier) {
            TodosContainerLayout(db.getUndoneTodos(shp.getCurrentUser()), navController, db)
        }
    }
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.fillMaxHeight()) {
        BottomAppBarLayout(navController, db, shp)
    }
}

@Composable
fun RecentFragment() {}

@Composable
fun DoneFragment(navController: NavController, db: SQLiteHelper, shp: SharedPreferencesHelper) {
    val paddingTopModifier = Modifier.padding(top = 40.dp)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {
        Row(modifier = paddingTopModifier) {
            Text(
                text = "Done todos",
                style = TextStyle(
                    color = Green40,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(600)
                )
            )
        }
        Row(modifier = paddingTopModifier) {
            DoneTodosContainerLayout(db.getDoneTodos(shp.getCurrentUser()), navController, db)
        }
    }
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.fillMaxHeight()) {
        BottomAppBarLayout(navController, db, shp)
    }
}

@Composable
fun BottomAppBarLayout(navController: NavController, db: SQLiteHelper, shp: SharedPreferencesHelper) {
    val showDialog =  remember { mutableStateOf(false) }

    if(showDialog.value)
        AddTodoDialog(setShowDialog = {
            showDialog.value = it
        }, navController, db, shp)

    BottomAppBar(
        containerColor = Gray80
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(all = 5.dp)
        ) {
            IconButton(
                onClick = { navController.navigate("Auth") },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.logout),
                    contentDescription = "Recent",
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )
            }
            Spacer(Modifier.weight(1f, true))
            IconButton(
                onClick = { navController.navigate("Todos") },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.todos_menu),
                    contentDescription = "Todos",
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )
            }
            Spacer(Modifier.weight(1f, true))
            IconButton(
                onClick = { navController.navigate("Done") },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.done_menu),
                    contentDescription = "Done",
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )
            }
            Spacer(Modifier.weight(1f, true))
            FloatingActionButton(
                onClick = { showDialog.value = true },
                containerColor = Gray40,
                contentColor = Green40
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
    }
}

@Composable
fun TodosContainerLayout(todos: ArrayList<Todo>, navController: NavController, db: SQLiteHelper) {
    val lazyListState: LazyListState = rememberLazyListState()
    Column(modifier = Modifier.width(350.dp).height(490.dp)) {
        LazyColumn(modifier = Modifier.fillMaxWidth(), state = lazyListState) {
            items (todos) { todo ->
                TodoCardLayout(todo.id, todo.name, todo.descr, todo.deadline, navController, db)
            }
        }
    }
}

@Composable
fun DoneTodosContainerLayout(todos: ArrayList<Todo>, navController: NavController, db: SQLiteHelper) {
    val lazyListState: LazyListState = rememberLazyListState()
    Column(modifier = Modifier.width(350.dp).height(490.dp)) {
        LazyColumn(modifier = Modifier.fillMaxWidth(), state = lazyListState) {
            items (todos) { todo ->
                DoneTodoCardLayout(todo.id, todo.name, todo.descr, todo.deadline, navController, db)
            }
        }
    }
}

@Composable
fun TodoCardLayout(id: Int, name: String, descr: String, deadlines: String, navController: NavController, db: SQLiteHelper) {
    val paddingModifier  = Modifier.padding(20.dp)
    val paddingVertical = Modifier.padding(vertical = 15.dp)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Gray20,
        ),
    ) {
        Column(modifier = paddingModifier) {
            Text(
                text = name,
                style = TextStyle(
                    color = White100,
                    fontSize = 24.sp,
                    fontWeight = FontWeight(400)
                )
            )
            Text(
                text = descr,
                style = TextStyle(
                    color = Gray10,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400)
                ),
                modifier = paddingVertical
            )
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Icon(
                    painter = painterResource(R.drawable.deadlines),
                    contentDescription = "Todos",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
                Spacer(Modifier.padding(horizontal = 5.dp))
                Text(
                    text = "Deadline",
                    style = TextStyle(
                        color = Gray00,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(400)
                    )
                )
                Spacer(Modifier.weight(1f, true))
                Text(
                    text = deadlines,
                    style = TextStyle(
                        color = Green100,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(400)
                    )
                )
            }
            TodoButtonWrapper(id, navController, db)
        }
    }
}

@Composable
fun DoneTodoCardLayout(id: Int, name: String, descr: String, deadlines: String, navController: NavController, db: SQLiteHelper) {
    val paddingModifier  = Modifier.padding(20.dp)
    val paddingVertical = Modifier.padding(vertical = 15.dp)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Gray20,
        ),
    ) {
        Column(modifier = paddingModifier) {
            Text(
                text = name,
                style = TextStyle(
                    color = White100,
                    fontSize = 24.sp,
                    fontWeight = FontWeight(400)
                )
            )
            Text(
                text = descr,
                style = TextStyle(
                    color = Gray10,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400)
                ),
                modifier = paddingVertical
            )
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Icon(
                    painter = painterResource(R.drawable.deadlines),
                    contentDescription = "Todos",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
                Spacer(Modifier.padding(horizontal = 5.dp))
                Text(
                    text = "Deadline",
                    style = TextStyle(
                        color = Gray00,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(400)
                    )
                )
                Spacer(Modifier.weight(1f, true))
                Text(
                    text = deadlines,
                    style = TextStyle(
                        color = Green100,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(400)
                    )
                )
            }
            Button(
                onClick = {
                    db.deleteTodo(id)
                    navController.navigate("Done")
                          },
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(Green40),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            ) {
                Text(
                    text = "Delete",
                    color = Green100,
                    modifier = Modifier
                        .padding(all = 5.dp)
                )
            }
        }
    }
}

@Composable
fun TodoButtonWrapper(id: Int, navController: NavController, db: SQLiteHelper) {
    Row(modifier = Modifier.padding(top = 15.dp)) {
        Button(
            onClick = {
                db.deleteTodo(id)
                navController.navigate("Todos")
            },
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(Green40),
            modifier = Modifier.width(150.dp)
        ) {
            Text(
                text = "Delete",
                color = Green100,
                modifier = Modifier
                    .padding(all = 5.dp)
            )
        }
        Spacer(Modifier.weight(1f, true))
        Button(
            onClick = {
                db.doneTodo(id)
                navController.navigate("Todos")
            },
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(Green100),
            modifier = Modifier.width(150.dp)
        ) {
            Text(
                text = "Done",
                color = White100,
                modifier = Modifier
                    .padding(all = 5.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoDialog(setShowDialog: (Boolean) -> Unit, navController: NavController, db: SQLiteHelper, shp: SharedPreferencesHelper) {
    var nameState = remember { TextFieldState() }
    var descrState = remember { TextFieldState() }
    var deadlineState = remember { TextFieldState() }


    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            color = Gray00
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "New todo",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Green40
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    TextFieldSample("Name", nameState)
                    TextFieldSample("Description", descrState)
                    TextFieldSample("Deadline", deadlineState)

                    Button(
                        onClick = {
                            if (AddFormValidation(nameState.text, descrState.text, deadlineState.text).validation()) {
                                if (db.createTodo(shp.getCurrentUser(),nameState.text, descrState.text, deadlineState.text)) {
                                    setShowDialog(false)
                                    navController.navigate("Todos")
                                }
                            }
                        },
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(Green100),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Add",
                            color = White100,
                            modifier = Modifier
                                .padding(all = 5.dp)
                        )
                    }
                }
            }
        }
    }
}
