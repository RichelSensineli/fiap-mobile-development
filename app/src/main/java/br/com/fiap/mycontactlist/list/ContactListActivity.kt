package br.com.fiap.mycontactlist.list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fiap.mycontactlist.R
import br.com.fiap.mycontactlist.model.Contact
import br.com.fiap.mycontactlist.service.RetrofitInitializer
import br.com.fiap.mycontacts.br.com.fiap.mycontacts.utils.ContactAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_contact_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactListActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private var contactList: ArrayList<Contact> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ContactAdapter
    private var userId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(br.com.fiap.mycontactlist.R.layout.activity_contact_list)

        RetrofitInitializer()

        userId = intent.getStringExtra("userId")

        findContacts(userId)
    }

    override fun onRestart() {
        super.onRestart()
        finish();
        startActivity(getIntent());
    }

    private fun findContacts(userId: String){
        val context = this

        val call = RetrofitInitializer().contactService().getContactList(userId)
        call.enqueue(object: Callback<ArrayList<Contact>?> {
            override fun onResponse(call: Call<ArrayList<Contact>?>?,
                                    response: Response<ArrayList<Contact>?>
            ){
                response?.body()?.let {

                    contactList = it

                    if(contactList.size > 0){
                        loadList()
                    } else {
                        Toast.makeText(context, R.string.no_contacts, Toast.LENGTH_SHORT).show()
                    }
                }
                findViewById<RelativeLayout>(R.id.loadingPanel).visibility = View.GONE;
            }

            override fun onFailure(call: Call<ArrayList<Contact>?>?,
                                   t: Throwable?) {
                Toast.makeText(context, R.string.failed_load.toString() + " \n" + t?.message, Toast.LENGTH_SHORT).show()
                Log.e("onFailure error", t?.message)

                }
        })
    }

    private fun loadList(){
        linearLayoutManager = LinearLayoutManager(this)
        recycler_view_contacts.layoutManager = linearLayoutManager

        adapter = ContactAdapter(contactList)
        recycler_view_contacts.adapter = adapter
    }
}
