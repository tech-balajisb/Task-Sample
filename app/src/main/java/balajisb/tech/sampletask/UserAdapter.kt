package balajisb.tech.sampletask

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.child_user_data.view.*

class UserAdapter(val context: Context, val usersList: List<UserData>) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.child_user_data, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder?, position: Int) {
        holder?.bindData(usersList.get(position), context)
    }

}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(data: UserData, context: Context) {
        itemView.firstName.setText(data.fName)
        itemView.lastName.setText(data.lName)
        Glide.with(context).load(data.image).apply(RequestOptions.circleCropTransform()).into(itemView.userImg)
    }
}