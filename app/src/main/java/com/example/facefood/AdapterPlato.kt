package com.example.facefood

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_comida.view.*

class AdapterPlato(val lista:ArrayList<Plato>,val context: Context):
    RecyclerView.Adapter<AdapterPlato.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comida,parent,false))
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.nombre.text = lista[position].nombre
        holder.desc.text = lista[position].descripcion
        holder.precio.text = lista[position].precio.toString()
        when(lista[position].imagen){
            "chicharron"->holder.imagen.setImageResource(R.drawable.plato_chicharron)
            "pollo"->holder.imagen.setImageResource(R.drawable.plato_pollo)
            "sushi"->holder.imagen.setImageResource(R.drawable.plato_sushi)
            "fricase"->holder.imagen.setImageResource(R.drawable.plato_fricase)
            "piquemacho"->holder.imagen.setImageResource(R.drawable.plato_piquemacho)
            "pescado"->holder.imagen.setImageResource(R.drawable.pez_dorado)
        }
    }

    class ViewHolder(val view: View):RecyclerView.ViewHolder(view){
        val imagen = view.img_plato
        val nombre = view.nombre_plato
        val desc = view.descripcion_plato
        val precio = view.precio_plato
    }
}