package com.svmsoftware.flashvocab.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.svmsoftware.flashvocab.core.design_system.theme.DarkSlateBlue
import com.svmsoftware.flashvocab.core.design_system.theme.MidnightBlack
import com.svmsoftware.flashvocab.core.design_system.theme.SlateGray

@Composable
fun BottomNavigationBar(navController: NavController, modifier: Modifier = Modifier) {

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar(
        containerColor = DarkSlateBlue,
        modifier = modifier
            .wrapContentSize()
            .clip(
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
            )
    ) {
        BottomNavItems.forEachIndexed { index, item ->
            val color = if (selectedItemIndex == index) Color.White else SlateGray

            NavigationBarItem(selected = false, onClick = {
                selectedItemIndex = index
                navController.navigate(item.route)
            }, label = {
                Text(
                    text = stringResource(id = item.label), color = color,
                    modifier = Modifier.padding(top = 32.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }, alwaysShowLabel = true, icon = {
                Icon(
                    tint = color,
                    modifier = Modifier
                        .size(32.dp),
                    imageVector = item.icon,
                    contentDescription = stringResource(id = item.label)
                )
            })
        }
    }
}
