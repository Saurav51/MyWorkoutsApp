package com.example.myworkouts

import android.R.layout.simple_list_item_multiple_choice
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.text.isNullOrBlank

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializing the array lists and the adapter
        var itemlist = arrayListOf<String>()
        var adapter =ArrayAdapter<String>(this,
                simple_list_item_multiple_choice
                , itemlist)

        var listType: String? ="Workout List"
        textView.text =" Make your $listType Here"

        // Adding the items to the list when the add button is pressed
        fun adding2(x: String) {
            if(x.isNullOrBlank())
            {
                Toast.makeText(this, "Add a workout!", Toast.LENGTH_SHORT).show()
            }
            else
            {
                itemlist.add(x)
                listView.adapter = adapter
                adapter.notifyDataSetChanged()
                // This is because every time when you add the item the input space or the edit text space will be cleared
                editText.text.clear()

                var num = itemlist.count()
                if (num < 3) {
                    textView.text = "Just $num more to go! "
                } else {
                    textView.text = "$num more to go! "
                }
            }

        }

        fun adding1() {
            var addString = editText.text.toString()
            adding2(addString)
        }

        add.setOnClickListener{
            GlobalScope.launch{
                delay(100L)
                add.setBackgroundColor(Color.WHITE)
            }
            add.setBackgroundColor(Color.TRANSPARENT)
            adding1()
        }

        // Clearing all the items in the list when the clear button is pressed
        fun clearing() {
            itemlist.clear()
            adapter.notifyDataSetChanged()


            textView.text = " Make another  $listType "

        }
        clear.setOnClickListener {
            GlobalScope.launch {
                delay(100L)
                clear.setBackgroundColor(Color.WHITE)
            }
            clear.setBackgroundColor(Color.TRANSPARENT)
            clearing()
        }
        // Adding the toast message to the list when an item on the list is pressed
        listView.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, "You Selected the item --> "+itemlist.get(i), Toast.LENGTH_SHORT).show()
        }

        // Selecting and Deleting the items from the list when the delete button is pressed
        fun deleting2(position: SparseBooleanArray, count: Int){
            var item = { x : Int -> x/2 }
            var item1=item(count)

            while (item1 >= 0) {
                if (position.get(item1)) {
                    adapter.remove(itemlist.get(item1))
                }
                item1--
            }
            position.clear()

            adapter.notifyDataSetChanged()


            GlobalScope.launch {
                delay(2000L)
                var num = itemlist.count()
                if (num == 0) {
                    textView.text = " Make another $listType! "
                } else if (num < 3) {
                    textView.text = "Just $num more to go! "
                } else {
                    textView.text = "$num more to go! "
                }
            }

            textView.text = "1 DOWN! LET'S GO!"


        }

        fun deleting1() {

            var position: SparseBooleanArray = listView.checkedItemPositions
            var count = listView.count
            deleting2(position, count)
        }

        delete.setOnClickListener{
            GlobalScope.launch{
                delay(100L)
                delete.setBackgroundColor(Color.WHITE)
            }
            delete.setBackgroundColor(Color.TRANSPARENT)
            deleting1()
        }
    }
}

