package br.com.fiap.mycontactlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.fiap.mycontactlist.form.NewContactActivity
import br.com.fiap.mycontactlist.list.ContactListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btListAll.setOnClickListener {
            val proximaTela =  Intent(this@MainActivity, ContactListActivity::class.java)
            startActivity(proximaTela)
        }

        btNewContact.setOnClickListener {
            val proximaTela =  Intent(this@MainActivity, NewContactActivity::class.java)
            startActivity(proximaTela)
        }
    }
}
