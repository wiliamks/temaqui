package com.wiliamks.temaqui.commons.viewmodel

sealed class ViewState {
    object Success : ViewState()
    object Error : ViewState()
    object Loading : ViewState()
}
