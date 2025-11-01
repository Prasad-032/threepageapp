package com.example.threepageapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.threepageapp.ui.theme.ThreePageAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThreePageAppTheme {
                AppNavigator()
            }
        }
    }
}

@Composable
fun AppNavigator() {
    var showLanding by remember { mutableStateOf(true) }

    if (showLanding) {
        LandingScreen(onStartClicked = { showLanding = false })
    } else {
        MainScreen()
    }
}

@Composable
fun LandingScreen(onStartClicked: () -> Unit) {
    val lemonFont = FontFamily(Font(R.font.lemon)) // Load Lemon font

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 🖼️ Background Image
        Image(
            painter = painterResource(id = R.drawable.onepiecebg),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 🧭 Overlay Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // ⚓ Title with red fill + white stroke
            Text(
                text = "🏴‍☠️ ONE PIECE WORLD 🏴‍☠️",
                textAlign = TextAlign.Center, // ✅ Center the text horizontally
                fontSize = 36.sp,
                fontFamily = lemonFont,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFFFFF),
                style = LocalTextStyle.current.copy(
                    drawStyle = Stroke(
                        width = 4f,
                        join = StrokeJoin.Round,
                        miter = 10f
                    )
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            // 🔴 Custom Red Button
            Button(
                onClick = onStartClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEC1C20),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(55.dp)
            ) {
                Text(
                    text = "Let's Go",
                    fontSize = 20.sp,
                    fontFamily = lemonFont,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}


@Composable
fun MainScreen() {
    var selectedScreen by remember { mutableStateOf("Home") }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(selectedScreen) { selectedScreen = it }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (selectedScreen) {
                "Home" -> HomeScreen(onMoviesClick = { selectedScreen = "Movies" })
                "Movies" -> MoviesPage()
                "Episodes" -> EpisodesScreen() // ✅ Added Movies page here
                "About" -> AboutScreen()
                "Contact" -> ContactScreen()
            }
        }
    }
}


@Composable
fun HomeScreen(onMoviesClick: () -> Unit) {
    val context = LocalContext.current

    // Background image
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.onepiecebg), // your background image
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "🏴‍☠️ ONE PIECE WORLD 🏴‍☠️",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.lemon)),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )

            // Main Card - Luffy
            AnimatedCard(
                imageRes = R.drawable.luffy,
                title = "LUFFY: KING OF PIRATES",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Popular Episodes Section
            Text(
                text = "⭐ POPULAR EPISODES ⭐",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Episode Cards - Grid-like layout
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                AnimatedCard(
                    imageRes = R.drawable.ep851,
                    title = "EP 851",
                    onClick = {
                        openWebLink(context, "https://aniwatch.com.ro/one-piece-episode-851/")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp)
                        .padding(4.dp)
                )
                AnimatedCard(
                    imageRes = R.drawable.ep854,
                    title = "EP 854",
                    onClick = {
                        openWebLink(context, "https://aniwatch.com.ro/one-piece-episode-854/")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp)
                        .padding(4.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                AnimatedCard(
                    imageRes = R.drawable.ep1046,
                    title = "EP 1046",
                    onClick = {
                        openWebLink(context, "https://aniwatch.com.ro/one-piece-episode-1046/")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp)
                        .padding(4.dp)
                )
                AnimatedCard(
                    imageRes = R.drawable.ep1049,
                    title = "EP 1049",
                    onClick = {
                        openWebLink(context, "https://aniwatch.com.ro/one-piece-episode-1049/")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp)
                        .padding(4.dp)
                )
            }

            // Movies card
            AnimatedCard(
                imageRes = R.drawable.movies,
                title = "CLICK TO LATEST MOVIES",
                onClick = { onMoviesClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(top = 8.dp)
            )

        }
    }
}

@Composable
fun AnimatedCard(
    imageRes: Int,
    title: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    var clicked by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (clicked) 1.05f else 1f, label = "")

    // 1. Get a coroutine scope that can be used in regular callbacks
    val scope = rememberCoroutineScope()

    Card(
        modifier = modifier
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .clickable {
                clicked = true
                onClick?.invoke()
                // 2. Launch a coroutine using the scope
                scope.launch {
                    delay(150)
                    clicked = false
                }
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Centered text with a semi-transparent background
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center, // ✅ Make text centered
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color(0x99000000)) // Darker background for readability
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}


fun openWebLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    context.startActivity(intent)
}


@Composable
fun MoviesPage() {
    LocalContext.current
    val lemonFont = FontFamily(Font(R.font.lemon)) // Make sure you have lemon.ttf in res/font

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7CFCF))
            .verticalScroll(rememberScrollState())
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🏴‍☠️ ONE PIECE WORLD 🏴‍☠️",
            fontSize = 24.sp,
            fontFamily = lemonFont,
            color = Color(0xFF2D0A0A),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )

        // Movie Cards
        MovieCard(
            imageRes = R.drawable.onepiece_stampede,
            title = "One Piece: Stampede (2020)",
            url = "https://kwik.bunniescdn.online/f/2VNvcBQU1FAE"
        )

        MovieCard(
            imageRes = R.drawable.onepiece_red,
            title = "One Piece Film: Red (2022)",
            url = "https://kwik.bunniescdn.online/f/nEp583IeM1ZN"
        )

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun MovieCard(imageRes: Int, title: String, url: String) {
    val context = LocalContext.current
    var clicked by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (clicked) 1.05f else 1f, label = "")

    // Coroutine scope for click animation delay
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .clickable {
                clicked = true
                scope.launch {
                    delay(150)
                    clicked = false
                }
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE5E5)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(130.dp)
                    .height(180.dp)
                    .padding(end = 12.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Left,
                    fontFamily = FontFamily(Font(R.font.lemon)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D0A0A),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEC1C20))
                ) {
                    Text(
                        text = "click to view",
                        fontFamily = FontFamily(Font(R.font.lemon)),
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}



@Composable
fun EpisodesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE6E6))
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        Text(
            text = "🏴‍☠️ ONE PIECE WORLD 🏴‍☠️",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.lemon)),
            fontWeight = FontWeight.Bold,
            color = Color(0xFFAE2727),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )

        // --- Episode Cards ---
        EpisodeCard(
            imageRes = R.drawable.episodes_0_500,
            title = "One Piece: Episodes 0–500",
            url = "https://example.com/episodes0to500"
        )

        EpisodeCard(
            imageRes = R.drawable.episodes_501_1000,
            title = "One Piece: Episodes 501–1000",
            url = "https://example.com/episodes501to1000"
        )

        EpisodeCard(
            imageRes = R.drawable.episodes_1001_1142,
            title = "One Piece: Episodes 1001–1142",
            url = "https://example.com/episodes1001to1142"
        )
    }
}


@Composable
fun EpisodeCard(imageRes: Int, title: String, url: String) {
    val context = LocalContext.current
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.05f else 1f,
        animationSpec = tween(durationMillis = 200)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                        context.startActivity(intent)
                    }
                )
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFCCCC)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(130.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.lemon)),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2B2B2B)
                )

                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEC1C20)),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("click to view", fontFamily = FontFamily(Font(R.font.lemon)), color = Color.White)
                }
            }
        }
    }
}




@Composable
fun AboutScreen() {
    val lemonFont = FontFamily(Font(R.font.lemon)) // 🟢 your custom font
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE0E0)) // light peach/pink tone
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 🔹 Top header bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        listOf(Color(0xFFB25A2B), Color(0xFF80411E))
                    )
                )
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🏴‍☠️ ONE PIECE WORLD 🏴‍☠️",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = lemonFont
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 Eiichiro Oda image
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .width(180.dp)
                .height(220.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.eiichiro_oda), // 🔸 your drawable image
                contentDescription = "Eiichiro Oda",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 Description text
        Text(
            text = "Eiichiro Oda, the creative genius behind One Piece, revolutionized the world of manga with his epic tale of pirates, dreams, and freedom. " +
                    "Since its debut in 1997, One Piece has become one of the best-selling manga series of all time, captivating millions with its rich world-building and unforgettable characters. " +
                    "The anime adaptation boasts over 1,000 episodes, each packed with adventure, emotion, and jaw-dropping twists. " +
                    "Oda’s storytelling has also expanded into blockbuster movies, each adding new layers to the One Piece universe. " +
                    "With its blend of humor, heart, and high-seas action, One Piece is more than a story—it’s a global phenomenon. " +
                    "Ready to set sail with the Straw Hat crew?",
            fontSize = 16.sp,
            color = Color.Black,
            lineHeight = 22.sp,
            textAlign = TextAlign.Center,
            fontFamily = lemonFont,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun ContactScreen() {
    val lemonFont = FontFamily(Font(R.font.lemon))
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var showPopup by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7CFCF))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "🏴‍☠️ ONE PIECE WORLD 🏴‍☠️",
                fontFamily = lemonFont,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color(0xFF2D0A0A),
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )

            Text(
                text = "CONTACT US",
                fontFamily = lemonFont,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Input fields
            ContactTextField(
                value = name,
                placeholder = "Your Name",
                onValueChange = { name = it },
                minHeight = 55.dp,
                singleLine = true
            )

            ContactTextField(
                value = email,
                placeholder = "Your Mail",
                onValueChange = { email = it },
                minHeight = 55.dp,
                singleLine = true
            )

            ContactTextField(
                value = message,
                placeholder = "Your Message",
                onValueChange = { message = it },
                minHeight = 160.dp,
                singleLine = false
            )

            Button(
                onClick = {
                    name = ""
                    email = ""
                    message = ""
                    scope.launch {
                        showPopup = true
                        delay(2000)
                        showPopup = false
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEC1C20),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(55.dp)
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = "Submit",
                    fontSize = 20.sp,
                    fontFamily = lemonFont,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Footer motivational text
            val footerText = listOf(
                "🙏 Thank you for your support",
                "💖 Your encouragement means the world to us.",
                "💫 Together, we’ll keep building something amazing.",
                "⭐ We’re grateful to have you with us on this journey.",
                "📖 Feel free to reach out anytime — we’re always here for you."
            )

            footerText.forEach {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = Color(0xFF2D0A0A),
                    fontFamily = lemonFont,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }

        if (showPopup) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .pointerInput(Unit) { detectTapGestures { } },
                contentAlignment = Alignment.Center
            ) {
                SentPopup()
            }
        }
    }
}

@Composable
fun SentPopup() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2D0A0A)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sent ",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.lemon))
            )
            Text(text = "✔️", fontSize = 20.sp)
        }
    }
}

@Composable
fun ContactTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    minHeight: Dp = 55.dp,
    singleLine: Boolean = true
) {
    val lemon = FontFamily(Font(R.font.lemon))

    androidx.compose.material3.OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = Color(0xFFB7AFAF),
                fontFamily = lemon,
                fontSize = 16.sp
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = minHeight)
            .padding(vertical = 8.dp),
        textStyle = androidx.compose.ui.text.TextStyle(
            color = Color(0xFF2D0A0A),
            fontFamily = lemon,
            fontSize = 16.sp
        ),
        singleLine = singleLine,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color(0xFFFFEAEA),
            unfocusedContainerColor = Color(0xFFFFEAEA),
            cursorColor = Color(0xFF2D0A0A),
            unfocusedPlaceholderColor = Color(0xFFB7AFAF),
            focusedPlaceholderColor = Color(0xFFB7AFAF),
            focusedTextColor = Color(0xFF2D0A0A),
            unfocusedTextColor = Color(0xFF2D0A0A)
        )
    )
}

@Composable
fun BottomNavigationBar(selected: String, onSelect: (String) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            selected = selected == "Home",
            onClick = { onSelect("Home") },
            label = { Text("Home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
        )
        NavigationBarItem(
            selected = selected == "Movies",
            onClick = { onSelect("Movies") },
            label = { Text("🎬 Movies") },
            icon = { Text("🍿", fontSize = 20.sp) }
        )


        NavigationBarItem(
            selected = selected == "Episodes",
            onClick = { onSelect("Episodes") },
            label = { Text("Episodes") },
            icon = { Text("📺", fontSize = 22.sp) }
        )


        NavigationBarItem(
            selected = selected == "About",
            onClick = { onSelect("About") },
            label = { Text("About") },
            icon = { Icon(Icons.Default.Info, contentDescription = "About") }
        )
        NavigationBarItem(
            selected = selected == "Contact",
            onClick = { onSelect("Contact") },
            label = { Text("Contact") },
            icon = { Icon(Icons.Default.Phone, contentDescription = "Contact") }
        )
    }
}
