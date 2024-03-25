package io.instabee.codetest.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import io.instabee.codetest.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        findNavController(R.id.nav_host_container).apply {
            setupActionBarWithNavController(this)
        }
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.nav_host_container).navigateUp()

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (onSupportNavigateUp()) return else finishAfterTransition()
    }
}
