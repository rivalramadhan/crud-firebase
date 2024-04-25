package com.example.datateman2

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdaptor (private val dataTeman: ArrayList<data_teman>, context: Context) :
    RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder>() {
    private val context: Context

    //viewholder for use save reference from other view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Nama: TextView
        val Alamat: TextView
        val NoHP: TextView
        val ListItem: LinearLayout

        //initiate view yg terpasang pada layout RecyclerView
        init {
            Nama = itemView.findViewById(R.id.namax)
            Alamat = itemView.findViewById(R.id.alamatx)
            NoHP = itemView.findViewById(R.id.no_hpx)
            ListItem = itemView.findViewById(R.id.list_item)
        }
    }

    //create view for set and memasang layout yg digunakan pada RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val V: View = LayoutInflater.from(parent.getContext()).inflate(
            R.layout.view_design, parent, false
        )
        return ViewHolder(V)
    }

    @SuppressLint("SetTextI18n")
    //pick value from recyclerview berdasarkan posisi tertentu
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val Nama: String? = dataTeman.get(position).nama
        val Alamat: String? = dataTeman.get(position).alamat
        val NoHP : String? = dataTeman.get(position).no_hp

        //input value ke dalam view
        holder.Nama.text = "Nama: $Nama"
        holder.Alamat.text = "Alamat: $Alamat"
        holder.NoHP.text = "No HP : $NoHP"
        holder.ListItem.setOnLongClickListener(
            object  : View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    //materi untuk next session
                    holder.ListItem.setOnLongClickListener { view ->
                        val action = arrayOf("Update", "Delete")
                        val alert: AlertDialog.Builder = AlertDialog.Builder(view.context)
                        alert.setItems(action, DialogInterface.OnClickListener { dialog, i ->
                            when (i) {
                                0 -> {
                                    val bundle = Bundle()
                                    bundle.putString("dataNama", dataTeman[position].nama)
                                    bundle.putString("dataAlamat", dataTeman[position].alamat)
                                    bundle.putString("dataNoHP", dataTeman[position].no_hp)
                                    bundle.putString("getPrimaryKey", dataTeman[position].key)
                                    val intent = Intent(view.context, UpdateData::class.java)
                                    intent.putExtras(bundle)
                                    context.startActivity(intent)
                                }

                                1 -> {listener?.onDeleteData(dataTeman.get(position), position)}
                            }
                        })
                        alert.create()
                        alert.show()
                        true
                    }
                    return true
                }
            })
    }

    //Menghitung Ukuran/jumlah daata  yg akan dishow pada recycler view
    override fun getItemCount(): Int {
        return dataTeman.size
    }

    interface dataListener {
        fun onDeleteData(data: data_teman?, position: Int)
    }

    //create constructor, for get input from database
    var listener : dataListener? = null
    init {
        this.context = context
        this.listener = context as MyListData
    }

}