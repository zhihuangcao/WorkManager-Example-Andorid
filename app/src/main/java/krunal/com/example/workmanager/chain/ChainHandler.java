package krunal.com.example.workmanager.chain;

import android.util.SparseArray;

/**
 * Created by ZivChao on 2020/10/14
 *
 * @author ZivChao
 */
public class ChainHandler implements Handler {
    //自动升序
    private SparseArray<ChainNode> nodeArrays;

    public ChainHandler(SparseArray<ChainNode> nodeArrays) {
        this.nodeArrays = nodeArrays;
    }

    @Override
    public void start() {
        if (nodeArrays == null || nodeArrays.size() <= 0) {
            throw new ChainException("nodeArrays == null || nodeArrays.size <= 0");
        }
        startNode(nodeArrays.keyAt(0));
    }

    private void startNode(int nodeId) {
        int index = nodeArrays.indexOfKey(nodeId);
        ChainNode node = nodeArrays.valueAt(index);
        if (node != null) {
            node.process(new ChainNode.CallBack() {
                @Override
                public void onComplete() {
                    nextNode(index);
                }

                @Override
                public void onError() {
                    cancel();
                }
            });
        }
    }

    private void nextNode(int index) {
        //移除执行过的第一个
        removeNode(index);
        if (nodeArrays.size() > 0) {
            startNode(nodeArrays.keyAt(0));
        }
    }

    private void removeNode(int index) {
        if (nodeArrays.size() > 0) {
            nodeArrays.removeAt(index);
        }
    }

    private void cancel() {
        if (nodeArrays.size() > 0) {
            nodeArrays.clear();
        }
    }

    public static class Builder {
        private SparseArray<ChainNode> nodeArrays;

        public Builder() {
            this.nodeArrays = new SparseArray<>();
        }

        public Builder addNode(ChainNode node) {
            if (node != null) {
                nodeArrays.append(node.getId(), node);
            }
            return this;
        }

        public ChainHandler build() {
            ChainHandler chainHandler = new ChainHandler(nodeArrays);
            return chainHandler;
        }
    }
}
