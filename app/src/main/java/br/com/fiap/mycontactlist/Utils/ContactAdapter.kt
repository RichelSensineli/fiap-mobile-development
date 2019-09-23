package br.com.fiap.mycontacts.br.com.fiap.mycontacts.utils

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.mycontactlist.R
import br.com.fiap.mycontactlist.form.ContactActivity
import br.com.fiap.mycontactlist.model.Contact

class ContactAdapter(private var contactList: MutableList<Contact>) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(){
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ContactAdapter.ContactViewHolder {
        val context = viewGroup.context
        val inflatter = LayoutInflater.from(context)
        val shouldAttachToParentImmediately = false

        val view = inflatter.inflate(R.layout.recycler_view_contact_item, viewGroup, shouldAttachToParentImmediately)

        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactAdapter.ContactViewHolder, position: Int) {
        val item = contactList[position]

        holder.bindContact(item)
    }

    class ContactViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener{

        private var view: View
        private lateinit var contact: Contact
        private var name : TextView
        private var phone : TextView

        override fun onClick(v: View?) {
            val context = itemView.context
            val intent = Intent(context, ContactActivity::class.java)
            intent.putExtra("contactName", name.text.toString())
            intent.putExtra("contactPhone", phone.text.toString())
            context.startActivity(intent)

        }

        init {
            view = v

            name = view.findViewById(R.id.tvContactName)
            phone = view.findViewById(R.id.tvContactPhone)

            v.setOnClickListener(this)
        }

        fun bindContact(contact: Contact){
            this.contact = contact

            name.text = contact.name
            phone.text = contact.phone.toString()
        }
    }
}