package uz.gita.dictionaryuzen.presentation.adapter

import android.database.Cursor
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qtalk.recyclerviewfastscroller.RecyclerViewFastScroller
import uz.gita.dictionaryuzen.data.model.ItemData
import uz.gita.dictionaryuzen.databinding.ScreenItemBinding
import uz.gita.dictionaryuzen.utils.Position
import uz.gita.dictionaryuzen.utils.paintResult
import java.util.*

class DictionaryAdapter : RecyclerView.Adapter<DictionaryAdapter.ViewHolder>(), RecyclerViewFastScroller.OnPopupTextUpdate {
    private var cursor: Cursor? = null
     var query: String? = null
    var speakerVisibility = false
    private var onItemClickListener: ((data: ItemData) -> Unit)? = null

    fun setonItemClickListener(block: ((data: ItemData) -> Unit)) {
        onItemClickListener = block
    }


    fun submitItem(cursor_: Cursor) {
        cursor = cursor_
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ScreenItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            val speech = TextToSpeech(binding.root.context,TextToSpeech.OnInitListener {

            })

            speech.setLanguage(Locale.UK)
            binding.speaker.setOnClickListener {
               val data = cursor?.let {
                   it.moveToPosition(absoluteAdapterPosition)
              speech.speak(it.getString(1),TextToSpeech.QUEUE_FLUSH,null)
               }
            }
            //ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            binding.root.setOnClickListener {
                cursor?.let {
                    it.moveToPosition(absoluteAdapterPosition)
                    onItemClickListener?.invoke(
                        ItemData(
                            it.getInt(0),
                            it.getString(1),
                            it.getString(2),
                            it.getString(3),
                            it.getString(4),
                            it.getInt(5)
                        )
                    )

                }
            }
        }

        fun binding() {
            cursor?.let { cursor ->
                cursor.moveToPosition(absoluteAdapterPosition)
                Log.d("AAA","${cursor.getInt(5)}")
                if (cursor.getInt(5) == 1){
                    binding.star.visibility = View.VISIBLE
                }else{
                    binding.star.visibility = View.INVISIBLE
                }
                if (Position.POS == 0) {
                    binding.text.text = when (query) {
                        null -> cursor.getString(1)
                        else -> query?.let { query -> cursor.getString(1).paintResult(query) }
                    }
                } else {
                    Log.d("AAA","${cursor.getString(4)}")
                    binding.text.text = when (query) {
                        null -> cursor.getString(4)
                        else -> query?.let { query -> cursor.getString(4).paintResult(query) }
                    }
                }

                if (Position.POS == 0) {
                    binding.speaker.visibility = View.VISIBLE
                } else {
                    binding.speaker.visibility = View.GONE
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ScreenItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding()
    }

    override fun getItemCount(): Int = cursor?.count ?: 0


    override fun onChange(position: Int): CharSequence {
        cursor?.let {
            it.moveToPosition(position)
            if (Position.POS == 0) {
                return it.getString(1).first().toUpperCase().toString()
            }else{
                return it.getString(4).first().toUpperCase().toString()
            }
        }
        return ""
    }
}