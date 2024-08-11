package com.example.aisplitwise

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.aisplitwise.ui.theme.AISplitwiseTheme
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random

@AndroidEntryPoint
class SplitWiseActivity : AppCompatActivity(), DefaultHardwareBackBtnHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "database-name"
                ).build()
                val userDao = db.userDao()
                var users: List<User> = emptyList()
                val rand = Random.Default
                LaunchedEffect(key1 = Unit) {
                    withContext(Dispatchers.IO) {
                        users = userDao.getAll()

                        delay(1000)
                        userDao.insertAll(
                            User(
                                uid = rand.nextInt(),
                                firstName = "Atharva",
                                lastName = "Gorule"
                            )
                        )
                    }

                }

                NavHostInitializer(navController)
            }

        }
    }

    override fun invokeDefaultOnBackPressed() {
        TODO("Not yet implemented")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AISplitwiseTheme {
        Greeting("Android")
    }
}