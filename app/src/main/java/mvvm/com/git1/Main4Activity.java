package mvvm.com.git1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_FIRST_USER);
        super.onBackPressed();
    }
}
