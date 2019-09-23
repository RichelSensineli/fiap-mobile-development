package br.com.fiap.mycontactlist.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fiap.mycontactlist.R
import br.com.fiap.mycontactlist.model.Contact
import br.com.fiap.mycontactlist.service.RetrofitInitializer
import br.com.fiap.mycontacts.br.com.fiap.mycontacts.utils.ContactAdapter
import kotlinx.android.synthetic.main.activity_contact_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactListActivity : AppCompatActivity() {

    private var contactList: ArrayList<Contact> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ContactAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        RetrofitInitializer()

        val userid = intent.getStringExtra("userId")

        findContacts(userid)

    }

    private fun findContacts(userId: String){
        val context = this
        var onComplete : (()-> Unit)? = null

        val call = RetrofitInitializer().contactService().getContactList(userId)
        call.enqueue(object: Callback<ArrayList<Contact>?> {
            override fun onResponse(call: Call<ArrayList<Contact>?>?,
                                    response: Response<ArrayList<Contact>?>
            ){
                response?.body()?.let {
                        contactList = it
                        loadList()
                    }
            }

            override fun onFailure(call: Call<ArrayList<Contact>?>?,
                                   t: Throwable?) {
                Toast.makeText(context, "Failed to Load the Contact", Toast.LENGTH_SHORT).show()
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
