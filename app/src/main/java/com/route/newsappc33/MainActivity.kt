package com.route.newsappc33

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.route.notesappc33gsun.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler()
            .postDelayed({
                startActivity(Intent(this@MainActivity,HomeActivity::class.java))
                finish()
            },2000);
    }
}