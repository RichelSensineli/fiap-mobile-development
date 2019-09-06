package br.com.fiap.mycontactlist.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.fiap.mycontactlist.R
import br.com.fiap.mycontactlist.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_new_contact.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.etNome

class SignupActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mAuth = FirebaseAuth.getInstance()

        btCreate.setOnClickListener {
            mAuth.createUserWithEmailAndPassword(
                etNome.text.toString(),
                etEmail.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    saveInRealTimeDatabase()
                } else {
                    Toast.makeText(this@SignupActivity, it.exception?.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveInRealTimeDatabase() {
        val user = User(etName.text.toString(), etEmail.text.toString(), etPh.text.toString())
        FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Usuário criado com sucesso",
                        Toast.LENGTH_SHORT).show()
                    val returnIntent = Intent()
                    returnIntent.putExtra("email", etEmail.text.toString())
                    setResult(RESULT_OK, returnIntent)
                    finish()
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao criar o usuário", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
