package com.example.giphyapp.utils

import android.app.AlertDialog
import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

object ViewUtils {

    fun showDialogForAddToBlockList(
        context: Context,
        title: String?,
        removalProcess: () -> Unit
    ) {
        val alert = AlertDialog.Builder(context)
        alert.apply {
            setTitle("Delete \"${title}?\"")
            setPositiveButton("Delete") { dialog, _ ->
                removalProcess()
                dialog.dismiss()
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
        }
        alert.show()
    }

}