package com.poptato.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poptato.design_system.Gray50
import com.poptato.design_system.Gray90
import com.poptato.design_system.PoptatoTypo
import com.poptato.design_system.R
import com.poptato.design_system.REPEAT

@Composable
fun RepeatItem() {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(4.dp))
            .background(Gray90),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_refresh),
            contentDescription = "repeat icon",
            modifier = Modifier
                .padding(start = 4.dp)
                .padding(vertical = 4.dp)
                .size(12.dp),
            tint = Gray50
        )

        Text(
            text = REPEAT,
            style = PoptatoTypo.xsMedium,
            color = Gray50,
            modifier = Modifier
                .padding(start = 2.dp, end = 4.dp),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewRepeat() {
    RepeatItem()
}