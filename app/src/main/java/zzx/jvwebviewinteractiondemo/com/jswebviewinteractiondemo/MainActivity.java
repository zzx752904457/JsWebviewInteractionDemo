package zzx.jvwebviewinteractiondemo.com.jswebviewinteractiondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView tv1;
    private TextView tv2;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        webView = (WebView) findViewById(R.id.webview);

        //设置支持js
        webView.getSettings().setJavaScriptEnabled(true);

        /**
         * 设置js调用Android本地方法所在的类以及它的别名
         * 该demo要调用Android中startFunction()、startFunction(String str)两方法
         * 而这两方法是在MainActivity中，所以直接是传this,zzx是别名，对应js中zzx.xxxx
         */
        webView.addJavascriptInterface(this, "zzx");

        /**
         * 加载本地html
         */
        webView.loadUrl("file:///android_asset/zzx.html");


        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //要新开线程，不然不会执行
                tv1.post(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * 调用js中javacalljs()方法
                         */
                        webView.loadUrl("javascript:javacalljs()");
                    }
                });
            }
        });


        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv2.post(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * 调用js中javacalljswithargs(arg)方法
                         */
                        webView.loadUrl("javascript:javacalljswithargs('鲜鲜哥')");
                    }
                });
            }
        });
    }

    /**
     * 对应js中xxxxx.startFunction()
     * android 4.0以上需要加上该注解
     */
    @JavascriptInterface
    public void startFunction() {
        Toast toast = null;
        if (toast == null){
            toast = Toast.makeText(MainActivity.this, "调用了Android中startFunction()方法", Toast.LENGTH_SHORT);
            toast.show();
        }else {
            toast.setText("调用了Android中startFunction()方法");
        }
    }

    /**
     * 对应js中 xxxxx.startFunction('hello world')
     * @param str
     */
    @JavascriptInterface
    public void startFunction(String str) {
        Toast toast = null;
        if (toast == null){
            toast = Toast.makeText(MainActivity.this, "调用了Android中startFunction(String str)方法，参数是:" + str, Toast.LENGTH_SHORT);
            toast.show();
        }else {
            toast.setText("调用了Android中startFunction(String str)方法，参数是:" + str);
        }
    }
}
