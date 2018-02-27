package android.jac.com.animationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.list_layout);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("慕课网" + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        mListView.setAdapter(adapter);
        //布局动画控制器
        LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.zoom_in));
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);//ORDER_RANDOM随机顺序 ORDER_NORMAL正序
        mListView.setLayoutAnimation(lac);//加载布局动画
        mListView.startLayoutAnimation();//启动布局动画
    }
}
