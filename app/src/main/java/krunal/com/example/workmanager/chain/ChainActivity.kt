package krunal.com.example.workmanager.chain

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import krunal.com.example.workmanager.R
import krunal.com.example.workmanager.chain.job.NodeA
import krunal.com.example.workmanager.chain.job.NodeB
import krunal.com.example.workmanager.chain.job.NodeC

/**
 * Created by ZivChao on 2021/9/3
 * @author ZivChao
 */
class ChainActivity : AppCompatActivity() {
    private lateinit var start: Button
    private lateinit var cancel: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chain)
        initView()
    }

    private fun initView() {
        start = findViewById(R.id.start)
        cancel = findViewById(R.id.cancel)
        start.setOnClickListener {
            val nodeA = NodeA().create(NodeIds.A)
            val nodeB = NodeB().create(NodeIds.B)
            val nodeC = NodeC().create(NodeIds.C)
            ChainHandler.Builder().addNode(nodeA).addNode(nodeB).addNode(nodeC).build().start()
        }
        cancel.setOnClickListener {

        }
    }
}