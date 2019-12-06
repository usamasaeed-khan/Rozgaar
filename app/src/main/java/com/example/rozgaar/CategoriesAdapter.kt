package com.example.rozgaar

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_view_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class CategoriesAdapter(val context:Context,val categories:ArrayList<Categories>) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view_item,parent,false))
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.setData(category)

        holder.itemView.setOnClickListener {
            val intent = Intent(context,CategoriesFeed::class.java)
            intent.putExtra("cat_id",category.Category_ID)
            intent.putExtra("cat_name",category.CategoryName)
            context.startActivity(intent)
        }
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun setData(category:Categories){
            val imageBytes  = Base64.decode(category.CategoryImage,Base64.DEFAULT)

            val bmp:Bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)
            itemView.category_image.setImageBitmap(bmp)
            itemView.category_text.text = category.CategoryName
        }
    }

}