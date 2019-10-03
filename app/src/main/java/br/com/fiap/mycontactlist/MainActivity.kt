package br.com.fiap.mycontactlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.fiap.mycontactlist.form.NewContactActivity
import br.com.fiap.mycontactlist.list.ContactListActivity
import br.com.fiap.mycontactlist.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btListAll.setOnClickListener {
            val proximaTela = Intent(this@MainActivity, ContactListActivity::class.java)
            val userId = intent.getStringExtra("userId")

            proximaTela.putExtra("userId", userId)
            startActivity(proximaTela)
        }

        btNewContact.setOnClickListener {
            val proximaTela = Intent(this@MainActivity, NewContactActivity::class.java)
            val userId = intent.getStringExtra("userId")

            proximaTela.putExtra("userId", userId)
            startActivity(proximaTela)
        }

        btAbout.setOnClickListener {
            val proximaTela = Intent(this@MainActivity, AboutActivity::class.java)

            startActivity(proximaTela)
        }

        btSair.setOnClickListener {
            //Sair
            FirebaseAuth.getInstance().signOut()
            val proximaTela = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(proximaTela)
            finish()
        }
    }
}
