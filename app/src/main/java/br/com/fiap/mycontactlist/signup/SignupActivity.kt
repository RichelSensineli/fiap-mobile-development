package br.com.fiap.mycontactlist.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.fiap.mycontactlist.R
import br.com.fiap.mycontactlist.model.User
import br.com.fiap.mycontactlist.service.ContactService
import br.com.fiap.mycontactlist.service.ServiceBuilder
import br.com.fiap.mycontactlist.service.UserService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_new_contact.*
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

        mAuth = FirebaseAuth.getInstance()

        btSignup.setOnClickListener {
            mAuth.createUserWithEmailAndPassword(
                etEmailSignup.text.toString(),
                etPasswordSignup.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    userid = mAuth.currentUser?.uid ?: ""
                    addUser()
                } else {
                    Toast.makeText(this@SignupActivity, it.exception?.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addUser() {
        System.out.println("USER ID: "+ userid)

        newUser.uid = userid

//        newUser.nome = etNomeSignup.text.toString()
//        newUser.email = etEmailSignup.text.toString()
//        newUser.fone = etPhoneSignup.text.toString()

        val userService = ServiceBuilder.buildService(UserService::class.java)
        val requestCall = userService.addUser(newUser)

        val context = this

        requestCall.enqueue(object: Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    finish() // Move back to DestinationListActivity
                    var newlyCreatedUser = response.body() // Use it or ignore it
                    Toast.makeText(context, "User Successfully Created", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to Create User", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(context, "Failed to Create User", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
