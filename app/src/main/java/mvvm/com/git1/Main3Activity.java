package mvvm.com.git1;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import mvvm.com.git1.Bean.BookBean;
import mvvm.com.git1.adapter.OpenBookAdapter;
import mvvm.com.git1.adapter.SimpleDividerItemDecoration;
import mvvm.com.git1.view.OpenBookView;

public class Main3Activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<BookBean> list;
    private Bitmap bitmap = null;
    private ImageView ImageViews = null;
    private OpenBookView openBookViews;
    private static final String TAG = "Main3Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mRecyclerView = findViewById(R.id.recycleerview);
        list = initData();
        initAdapter();

    }

    private void initAdapter() {
        if (list == null) {
            list = initData();
        }

        OpenBookAdapter openBookAdapter = new OpenBookAdapter(list, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, true);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(openBookAdapter);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(5));

        openBookAdapter.setItemOnClickListenr(new OpenBookAdapter.ItemOnClickListenr() {
            @Override
            public void OnClickListener(OpenBookView openBookView, ImageView imageView, final int postion) {
                openBookView.setVisibility(View.VISIBLE);
                openBookViews = openBookView;
                try {
                    ImageViews = imageView;
                    if (imageView != null) {
                        bitmap = ((BitmapDrawable) imageView.getBackground()).getBitmap();
                    } else {
                        Toast.makeText(Main3Activity.this, "imageView is null", Toast.LENGTH_SHORT).show();
                    }

                    if (bitmap != null) {
                        openBookView.openAnimation(bitmap, imageView.getLeft(), imageView.getTop(), imageView.getWidth(), imageView.getHeight(), new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                Intent intent = new Intent(Main3Activity.this, Main4Activity.class);
                                startActivityForResult(intent, RESULT_FIRST_USER);
                                overridePendingTransition(0, 0);

                                openBookViews.closeAnimation(bitmap, ImageViews.getWidth(), ImageViews.getHeight(), new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        openBookViews.setVisibility(View.INVISIBLE);
                                    }
                                });

                            }
                        });
                    } else {
                        Toast.makeText(Main3Activity.this, "bitmap 为空", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_FIRST_USER) {

        } else {
            Log.i(TAG, "onActivityResult: " + RESULT_FIRST_USER);
        }
    }

    private ArrayList<BookBean> initData() {
        String url1 = "http://192.168.1.132/photo/book2.png";
        String url2 = "http://192.168.1.132/photo/book2.png";
        String url3 = "http://192.168.1.132/photo/book2.png";
        String url4 = "http://192.168.1.132/photo/book2.png";
        String url5 = "http://192.168.1.132/photo/book2.png";
//        String url1 = "http://192.168.1.132/photo/book1.png";
//        String url2 = "http://192.168.1.132/photo/book2.png";
//        String url3 = "http://192.168.1.132/photo/book3.png";
//        String url4 = "http://192.168.1.132/photo/book4.png";
//        String url5 = "http://192.168.1.132/photo/book5.png";

        BookBean bookBean1 = new BookBean("Android1", url1);
        BookBean bookBean2 = new BookBean("Android2", url2);
        BookBean bookBean3 = new BookBean("技术资料", url3);
        BookBean bookBean4 = new BookBean("皮囊", url4);
        BookBean bookBean5 = new BookBean("传统文化", url5);

        ArrayList<BookBean> list = new ArrayList<>();
        ArrayList<BookBean> list2 = new ArrayList<>();
        list.add(bookBean1);
        list.add(bookBean2);
        list.add(bookBean3);
        list.add(bookBean4);
        list.add(bookBean5);

        for (int j = 0; j < 100; j++) {
            int i = new Random().nextInt(4);
            list2.add(list.get(i));
        }
        return list2;
    }
}
