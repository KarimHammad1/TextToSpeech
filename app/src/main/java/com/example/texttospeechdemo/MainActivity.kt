package com.example.texttospeechdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.texttospeechdemo.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech?=null
    private var binding: ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        tts=TextToSpeech(this,this)

        binding?.btnSpeak?.setOnClickListener {view->
            if(binding?.etEnteredText?.text!!.isEmpty()){
                Toast.makeText(this,"Enter a text to speak",Toast.LENGTH_LONG).show()
            }else{
                speakOut(binding?.etEnteredText?.text.toString())
            }

        }
    }

    override fun onInit(status: Int) {
        if (status==TextToSpeech.SUCCESS){
            val result=tts?.setLanguage(Locale.US)

            if (result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","The Language specified is not supported!")
            }else{
                Log.e("TTS","Initialization Failed!")
            }
        }
    }
    private fun speakOut(text:String){
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")

    }

    override fun onDestroy() {
        super.onDestroy()

        if (tts!=null){
            tts?.stop()
            tts?.shutdown()
        }
        binding=null
    }
}