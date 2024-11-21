package com.poptato.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.poptato.design_system.Gray00
import com.poptato.design_system.Gray100
import com.poptato.design_system.PoptatoTypo
import com.poptato.domain.model.response.category.CategoryItemModel

@Composable
fun CategoryBottomSheet(
    categoryList: List<CategoryItemModel> = emptyList()
) {
    CategoryListBottomSheetContent(
        categoryList = categoryList
    )
}

@Composable
fun CategoryListBottomSheetContent(
    categoryList: List<CategoryItemModel> = emptyList(),
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(392.dp)
            .background(Gray100)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(264.dp)
                .padding(top = 24.dp, bottom = 16.dp)
        ) {
            itemsIndexed(categoryList, key = { _, item -> item.categoryId }) { index, item ->
                CategoryBottomSheetItem(
                    iconImg = item.categoryImgUrl,
                    categoryName = item.categoryName
                )
            }
        }
    }
}

@Composable
fun CategoryBottomSheetItem(
    iconImg: String = "",
    categoryName: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 12.dp)
            .padding(horizontal = 24.dp),
    ) {
        AsyncImage(
            model = iconImg,
            contentDescription = "category icon",
            modifier = Modifier
                .size(24.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = categoryName,
            style = PoptatoTypo.mdMedium,
            color = Gray00,
            modifier = Modifier
                .padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCategoryListBottomSheet() {

    val categoryList = listOf(
        CategoryItemModel(1, 1, "테스트", ""),
        CategoryItemModel(1, 1, "테스트", ""),
        CategoryItemModel(1, 1, "테스트", ""),
        CategoryItemModel(1, 1, "테스트", ""),
        CategoryItemModel(1, 1, "테스트", ""),
    )
    CategoryListBottomSheetContent(categoryList = categoryList)
}