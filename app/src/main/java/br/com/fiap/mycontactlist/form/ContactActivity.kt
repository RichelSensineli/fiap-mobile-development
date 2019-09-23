package br.com.fiap.mycontactlist.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.fiap.mycontactlist.MainActivity
import br.com.fiap.mycontactlist.R
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.activity_contact.btUpdateContact
import kotlinx.android.synthetic.main.activity_update_contact.*

class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val contactName = intent.getStringExtra("contactName")

        tvContactName.text = contactName

        btUpdateContact.setOnClickListener {
            deleteContact()
        }

        btDeleteContact.setOnClickListener {
            val intent = Intent(this, UpdateContactActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }

    private fun deleteContact(){

    }
}
