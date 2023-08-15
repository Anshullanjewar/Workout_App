package com.example.a10minworkout.Activity

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a10minworkout.Database.Constants
import com.example.a10minworkout.Database.ExerciseModel
import com.example.a10minworkout.ExerciseStatusAdapter
import com.example.a10minworkout.R
import com.example.a10minworkout.databinding.ActivityExerciseBinding
import com.example.a10minworkout.databinding.CustomDialogboxBinding
import java.lang.Exception
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding : ActivityExerciseBinding? = null
    private var resTimer : CountDownTimer?= null
    private var restProgress = 0

    private var exerciseTimer : CountDownTimer?= null
    private var exerciseProgress = 0

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentPosition = -1

    private var tts : TextToSpeech?=null

    private var player: MediaPlayer?=null

    private var exerciseAdapter : ExerciseStatusAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

        tts= TextToSpeech(this,this)
        binding?.toolbarExercise?.setNavigationOnClickListener{
            customDialogForBackButton()
        }

        setupRestView()
        setupExerciseStatusRV()
    }

    private fun setupExerciseStatusRV(){
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setupRestView(){

        try{
            val soundURI = Uri.parse("android.resource://eu.tutorials.a10minworkout/"+ R.raw.press_start)

            player = MediaPlayer.create(applicationContext,soundURI)
            player?.isLooping= false
            player?.start()
        }
        catch (e: Exception){
            e.printStackTrace()
        }

        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility  = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingLabelExerciseName?.visibility = View.VISIBLE

        if (resTimer != null){
            resTimer?.cancel()
            restProgress = 0
        }

        speakOut("Now rest for 10 seconds")


        binding?.tvUpcomingLabelExerciseName?.text = exerciseList!![currentPosition+1].getName()

        setRestProgressBar()
    }

    private fun setupExerciseView(){
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility  = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabelExerciseName?.visibility = View.INVISIBLE

        if (exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentPosition].getName())

        binding?.ivImage?.setImageResource(exerciseList!![currentPosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentPosition].getName()

        setExerciseProgressBar()
    }

    private fun setRestProgressBar(){
        binding?.progrssbar?.progress = restProgress

        resTimer = object: CountDownTimer(10000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progrssbar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10- restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Start Exercise", Toast.LENGTH_SHORT).show()
                currentPosition++

                exerciseList!![currentPosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setupExerciseView()
            }

        }.start()
    }

    private fun setExerciseProgressBar(){
        binding?.progressbarExercise?.progress = exerciseProgress

        exerciseTimer = object: CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressbarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30- exerciseProgress).toString()
            }

            override fun onFinish() {




                Toast.makeText(this@ExerciseActivity, "Rest Now", Toast.LENGTH_SHORT).show()
                if (currentPosition< exerciseList?.size!!-1){
                    exerciseList!![currentPosition].setIsSelected(false)
                    exerciseList!![currentPosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }
                else{

                    Toast.makeText(this@ExerciseActivity,"Completed",Toast.LENGTH_SHORT).show()

                    finish()
                    val intent = Intent(this@ExerciseActivity , FinishActivity::class.java)
                    startActivity(intent)

                }

            }

        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()

        if (resTimer != null){
            resTimer?.cancel()
            restProgress = 0
        }

        if (exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player!=null){
            player!!.stop()
        }

        binding = null
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.US)

            if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "Language not Supported!")
            }
        }else{
            Log.e("TTS","Installation Failed")
        }
    }

    private fun speakOut(text:String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH,null, "")
    }

    private fun customDialogForBackButton(){
        val customDialog = Dialog(this)
        val dialogBinding = CustomDialogboxBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.tvYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
    }
}