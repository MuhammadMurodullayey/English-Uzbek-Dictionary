package uz.gita.dictionaryuzen.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan

fun String.paintResult(query: String): Spannable {
    var start = this.indexOf(query)
    var end = start + query.length
    if (this.equals(query, true)) {
        start = 0
        end = this.length
    }
    val spannable = SpannableString(this)
    spannable.setSpan(
        ForegroundColorSpan(Color.BLUE),
        start, end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannable
}
class Position{
    companion object{
    var POS = 0
    }
}
