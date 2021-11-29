package com.example.numbersgameapp


import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var clRoot: ConstraintLayout
    private lateinit var guessEditText: EditText
    private lateinit var guessButton: Button
    private lateinit var answers: ArrayList<String>
    private lateinit var tvPrompt: TextView

    private var answer = 0
    private var tries = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        answer = Random.nextInt(11)

        clRoot = findViewById(R.id.main)
        answers = ArrayList()

        tvPrompt = findViewById(R.id.tvPrompt)

        rvMain.adapter = RecyclerViewAdapter( answers)
        rvMain.layoutManager = LinearLayoutManager(this)

        guessEditText = findViewById(R.id.etMessage)
        guessButton = findViewById(R.id.btSubmit)

        guessButton.setOnClickListener { addMessage() }
    }

    private fun addMessage(){
        val num = guessEditText.text.toString()

        //for input just number
        try {
            if(num.isNotEmpty()){
                if(tries>0){
                    if(num.toInt() == answer){
                        disableEntry()
                        showAlertDialog("Wow You win!\nDo you want Play again?")
                    }else{
                        tries-=1
                        answers.add("You Answer $num")
                        answers.add("You have $tries Tries left")
                    }
                    if(tries==0){

                        answers.add("Sorry You lose - The correct answer is $answer")
                        answers.add("Game Over")
                        showAlertDialog("You lose...\nThe correct answer is $answer\n.Do you want Play again?")
                        disableEntry()
                    }
                }
                guessEditText.text.clear()
                guessEditText.clearFocus()
                rvMain.adapter?.notifyDataSetChanged()
            }
            else{
                Snackbar.make(clRoot, "Please enter a number", Snackbar.LENGTH_LONG).show()
            }
        } catch (e : Exception){
            Snackbar.make(clRoot, "Please enter a number", Snackbar.LENGTH_LONG).show()

        }    }

    //for the user cant play is press no
    private fun disableEntry(){
        guessButton.isEnabled = false
        guessButton.isClickable = false
        guessButton.isVisible = false
        guessEditText.isEnabled = false
        guessEditText.isClickable = false
        guessEditText.isVisible = false
        answers.removeAll(answers)
        answers.add("GoodBye")
    }

    private fun showAlertDialog(title: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(title).setCancelable(false).setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id -> this.recreate() }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert = dialogBuilder.create()
        alert.setTitle("Game Over")
        alert.show()
    }
}
