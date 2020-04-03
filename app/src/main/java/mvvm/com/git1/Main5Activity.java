package mvvm.com.git1;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import mvvm.com.git1.view.OpenBookView;

public class Main5Activity extends AppCompatActivity implements View.OnClickListener {
    private OpenBookView openBookview;
    private ImageView bookshlefBook;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        bookshlefBook = findViewById(R.id.iv_bookshlef_book);
        openBookview = findViewById(R.id.openbookview);
        bookshlefBook.setOnClickListener(this);
        Log.i(TAG, "onCreate:->Main5Activity-> " + UserManager.sUserId);
    }

    public void onClick(View view) {
        openBookview.setVisibility(View.VISIBLE);
        Bitmap bitmap = ((BitmapDrawable) bookshlefBook.getBackground()).getBitmap();
        int left = bookshlefBook.getLeft();
        int top = bookshlefBook.getTop();
        int width = bookshlefBook.getWidth();
        int height = bookshlefBook.getHeight();
        Log.i(TAG, "onClick: left-->" + left + "--top-->" + top + "--width-->" + width + "--height-->" + height);
        Log.i(TAG, "bitmap: Width-->" + bitmap.getWidth() + "--Height-->" + bitmap.getHeight());
        openBookview.openAnimation(bitmap, bookshlefBook.getLeft(), bookshlefBook.getTop(), bookshlefBook.getWidth(), bookshlefBook.getHeight(), new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(Main5Activity.this, Main4Activity.class);
                startActivityForResult(intent, RESULT_FIRST_USER);
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = ((BitmapDrawable) bookshlefBook.getBackground()).getBitmap();
        openBookview.closeAnimation(bitmap, bookshlefBook.getWidth(), bookshlefBook.getHeight(), new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                openBookview.setVisibility(View.INVISIBLE);
            }
        });

    }
}
