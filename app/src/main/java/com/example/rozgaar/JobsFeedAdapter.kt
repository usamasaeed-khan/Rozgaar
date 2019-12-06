package com.example.rozgaar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_view_item.view.*
import kotlinx.android.synthetic.main.jobs_feed_recycler_item.view.*
import java.util.*
import kotlin.collections.ArrayList
import android.content.Intent
import android.widget.Filter
import android.widget.Filterable

class JobsFeedAdapter(val context:Context,val jobs:ArrayList<PostedJobs>) : RecyclerView.Adapter<JobsFeedAdapter.PostedJobsViewHolder>(),Filterable {

    var searchedJobs:ArrayList<PostedJobs> = ArrayList(jobs)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostedJobsViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.jobs_feed_recycler_item,parent,false)
        return PostedJobsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return jobs.size
    }

    override fun onBindViewHolder(holder: PostedJobsViewHolder, position: Int) {
        val job = jobs[position]
        holder.setData(job)
        holder.itemView.applyButton.setOnClickListener {
            val intent = Intent(context,ApplyScreenActivity::class.java)
            intent.putExtra("jobDesc",job)
            context.startActivity(intent)
        }
        holder.itemView.setOnClickListener {
            Toast.makeText(context,job.EmployerName,Toast.LENGTH_LONG).show()
        }
    }

    inner class PostedJobsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun setData(job:PostedJobs){
            if(job.EmployerImage!=null) {

                val imageBytes = Base64.decode(job.EmployerImage, Base64.DEFAULT)

                val bmp: Bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                itemView.image.setImageBitmap(bmp)
            }
            itemView.company_name_text.text = job.EmployerName
            itemView.description_text.text = job.Description + " ... See More."
        }
    }

    override fun getFilter(): Filter {
        return searchFilter
    }
    private val searchFilter:Filter = object: Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<PostedJobs>()
            if(constraint == null || constraint.isEmpty()){
                filteredList.addAll(searchedJobs)
            }
            else {
                val filterPattern:String = constraint.toString().toLowerCase().trim()
                for(item in searchedJobs){
                    if(item.EmployerName.toLowerCase().contains(filterPattern,true) || item.Description.toLowerCase().contains(filterPattern,true) || item.Location.toLowerCase().contains(filterPattern,true)){
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {


            jobs.clear()
            jobs.addAll(results!!.values as Collection<PostedJobs>)
            notifyDataSetChanged()
        }

    }

}