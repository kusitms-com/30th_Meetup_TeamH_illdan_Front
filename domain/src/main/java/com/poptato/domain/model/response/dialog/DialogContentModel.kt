package com.poptato.domain.model.response.dialog

import com.poptato.domain.model.enums.DialogType

data class DialogContentModel (
    val dialogType: DialogType = DialogType.OneBtn,
    val titleText: String = "",
    val btnText: String = "",
    val positiveBtnText: String = "",
    val cancelBtnText: String = ""
)