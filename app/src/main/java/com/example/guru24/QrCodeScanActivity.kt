package com.example.guru24

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class QrCodeScanActivity : AppCompatActivity() {

    // QR 코드 스캔을 위한 ActivityResultLauncher
    private val qrScanLauncher = registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
        if (result.contents != null) {
            // QR 코드 스캔 성공, result.contents 에 스캔된 데이터가 담겨 있음
            val intent = Intent()
            intent.putExtra("SCAN_RESULT", result.contents)
            setResult(Activity.RESULT_OK, intent)
        } else {
            // 스캔 실패 혹은 취소
            setResult(Activity.RESULT_CANCELED)
        }
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // QR 코드 스캔 옵션 설정
        val options = ScanOptions().apply {
            setPrompt(getString(R.string.qr_scan_prompt)) // QR 스캔 프롬프트 메시지
            setBeepEnabled(false) // 비프음 비활성화
            setOrientationLocked(false) // 방향 고정 비활성화
        }

        // QR 코드 스캔 시작
        qrScanLauncher.launch(options)
    }
}