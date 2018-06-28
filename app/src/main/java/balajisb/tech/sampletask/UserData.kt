package balajisb.tech.sampletask

import java.lang.reflect.Array

data class UserData(val id:Int,val fName:String,val lName:String,val image:String)

data class UserResponse(val page:Int,val per_page:Int,val total:Int,val total_pages:Int,val userList: ArrayList<UserData>)