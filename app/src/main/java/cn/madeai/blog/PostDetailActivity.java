package cn.madeai.blog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

import ru.noties.markwon.Markwon;
import ru.noties.markwon.SpannableConfiguration;

public class PostDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String content=intent.getStringExtra("content");

        Toolbar toolbar=findViewById(R.id.post_detail_toolbar);
        CollapsingToolbarLayout collapsingToolbar=findViewById(R.id.collapsing_toolbar);
        ImageView bannerView=findViewById(R.id.banner_view);
        TextView contentText=findViewById(R.id.content_text);

        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(title);
        collapsingToolbar.setExpandedTitleColor(Color.BLACK);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        Markwon.setMarkdown(contentText, content);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
