package krunal.com.example.workmanager.chain.job

import android.util.Log

/**
 * Created by ZivChao on 2021/9/3
 * @author ZivChao
 */
class NodeA : Job() {
    override fun work() {
        Log.d("Job", "NodeA")
        node.complete()
    }

}