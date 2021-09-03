package krunal.com.example.workmanager.chain.job

import krunal.com.example.workmanager.chain.ChainNode

/**
 * Created by ZivChao on 2021/9/3
 * @author ZivChao
 */
abstract class Job {
    protected lateinit var node: ChainNode
    protected abstract fun work()

    fun create(id: Int): ChainNode {
        node = ChainNode.create(id) {
            work()
        }
        return node
    }


}