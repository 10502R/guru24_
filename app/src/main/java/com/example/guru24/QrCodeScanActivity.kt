package com.example.guru24

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator

class QrCodeScanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val integrator = IntentIntegrator(this)
        integrator.setPrompt("QR 코드를 스캔해 주세요")
        integrator.setBeepEnabled(false)
        integrator.setOrientationLocked(false)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                // QR 코드 스캔 성공, result.contents 에 스캔된 데이터가 담겨 있음
                val intent = Intent()
                intent.putExtra("SCAN_RESULT", result.contents)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                // 스캔 실패 혹은 취소
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }
}
