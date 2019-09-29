package br.com.fiap.mycontactlist.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.fiap.mycontactlist.R
import br.com.fiap.mycontactlist.service.RetrofitInitializer
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.activity_contact.btUpdateContact
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private var userid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        mAuth = FirebaseAuth.getInstance()
        val contactName = intent.getStringExtra("contactName")
        val contactId = intent.getIntExtra("contactId", 0)

        tvContactName.text = contactName

        btUpdateContact.setOnClickListener {
            val intent = Intent(this, UpdateContactActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        btDeleteContact.setOnClickListener {
            userid = mAuth.currentUser?.uid ?: ""
            deleteContact(contactId)
        }
    }

    private fun deleteContact(contactId: Int){
        val context = this

        val call = RetrofitInitializer().contactService().deleteContact(
            userid, contactId)
        call.enqueue(object: Callback<Unit?> {
            override fun onResponse(call: Call<Unit?>?,
                                    response: Response<Unit?>
            ) {
                if (response.isSuccessful) {
                    finish()
                    response?.let {
                        val Contact = it.body()
                    }
                    Toast.makeText(context, "Contact Successfully Deleted", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(context, "Failed to Delete Contact", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Unit?>?, t: Throwable?) {
                Toast.makeText(context, "Failed to Delete Contact", Toast.LENGTH_SHORT).show()
                Log.e("onFailure error", t?.message)
            }
        })
    }
}
