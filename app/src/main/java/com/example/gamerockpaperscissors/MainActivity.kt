package com.example.gamerockpaperscissors


import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.gamerockpaperscissors.databinding.ActivityMainBinding

class MainActivity: ComponentActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        databaseHelper = DatabaseHelper(this)

        val username = intent.getStringExtra("name")
        val password = intent.getStringExtra("password")
        val userScore = databaseHelper.readUserScore(username.toString(), password.toString())

        binding.playerScore.text = userScore.toString()

        binding.restartBtn.setOnClickListener {
            clear_score()
        }

        binding.scissorsBtn.setOnClickListener {

            binding.userMoveImg.setImageDrawable(getResources().getDrawable(R.drawable.scissors));

            val computer_move = (1..4).random()

            if (computer_move == 1) {
                binding.computerMoveImg.setImageDrawable(getResources().getDrawable(R.drawable.rock));

                binding.winnerTv.text = "Компьютер победил"

                val cscore: Int = binding.computerScore.text.toString().toInt() + 1
                binding.computerScore.text = cscore.toString()
            } else if (computer_move == 2) {

                binding.computerMoveImg.setImageDrawable(getResources().getDrawable(R.drawable.paper));

                binding.winnerTv.text = "Игрок победил"

                val pscore: Int = binding.playerScore.text.toString().toInt() + 1
                databaseHelper.updateUserScore(username.toString(), pscore)

                binding.playerScore.text = pscore.toString()
            } else {

                binding.computerMoveImg.setImageDrawable(getResources().getDrawable(R.drawable.scissors));

                binding.winnerTv.text = "Ничья"
            }

        }

        binding.paperBtn.setOnClickListener {

            binding.userMoveImg.setImageDrawable(getResources().getDrawable(R.drawable.paper));

            val computer_move = (1..4).random()

            if (computer_move == 1) {

                binding.computerMoveImg.setImageDrawable(getResources().getDrawable(R.drawable.rock));

                binding.winnerTv.text = "Игрок победил"

                val pscore: Int = binding.playerScore.text.toString().toInt() + 1
                databaseHelper.updateUserScore(username.toString(), pscore)
                binding.playerScore.text = pscore.toString()
            }
            else if (computer_move == 2) {

                binding.computerMoveImg.setImageDrawable(getResources().getDrawable(R.drawable.paper));

                binding.winnerTv.text = "Ничья"
            } else {


                binding.computerMoveImg.setImageDrawable(getResources().getDrawable(R.drawable.scissors));

                binding.winnerTv.text = "Компьютер победил"

                val cscore: Int = binding.computerScore.text.toString().toInt() + 1
                binding.computerScore.text = cscore.toString()
            }

        }

        binding.rockBtn .setOnClickListener {

            binding.userMoveImg.setImageDrawable(getResources().getDrawable(R.drawable.rock));

            val computer_move = (1..4).random()

            if (computer_move == 1) {
                binding.computerMoveImg.setImageDrawable(getResources().getDrawable(R.drawable.rock));

                binding.winnerTv.text = "Ничья"

            } else if (computer_move == 2) {

                binding.computerMoveImg.setImageDrawable(getResources().getDrawable(R.drawable.paper));

                binding.winnerTv.text = "Компьютер победил"

                val cscore: Int = binding.computerScore.text.toString().toInt() + 1
                binding.computerScore.text = cscore.toString()


            } else {
                binding.computerMoveImg.setImageDrawable(getResources().getDrawable(R.drawable.scissors));

                binding.winnerTv.text = "Игрок победил"

                val pscore: Int = binding.playerScore.text.toString().toInt() + 1
                databaseHelper.updateUserScore(username.toString(), pscore)

                binding.playerScore.text = pscore.toString()
            }
        }

    }
    private fun clear_score() {
        databaseHelper = DatabaseHelper(this)

        val username = intent.getStringExtra("name")

        binding.computerScore.text = "0"
        binding.playerScore.text = "0"
        databaseHelper.updateUserScore(username.toString(), 0)

        binding.winnerTv.text = ""

        binding.userMoveImg.setImageDrawable(getResources().getDrawable(R.drawable.question));
        binding.computerMoveImg.setImageDrawable(getResources().getDrawable(R.drawable.question));

    }
}