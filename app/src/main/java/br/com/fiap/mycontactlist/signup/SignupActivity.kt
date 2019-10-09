package br.com.fiap.mycontactlist.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.fiap.mycontactlist.MainActivity
import br.com.fiap.mycontactlist.R
import br.com.fiap.mycontactlist.login.LoginActivity
import br.com.fiap.mycontactlist.model.User
import br.com.fiap.mycontactlist.service.RetrofitInitializer
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private val newUser = User()
    private var userid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val context = this

        mAuth = FirebaseAuth.getInstance()

        btSignup.setOnClickListener {
            mAuth.createUserWithEmailAndPassword(
                etEmailSignup.text.toString(),
                etPasswordSignup.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    userid = mAuth.currentUser?.uid ?: ""
                    addUser()

                    Toast.makeText(context, R.string.account_created, Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@SignupActivity, it.exception?.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addUser() {
        newUser.uid = userid

        val call = RetrofitInitializer().userService().addUser(newUser)
        call.enqueue(object: Callback<User?> {
            override fun onResponse(call: Call<User?>?, response: Response<User?>) {
                response?.let {
                    val user = it.body()
                }
            }

            override fun onFailure(call: Call<User?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }
}
