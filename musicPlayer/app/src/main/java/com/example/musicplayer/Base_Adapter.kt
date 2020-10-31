package com.example.musicplayer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class Base_Adapter(private var context: Context, private var lista : ArrayList<Songs>): BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var listaPosition = lista.get(position)
        var view : View

        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_songs,null)
        }
        else{
            return convertView
        }

        val tvNome = view.findViewById<TextView>(R.id.tvNome)
        val ivImagem = view.findViewById<ImageView>(R.id.ivImagem)

        val img = context.resources.getIdentifier(listaPosition.getImage(), "mipmap", context.packageName)
        ivImagem.setImageResource(img)
        tvNome.text = listaPosition.getNome()
        return view
    }

    override fun getItem(position: Int): Any {
        return this.lista.get(position)
    }

    override fun getItemId(position: Int): Long {
        return -1
    }

    override fun getCount(): Int {
        return this.lista.count()
    }
}