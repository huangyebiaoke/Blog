package cn.madeai.blog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Post> posts;
    private SwipeRefreshLayout swipeRefresh;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    RecyclerView recyclerView=findViewById(R.id.recycler_view);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    final PostAdapter adapter=new PostAdapter(posts);
                    recyclerView.setAdapter(adapter);
                    swipeRefresh.setRefreshing(false);
                    adapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            switch (view.getId()){
                                case R.id.img_main_card2_share:
                                    Intent shareIntent=new Intent(Intent.ACTION_SEND);
                                    shareIntent.putExtra(Intent.EXTRA_TEXT,posts.get(position).getTitle()+":"+posts.get(position).getPermalink());
                                    shareIntent.setType("text/plain");
                                    startActivity(Intent.createChooser(shareIntent,"分享"));
                                    break;
                                case R.id.img_main_card2_favorite:
                                    ImageView favorite=view.findViewById(R.id.img_main_card2_favorite);
                                    favorite.setImageResource(R.drawable.baseline_favorite_black_24);
                                    break;
                                case R.id.card_main_1_2:
                                    Intent intent=new Intent(MainActivity.this,PostDetailActivity.class);
                                    intent.putExtra("title",posts.get(position).getTitle());
                                    intent.putExtra("content",posts.get(position).getRaw());
                                    ImageView imageView=view.findViewById(R.id.banner_view);
                                    Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                                    intent.putExtra("banner",bitmap);
                                    startActivity(intent);


                            }
                        }
                    });
                    break;
                default:
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getData(Config.BLOG_URL);

        swipeRefresh=findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(Config.BLOG_URL);
            }
        });
    }


    private void getData(final String url){
// TODO: 2019-02-22 最好使用回调机制处理异常;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg=Message.obtain();
                try {
                    OkHttpClient client=new OkHttpClient.Builder()
                            .connectTimeout(5, TimeUnit.SECONDS)
                            .cache(new Cache(new File(MainActivity.this.getExternalCacheDir(), "okhttpcache"), 10 * 1024 * 1024))
                            .build();
                    Request request=new Request.Builder().url(url).build();
                    Response response=client.newCall(request).execute();
                    String resqposeData=response.body().string();
                    Gson gson=new Gson();
                    posts=gson.fromJson(resqposeData, new TypeToken<List<Post >>(){}.getType());
/*                    for (Post post:posts){
                        List<Post.Tag> tags=post.getTags();
                        Log.d("Posts", post.toString()+"tags:"+tags.get(0).getName());
                    }*/
                    msg.what=1;
                } catch (IOException e) {
                    msg.what=0;
                    msg.obj=e.getMessage();
                }
                handler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
