package krunal.com.example.workmanager.chain;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ZivChao on 2020/10/14
 *
 * @author ZivChao
 */
@IntDef({NodeIds.A, NodeIds.B, NodeIds.C})
@Retention(RetentionPolicy.SOURCE)
public @interface NodeIds {
    int A = 50;

    int B = 100;

    int C = 200;
}
