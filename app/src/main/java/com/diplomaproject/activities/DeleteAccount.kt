package com.diplomaproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.diplomaproject.R
import com.diplomaproject.databinding.ActivityDeleteAccountBinding

class DeleteAccount : AppCompatActivity() {

    lateinit var binding: ActivityDeleteAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delete_account)

        binding.delBtn.setOnClickListener(){
            finish()
        }
    }
}