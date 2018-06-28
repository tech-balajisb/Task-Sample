package balajisb.tech.sampletask

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class APIUser {

    var requestQueue: RequestQueue? = null

    constructor(context: Context?) {
        requestQueue = getRequestQueue(context!!)
    }


    fun getRequestQueue(context: Context): RequestQueue {
        if (requestQueue == null) {
            synchronized(APIUser::class.java) {
                if (requestQueue == null) {
                    requestQueue = Volley.newRequestQueue(context)
                }
            }
        }
        return requestQueue!!
    }


}