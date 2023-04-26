package dave.devs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dave.devs.viewmodel.MainViewModel
import dave.devs.ui.theme.SearchField_With_ComposeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchField_With_ComposeTheme {
                val viewModel = viewModel<MainViewModel>()
                val searchText by viewModel.searchText.collectAsState()
                val isSearching by viewModel.isSearching.collectAsState()
                val countries by viewModel.countries.collectAsState()
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    TextField(
                        value = searchText,
                        onValueChange = viewModel::searchCountries,
                        Modifier.fillMaxWidth(),
                        placeholder = { Text(text = "Type Keyword...")}
                    )
                    Spacer(Modifier.height(16.dp))
                    if (isSearching) {
                        Box(Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                Modifier.align(Alignment.Center)
                            )
                        }
                    } else {
                        LazyColumn(
                            Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            items(countries) { countries->
                                Text(
                                    text = "${countries.name} ${(countries.capital)}",
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}