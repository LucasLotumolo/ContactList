package br.edu.ifsp.scl.ads.prdm.sc3039439.contactlist

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.ads.prdm.sc3039439.contactlist.Constant.EXTRA_CONTACT
import br.edu.ifsp.scl.ads.prdm.sc3039439.contactlist.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() {
    private val amb: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.contact_details)

        with (amb){
            saveBt.setOnClickListener {
                val contact = Contact(
                    hashCode(),
                    nameEt.text.toString(),
                    addressEt.text.toString(),
                    phoneEt.text.toString(),
                    emailEt.text.toString(),
                ).let{
                    Intent().putExtra(EXTRA_CONTACT, it).apply {
                        setResult(RESULT_OK, this)
                        finish()
                    }
                }
            }
        }
    }
}


