package ru.ilocal.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.bindings.ScopeCloseable
import ru.ilocal.R
import ru.ilocal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}
