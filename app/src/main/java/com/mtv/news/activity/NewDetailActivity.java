package com.mtv.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mtv.news.MainActivity;
import com.mtv.news.R;
import com.mtv.news.adapter.MenuAdapter;
import com.mtv.news.adapter.RelatedpostAdapter;
import com.mtv.news.entity.Author;
import com.mtv.news.entity.Category;
import com.mtv.news.entity.DAO.AuthorDAO;
import com.mtv.news.entity.DAO.CategoryDAO;
import com.mtv.news.entity.DAO.NewDTO;
import com.mtv.news.entity.New;
import com.mtv.news.interfaces.AdapterListener;
import com.mtv.news.json.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewDetailActivity extends AppCompatActivity {

    private RecyclerView rvRelatedpost;

    List<New> newList;

    int isSelected;

    ImageView imgMenu;
    TextView textView;
    TextView textName;
    TextView textNode;
    TextView textContent;
    TextView textAuthor;
    ImageView img1;
    private RecyclerView rvMenu;

    private MenuAdapter menuAdapter;
    private List<Category> menuEntities = new ArrayList<>();

    AuthorDAO authorDAO;
    CategoryDAO categoryDAO;
    NewDTO newDTO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        authorDAO = new AuthorDAO(this,"Author",null,1);
        categoryDAO = new CategoryDAO(this,"Category2",null,1);
        newDTO = new NewDTO(this,"Newspaper",null,1);
        menuEntities = categoryDAO.getAllCategory();

        setContentView(R.layout.activity_newdetail);
        textView = findViewById(R.id.tv_data);
        textName = findViewById(R.id.tv_name);
        textNode = findViewById(R.id.tv_not);
        textContent = findViewById(R.id.tv_content);
        img1 = findViewById(R.id.img1);
        textAuthor = findViewById(R.id.tvAuthor);
        setData();
        imgMenu = findViewById(R.id.img_menu);
        final SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen._60sdp);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.layout_menu);

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });

        menuAdapter = new MenuAdapter(menuEntities, new AdapterListener() {
            @Override
            public void onItemClickListener(Object o, int pos, RecyclerView.ViewHolder holder) {
                for (int i = 0; i < menuEntities.size(); ++i) {
                    Category entity = menuEntities.get(i);
                    entity.setSelected(false);
                }
                Category category = (Category) o;
                Intent intent = new Intent(NewDetailActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("categoryId",category.getCategoryId());
                intent.putExtra(MainActivity.BUNDEL,bundle);
                startActivity(intent);
            }
            @Override
            public void onClick(New n) {

            }
        });
        rvMenu = (RecyclerView) menu.findViewById(R.id.rv_menu);
        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        rvMenu.setItemAnimator(new DefaultItemAnimator());
        rvMenu.setAdapter(menuAdapter);

        newList = newDTO.getNewByCategory(isSelected);
        rvRelatedpost = findViewById(R.id.rv_relatedpost);
        rvRelatedpost.setLayoutManager(new LinearLayoutManager(this));
        rvRelatedpost.setItemAnimator(new DefaultItemAnimator());
        RelatedpostAdapter relatedpostAdapter = new RelatedpostAdapter(newList,this);
        relatedpostAdapter.setListener(new AdapterListener() {
            @Override
            public void onItemClickListener(Object o, int pos, RecyclerView.ViewHolder holder) {

            }
            @Override
            public void onClick(New n) {
                Intent intent = new Intent(NewDetailActivity.this, NewDetailActivity.class);
                Bundle bundle = new Bundle();
                String newJson = Utils.getGsonParser().toJson(n);
                bundle.putString(MainActivity.NEW,newJson);
                intent.putExtra(MainActivity.BUNDEL,bundle);
                startActivity(intent);
            }
        });
        rvRelatedpost.setAdapter(relatedpostAdapter);
    }
    public void setData(){
        Intent intent =  getIntent();
        Bundle bundle = intent.getBundleExtra(MainActivity.BUNDEL);
        String newJson = bundle.getString(MainActivity.NEW);
        New n = Utils.getGsonParser().fromJson(newJson, New.class);
        textView.setText(n.getTime());
        textName.setText(n.getName());
        textNode.setText(n.getNote());
        textContent.setText(n.getContent());
        Picasso.with(this)
                .load(n.getUrlImg())
                .resize(750,452).noFade().into(img1);
        Author author = authorDAO.getAuthorById(n.getAuthorId());
        textAuthor.setText(author.getName());
        isSelected = n.getCategoryId();
    }
}
