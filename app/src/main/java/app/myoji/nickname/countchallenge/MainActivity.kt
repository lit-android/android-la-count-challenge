package app.myoji.nickname.countchallenge

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.myoji.nickname.countchallenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // バインディングクラスの変数
    private lateinit var binding: ActivityMainBinding

    // SoundPoolの変数
    private lateinit var soundPool: SoundPool
    private var sound: Int = 0

    private lateinit var prefs: SharedPreferences

    // カウント変数
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        // SoundPoolの設定
        soundPool = SoundPool.Builder().build()
        sound = soundPool.load(this, R.raw.sound, 1)

        // SharedPreferencesの初期化
        prefs = getSharedPreferences("CountChallenge", Context.MODE_PRIVATE)
        count = prefs.getInt("Count", 0)

        setCountText()

        binding.upButton.setOnClickListener {
            count++
            setCountText()
        }
    }

    override fun onPause() {
        // カウント数の保存
        val editor = prefs.edit()
        editor.putInt("count", count)
        editor.apply()

        super.onPause()
    }

    private fun setCountText() {
        binding.countText.text = count.toString()
        when (count % 2) {
            0 -> {
                binding.countText.setTextColor(Color.RED)
            }
            1 -> {
                binding.countText.setTextColor(Color.BLUE)
                soundPool.play(sound, 1.0f, 1.0f, 0, 0, 1.0f)
            }
         }
    }
}