package krunal.com.example.workmanager.chain;

/**
 * 节点
 * Created by ZivChao on 2020/10/14
 *
 * @author ZivChao
 */
public interface Node {
    int getId();

    void complete();

    void error();
}
