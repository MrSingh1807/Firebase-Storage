package com.example.basic_project.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.basic_project.R
import com.example.basic_project.activities.ProfileActivity
import com.example.basic_project.modals.Child
import com.example.basic_project.utils.Constants
import com.example.basic_project.utils.Constants.Companion.PUT_EXTRAS_KEY
import kotlinx.android.synthetic.main.contact_card.view.*

class RecyclerAdapter(private val context: Context, private val childData: ArrayList<Child>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.tv_name
        val age: TextView = view.tv_age
        val cls: TextView = view.tv_class
        val state: TextView = view.tv_state
        val uid :TextView = view.tv_uid

        val layout: LinearLayout = view.contact_layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.contact_card, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.uid.text = childData[position].uid
        holder.name.text = "Name: ${childData[position].name}"
        holder.age.text = "Age: ${childData[position].age.toString()}"
        holder.cls.text = "Class: ${childData[position].cls.toString()}"
        holder.state.text = "State: ${childData[position].state}"

        holder.layout.setOnClickListener {
            val uid: String? = childData[holder.adapterPosition].uid
            if (uid != null) {
                val intent = Intent(context, ProfileActivity::class.java)
                intent.putExtra(PUT_EXTRAS_KEY, uid)
                context.startActivity(intent)
            }
        }

        holder.layout.setOnLongClickListener {
            val uid: String? = childData[holder.adapterPosition].uid

            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Alert!")
            dialog.setMessage("Do you want to Delete...")
            dialog.setCancelable(true)

            dialog.setPositiveButton("Yes") { dialogInterface, int ->
                if (uid != null) {
                    val voidTask = Constants().removeChild(uid)
                    voidTask.addOnSuccessListener {
                        Toast.makeText(
                            context,
                            "Child removed from Database",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    voidTask.addOnFailureListener {
                        Toast.makeText(
                            context,
                            "Child didn't removed from Database",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            dialog.setNegativeButton("Cancel") { dialogInterface, i ->
                Toast.makeText(context, "Canceled..", Toast.LENGTH_SHORT).show()
            }
            dialog.show()

            true
        }
    }

    override fun getItemCount(): Int {
        return childData.size
    }

}
