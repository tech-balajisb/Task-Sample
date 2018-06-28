package balajisb.tech.sampletask

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.activity_users_recycler.*
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {

    private var userList: ArrayList<UserData> = arrayListOf()
    var url = "https://reqres.in/api/users?page="
    private var requestQueue: RequestQueue? = null
    private var pageId = 1
    private var adapter: UserAdapter? = null

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_recycler)
        userRecycler.layoutManager = LinearLayoutManager(this)
        requestQueue = APIUser(this).requestQueue
        setupScrollListener()
        getUsers(pageId)
        adapter = UserAdapter(this, userList)
        userRecycler.adapter = adapter
    }

    fun getUsers(pageId: Int) {
        val getUrl = url + pageId
        val request = StringRequest(0, getUrl, {
            val jsonObject = JSONObject(it)
            val page = jsonObject.getInt("page")
            val per_page = jsonObject.getInt("per_page")
            val total = jsonObject.getInt("total")
            val total_pages = jsonObject.getInt("total_pages")
            val dataArray = jsonObject.getJSONArray("data")
            if (dataArray.length() > 0) {
                for (i in 0 until dataArray.length()) {
                    val jsonObj = dataArray.getJSONObject(i)
                    val data = UserData(jsonObj.getInt("id"), jsonObj.getString("first_name"), jsonObj.getString("last_name"), jsonObj.getString("avatar"))
                    userList.add(data)
                }
            }
            val userData = UserResponse(page, per_page, total, total_pages, userList)
            updateUI(userData)
        }, {
            Log.e("Error", it.toString())
        })
        requestQueue?.add(request)

    }


    fun updateUI(userData: UserResponse) {
        pageId = userData.page + 1
        if (userList.size > 0) {
            noUserText.visibility = View.GONE
            userRecycler.visibility = View.VISIBLE
            adapter?.notifyDataSetChanged()
        } else {
            noUserText.visibility = View.VISIBLE
            userRecycler.visibility = View.GONE
        }
    }

    private fun setupScrollListener() {
        val layoutManager = userRecycler.layoutManager as LinearLayoutManager
        userRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val firstVisibleItem = layoutManager.findLastVisibleItemPosition()

                listScrolled(visibleItemCount, firstVisibleItem, totalItemCount)
            }
        })
    }

    private fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        Log.e("visibleItemCount", visibleItemCount.toString())
        Log.e("lastVisibleItemPosition", lastVisibleItemPosition.toString())
        Log.e("totalItemCount", totalItemCount.toString())
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            getUsers(pageId)
        }
    }

}
