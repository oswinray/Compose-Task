package com.example.samplecompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnDemo()

        }
    }
}

@Preview
@Composable
fun Test() {
    LazyColumnDemo()
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LazyColumnDemo() {
    var userNames by remember {
        mutableStateOf(
            listOf(
                "Alice", "Bob", "Charlie", "David", "Eve",
                "Frank", "Grace", "Hannah", "Isaac", "Julia"
            )
        )
    }
    var roles by remember {
        mutableStateOf(
            listOf(
                "Developer", "Designer", "Manager", "Engineer", "Analyst",
                "Architect", "Tester", "Artist", "Writer", "Administrator"
            )
        )
    }
    val selectedIndices = remember { mutableStateListOf<Int>() }

    val onDeleteClicked: () -> Unit = {
        val newSelectedIndices = selectedIndices.toList()
        userNames = userNames.filterIndexed { index, _ -> index !in selectedIndices }
        roles = roles.filterIndexed { index, _ -> index !in selectedIndices }
        selectedIndices.clear()

        // Re-add the items that should still be selected
        newSelectedIndices.forEach {
            if (it in userNames.indices) {
                selectedIndices.add(it)
            }
        }
    }

    Surface(
        color = Color.Red
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "User List") },
                    actions = {
                        IconButton(
                            onClick = onDeleteClicked
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }
                    }
                )
            }
        ) {
            Box(
                modifier = Modifier.padding(top = AppBarHeight)
            ) {
                LazyColumn {
                    itemsIndexed(userNames) { index, name ->
                        val isSelected = index in selectedIndices

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .clickable {
                                    if (isSelected) {
                                        selectedIndices.remove(index)
                                    } else {
                                        selectedIndices.add(index)
                                    }
                                }
                                .background(if (isSelected) Color.Red else Color.Transparent)
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.baseline_person),
                                    contentDescription = "User Image",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(shape = MaterialTheme.shapes.medium),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text(
                                        text = name,
                                        fontSize = 20.sp,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = "Role: ${roles[index]}",
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private val AppBarHeight = 56.dp


@Composable
fun SingleSelectionLazyColumn(items: List<String>) {
    var selectedIndex by remember { mutableStateOf(-1) }

    LazyColumn {
        itemsIndexed(items) { index, item ->
            val isSelected = index == selectedIndex

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { selectedIndex = index }
                    .background(if (isSelected) Color.LightGray else Color.Transparent)) {
                Text(
                    text = item,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun SingleSelectionDemo() {
    val items = remember {
        listOf(
            "Item 1", "Item 2", "Item 3", "Item 4", "Item 5",
            "Item 6", "Item 7", "Item 8", "Item 9", "Item 10"
        )
    }

    Surface(color = Color.White) {
        SingleSelectionLazyColumn(items = items)
    }
}
