package com.example.facefood

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val lista:ArrayList<Plato> = ArrayList()
    private val adapterPlato = AdapterPlato(lista,this)
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_platos.layoutManager = GridLayoutManager(this,2)
        rv_platos.adapter = adapterPlato

        leerDatos()

        btn_adicionar.setOnClickListener{
            val plato = Plato("fricase","Fricase","Plato insertado desde codigo",25)
            insertarDatos(Plato)
        }
    }

    private fun insertarDatos(plato:Plato){
        db.collection("comidas").document("Plato7").set(plato).addOnSuccessListener{
            leerDatos()
        }.addOnFailureListener{
            Toast.makeText(this, "Error en al insertar nuevo plato", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart(){
        super.onStart()

        escucharDatos()
    }

    private fun leerDatos(){
        db.collection("comidas").get().addOnCompleteListener{
            if(it.isSuccessful){
                lista.clear()
                for(documentos in it.result!!){
                    val nombre = documentos.getString("nombre")
                    val imagen = documentos.getString("imagen")
                    val desc = documentos.getString("descripcion")
                    val precio = documentos.get("precio") as Long?

                    if(nombre != null && imagen != null && desc != null && precio != null){
                        lista.add(Plato(imagen,nombre,desc,precio))
                    }
                }
                adapterPlato.notifyDataSetChanged()
            }
        }
    }

    private fun escucharDatos(){
        //cambiar datos sin salir de la app
        db.collection("comidas").addSnapshotListener{snapshot,exception ->
            if(exception != null){
                Toast.makeText(this,"Sucedio un error", Toast.LENGTH_SHORT).show()
            }

            lista.clear()
            snapshot?.forEach{
                val nombre = it.getString("nombre")
                val imagen = it.getString("imagen")
                val desc = it.getString("descripcion")
                val precio = it.get("precio") as Long?

                if(nombre != null && imagen != null && desc != null && precio != null){
                    lista.add(Plato(imagen,nombre,desc,precio))
                }
            }
            adapterPlato.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
