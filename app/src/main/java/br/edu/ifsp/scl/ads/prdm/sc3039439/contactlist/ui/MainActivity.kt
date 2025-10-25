package br.edu.ifsp.scl.ads.prdm.sc3039439.contactlist.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc3039439.contactlist.R
import br.edu.ifsp.scl.ads.prdm.sc3039439.contactlist.adapter.ContactAdapter
import br.edu.ifsp.scl.ads.prdm.sc3039439.contactlist.model.Constant.EXTRA_CONTACT
import br.edu.ifsp.scl.ads.prdm.sc3039439.contactlist.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.prdm.sc3039439.contactlist.model.Contact

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //Data source
    private val  contactList: MutableList<Contact> = mutableListOf()

    //Adapter
    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(this, contactList)
    }

    private lateinit var carl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(br.edu.ifsp.scl.ads.prdm.sc3039439.contactlist.R.string.list)

        fillContactList()

        amb.contactLv.adapter = contactAdapter

        carl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result -> if (result.resultCode == RESULT_OK){
            val contact = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                result.data?.getParcelableExtra(EXTRA_CONTACT, Contact::class.java)
            } else {
                result.data?.getParcelableExtra(EXTRA_CONTACT)
            }
            contact?.let{ receivedContact ->
                contactList.add(receivedContact)
                contactAdapter.notifyDataSetChanged()
            }
        }
        }

        registerForContextMenu(amb.contactLv)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterForContextMenu(amb.contactLv)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.add_contact_mi -> {
                carl.launch( Intent(this, ContactActivity::class.java))
                true
            }
            else -> { false }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position

        return when(item.itemId){
            R.id.remove_contact_mi -> {
                contactList.removeAt(position)
                contactAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Contact Remove", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.edit_contact_mi -> {
                true
            }
            else -> { false }
        }
    }


    private fun fillContactList(){
        for (i in 1 .. 10){
            contactList.add(
                Contact(
                    i,
                    "Name: $i",
                    "Address: $i",
                    "Phone: $i",
                    "Email: $i",
                )
            )
        }
    }

}