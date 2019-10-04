package br.com.fiap.mycontactlist.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.fiap.mycontactlist.MainActivity
import br.com.fiap.mycontactlist.R
import br.com.fiap.mycontactlist.signup.SignupActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.etPasswordLogin

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private val newUserRequestCode = 1
    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userLogged = getSharedPreferences(
            "user_logged",
            Context.MODE_PRIVATE
        )

        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null && userLogged.getBoolean("user_logged", true)) {

            mAuth.currentUser?.reload()
            userId = mAuth.currentUser?.uid ?: ""

            goToHome()
        }

        btLogin.setOnClickListener {

            if (etEmailLogin.text.toString() == "" || etPasswordLogin.text.toString() == "") {
                Toast.makeText(this, R.string.blank_fields, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mAuth.signInWithEmailAndPassword(
                etEmailLogin.text.toString(),
                etPasswordLogin.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    mAuth.currentUser?.reload()
                    userId = mAuth.currentUser?.uid ?: ""

                    if(chbManterLogado.isChecked){
                        val editor = userLogged.edit()
                        editor.putBoolean("user_logged", true)
                        editor.apply()
                    } else {
                        val editor = userLogged.edit()
                        editor.putBoolean("user_logged", false)
                        editor.apply()
                    }

                    goToHome()
                } else {
                    Toast.makeText(
                        this@LoginActivity, it.exception?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        btSignup.setOnClickListener {
            startActivityForResult(
                Intent(this, SignupActivity::class.java),
                newUserRequestCode
            )
            finish()
        }
    }

    private fun goToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("userId", userId)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newUserRequestCode && resultCode == Activity.RESULT_OK) {
            etPasswordLogin.setText(data?.getStringExtra("email"))
        }
    }
}
