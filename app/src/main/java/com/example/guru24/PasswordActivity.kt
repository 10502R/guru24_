package com.example.guru24

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.guru24.databinding.ActivityIntroBinding
import com.example.guru24.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity() {
    private var mBinding: ActivityPasswordBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}