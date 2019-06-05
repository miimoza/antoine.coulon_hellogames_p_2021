package fr.epita.android.hellogames

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ImageFragment.InteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Fragment Transcription

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val imageFragment = ImageFragment()
        fragmentTransaction.add(R.id.main_container, imageFragment)
        fragmentTransaction.commit()
    }

    override fun imageWasClicked(i: Int) {

        // When the user clik on the image
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val detailedFragment = DetailedFragment(i)

        fragmentTransaction.replace(R.id.main_container, detailedFragment)
        fragmentTransaction.commit()
    }
}
