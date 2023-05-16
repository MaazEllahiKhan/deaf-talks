package com.example.deaftalks.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.deaftalks.R
import com.example.deaftalks.models.ProfileModel
import com.example.deaftalks.ui.interfaces.OnItemClickListener
import com.example.deaftalks.utlis.Helper

class ProfileListAdapter(private val data: List<ProfileModel>,private  val click: OnItemClickListener) : RecyclerView.Adapter<ProfileListAdapter.ViewHolder>() {
        private val TAG ="ProfileAdapter"
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.nameItem)
        val expertise: TextView = itemView.findViewById(R.id.expertise)
        val parenCL: ConstraintLayout = itemView.findViewById(R.id.parenCL)
        val profileImage: ImageView = itemView.findViewById(R.id.profileImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userName.text = data[position].username
        holder.expertise.text = data[position].expertise
        holder.profileImage.setImageBitmap(Helper.getCircleBitmap(data[position].profileImage))
        holder.parenCL.setOnClickListener {
            click.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        Log.i(TAG, "getItemCount: size "+data.size)
        return data.size
    }
}
