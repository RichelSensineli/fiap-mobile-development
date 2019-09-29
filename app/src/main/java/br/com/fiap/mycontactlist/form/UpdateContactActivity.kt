package br.com.fiap.mycontactlist.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.fiap.mycontactlist.R
import br.com.fiap.mycontactlist.model.Contact
import br.com.fiap.mycontactlist.service.RetrofitInitializer
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_update_contact.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpdateContactActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private var userid = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_contact)

        mAuth = FirebaseAuth.getInstance()
        val contactId = intent.getIntExtra("contactId", 0)


        btUpdateContact.setOnClickListener{
            userid = mAuth.currentUser?.uid ?: ""

                updateContact(
                contactId,
                etUpdateContactName.text.toString(),
                Integer.parseInt(etUpdateContactPhone.text.toString()),
                etUpdateContactEmail.text.toString())
        }

    }

    private fun updateContact(contactId: Int,
                              contactName: String,
                              contactPhone: Int,
                              contactEmail: String){
        val context = this

        val call = RetrofitInitializer().contactService().updateContact(
            userid,
            contactId,
            contactName,
            contactPhone,
            contactEmail)

        call.enqueue(object: Callback<Contact> {
            override fun onResponse(call: Call<Contact>?, response: Response<Contact>
            ) {
                if (response.isSuccessful) {
                    finish()
                    response?.let {
                        val Contact = it.body()
                    }
                    Toast.makeText(context, "Contact Successfully updated", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(context, "Failed to update Contact", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Contact>?, t: Throwable?) {
                Toast.makeText(context, "Failed to update Contact", Toast.LENGTH_SHORT).show()
                Log.e("onFailure error", t?.message)
            }
        })
    }
}
