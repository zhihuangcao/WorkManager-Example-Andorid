package krunal.com.example.workmanager.chain;

/**
 * Created by ZivChao on 2020/10/14
 *
 * @author ZivChao
 */
public class ChainNode implements Node {
    private int id;
    private Executor executor;
    private CallBack callBack;

    private ChainNode(int id, Executor executor) {
        this.id = id;
        this.executor = executor;
    }

    public static ChainNode create(int id, Executor executor) {
        return new ChainNode(id, executor);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void complete() {
        if (callBack != null) {
            callBack.onComplete();
        }
    }

    @Override
    public void error() {
        if (callBack != null) {
            callBack.onError();
        }
    }

    public void process(CallBack callBack) {
        this.callBack = callBack;
        if (executor != null) {
            executor.execute(this);
        }
    }

    interface CallBack {
        void onComplete();

        void onError();
    }
}
