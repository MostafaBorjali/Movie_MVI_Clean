@file:Suppress("unused")

package com.borjali.presentation.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.borjali.domain.utils.MessageType
import com.borjali.presentation.ui.component.SnackBarMessage

fun Fragment.navigate(fragment: Int, arg: Bundle? = null) {
    if (arg != null) {
        findNavController().navigate(fragment, arg)
    } else {
        findNavController().navigate(fragment)
    }
}

fun Fragment.navigate(direction: NavDirections) {
    findNavController().navigate(direction)
}

fun Fragment.back() {
    findNavController().popBackStack()
}

fun Fragment.snackMessage(
    message: String,
    messageType: MessageType
) {
    view?.let {
        SnackBarMessage().show(
            context = context,
            message = message,
            messageType = messageType,
            root = it
        )
    }
}

