package com.example.yemektariflerikitabi

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_liste.*

class ListeFragment : Fragment() {

    var yemekIsimListesi =ArrayList<String>()
    var yemekIdListesi = ArrayList<Int>()
    private lateinit var listeAdapter : ListeRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_liste, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listeAdapter= ListeRecyclerAdapter(yemekIsimListesi,yemekIdListesi)
        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.adapter=listeAdapter

        sqlVeriAlma()
    }

    fun sqlVeriAlma() {

        try {

            activity?.let {
                val database = it.openOrCreateDatabase("Yemekler", Context.MODE_PRIVATE, null)
                val cursor = database.rawQuery("SELECT * FROM yemekler", null)
                val yemekismiIndex = cursor.getColumnIndex("yemekismi")
                val yemekidIndex = cursor.getColumnIndex("id")
                yemekIsimListesi.clear()
                yemekIdListesi.clear()


                while (cursor.moveToNext()) {
                   yemekIsimListesi.add(cursor.getString(yemekismiIndex))
                    yemekIdListesi.add(cursor.getInt(yemekidIndex))
                }
                listeAdapter.notifyDataSetChanged()
                cursor.close()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}