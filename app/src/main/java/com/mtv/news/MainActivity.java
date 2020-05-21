package com.mtv.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mtv.news.activity.NewDetailActivity;
import com.mtv.news.adapter.MenuAdapter;
import com.mtv.news.adapter.PostAdapter;
import com.mtv.news.entity.Author;
import com.mtv.news.entity.DAO.AuthorDAO;
import com.mtv.news.entity.DAO.CategoryDAO;
import com.mtv.news.entity.Category;
import com.mtv.news.entity.DAO.NewDTO;
import com.mtv.news.entity.New;
import com.mtv.news.interfaces.AdapterListener;
import com.mtv.news.json.Utils;
import com.mtv.news.search.Suggestion;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String NEW = "NEW";
    public static final String BUNDEL = "BUNDEL";

    private ImageView imgView;
    private RecyclerView rvMenu;
    private MenuAdapter menuAdapter;
    private RecyclerView rvPost;
    private ImageView imgSearch;

    private List<Category> menuEntities = new ArrayList<>();
    private List<New> newList = new ArrayList<>();

    private CategoryDAO categoryDAO;
    private NewDTO newDTO;
    private AuthorDAO authorDAO;

    private Category selectedCategory;

    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryDAO = new CategoryDAO(this,"Category2",null,1);
        authorDAO = new AuthorDAO(this,"Author",null,1);
        newDTO = new NewDTO(this,"Newspaper",null,1);

        List<New> list = newDTO.getNewByName("barca");

        handleSearch(newDTO.getAllNew());

        imgView = findViewById(R.id.img_menu);
        final SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen._60sdp);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.layout_menu);

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });

        List<Category> categoryList = categoryDAO.getAllCategory();

        for (Category category : categoryList){
            category.setSelected(false);
            categoryDAO.updateCategory(category.getCategoryId(),category);
        }

        Intent intent =  getIntent();
        Bundle bundle = intent.getBundleExtra(MainActivity.BUNDEL);
        if (bundle!=null){
            int id = bundle.getInt("categoryId");
            Category category = categoryDAO.getCategoryById(id);
            category.setSelected(true);
            categoryDAO.updateCategory(id,category);
            newList = newDTO.getNewByCategory(id);
        }else {
            Category category1
                    = new Category(1,"Thời sự",true);
            categoryDAO.updateCategory(1,category1);
            selectedCategory = category1;
            addNew();
        }
        menuEntities = categoryDAO.getAllCategory();

        menuAdapter = new MenuAdapter(menuEntities, new AdapterListener() {
            @Override
            public void onItemClickListener(Object o, int pos, RecyclerView.ViewHolder holder) {
                for (int i = 0; i < menuEntities.size(); ++i) {
                    Category entity = menuEntities.get(i);
                    entity.setSelected(false);
                }
                if(selectedCategory!=null){
                    selectedCategory.setSelected(false);
                    categoryDAO.updateCategory(selectedCategory.getCategoryId(),selectedCategory);
                }
                Category category = (Category) o;
                category.setSelected(true);
                categoryDAO.updateCategory(category.getCategoryId(),category);
                newList = newDTO.getNewByCategory(category.getCategoryId());
                selectedCategory = category;
                setMenuAdapter(newList);
                menu.toggle();
            }

            @Override
            public void onClick(New n) {

            }
        });

        rvMenu = (RecyclerView) menu.findViewById(R.id.rv_menu);
        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        rvMenu.setItemAnimator(new DefaultItemAnimator());
        rvMenu.setAdapter(menuAdapter);
        setRV();
    }

    public void addNew(){
        Author author = new Author(2,"Trần Minh Quang","Nam Định","123333333");
        Author author1 = new Author(3,"Phan Văn Chinh","Quảng Ninh","2322222222");
        authorDAO.addAuthor(author1);
        authorDAO.addAuthor(author);
        New n = new New(1,1,1,"Đà Nẵng đón chuyến bay đưa 240 công dân từ Myanmar về nước","Đây là chuyến bay được thực hiện nhờ sự hỗ trợ của Cục Lãnh sự, Bộ Ngoại giao nhằm đưa những công dân nước ta về tránh dịch Covid-19.","Lúc 15 giờ 45 phút chiều nay (21/5), chuyến bay VJ 832 của Hãng Hàng không VietJet đã về tới Sân bay Quốc tế Đà Nẵng, chở theo 240 hành khách là công dân Việt Nam từ Myanmar về tránh dịch Covid-19.\nĐây là chuyến bay được thực hiện nhờ sự hỗ trợ của Cục Lãnh sự, Bộ Ngoại giao nhằm đưa những công dân nước ta về tránh dịch Covid-19. Hành khách trên chuyến bay này chủ yếu là người già, trẻ em, sinh viên phải nghỉ học tránh dịch và những công dân hoàn cảnh khó khăn bên nước bạn. Sau khi hạ cánh xuống Sân bay Quốc tế Đà Nẵng, máy bay được bố trí khu vực đỗ riêng, sau đó các bộ phận Kiểm dịch Y tế quốc tế, Hải quan, Công an Xuất nhập cảnh sẽ trực tiếp làm thủ tục ngay tại chân máy bay.","https://images.vov.vn/cr_w990/uploaded/cizotokek8ly8uzveukg/2020_05_21/chuan_bi_don_khach_xuong_lam_thu_tucvov__ikvr.jpg","Thứ 5, 19:21, 21/05/2020","");
        New n1 = new New(6,1,1,"Các quan chức cấp cao APEC sẽ họp trực tuyến để thảo luận COVID-19","Hội nghị sẽ thảo luận các vấn đề liên quan đến phục hồi kinh tế, trong khi đảm bảo rằng các nỗ lực hiện nay nhằm giảm thiểu các tác động y tế của dịch tiếp tục đem lại kết quả tích cực.","Bộ Thương mại và công nghiệp Malaysia ngày 21/5 thông báo nước này sẽ chủ trì một hội nghị trực tuyến các quan chức cấp cao của Diễn đàn Hợp tác kinh tế châu Á-Thái Bình Dương (APEC).\nHội nghị sẽ diễn ra ngày 27/5, tập trung vào việc thực hiện tuyên bố của Các Bộ trưởng thương mại APEC về đại dịch viêm đường hô hấp cấp COVID-19, thảo luận các vấn đề liên quan đến phục hồi kinh tế, trong khi đảm bảo rằng các nỗ lực hiện nay nhằm giảm thiểu các tác động y tế của dịch tiếp tục đem lại kết quả tích cực.\nĐầu tháng trước, các bộ trưởng thương mại APEC đã ra tuyên bố kêu gọi tăng cường hợp tác giữa các nền kinh tế thành viên nhằm tạo điều kiện cho thương mại, coi đây là một biện pháp để chống dịch.","https://cdnimg.vietnamplus.vn/t620/uploaded/hotnnz/2020_05_21/ttxvnkinh_te_chau_a.jpg","21/05/2020 18:57 GMT+7 ","");
       New n2 = new New(7,2,1,"Nắng nóng sắp chấm dứt, cảnh báo lũ quét và ngập lụt","Từ chiều tối và đêm nay, khu vực vùng núi và trung du Bắc Bộ có mưa, mưa vừa và giông, có nơi mưa to đến rất to.","Chiều và đêm mai, vùng mưa mở rộng xuống các tỉnh đồng bằng Bắc Bộ và Bắc Trung Bộ.\nTừ mai, trên các sông suối khu vực Bắc Bộ sẽ xuất hiện một đợt lũ với biên độ lũ lên ở thượng lưu từ 2 - 4m, hạ lưu từ 1 - 2m. Mực nước đỉnh lũ trên các sông dưới mức báo động 1.\nTừ ngày 23/5, trên sông Bưởi và thượng nguồn các sông thuộc Thanh Hóa, Nghệ An khả năng xuất hiện lũ nhỏ với biên độ lũ lên từ 1 - 3m.\nNguy cơ cao xảy ra lũ quét và sạt lở đất, ngập lụt ở vùng trũng tại nhiều tỉnh vùng núi khu vực Bắc Bộ và Bắc Trung Bộ, đặc biệt tại các tỉnh Lào Cai, Yên Bái, Cao Bằng, Bắc Kạn, Lạng Sơn, Hà Giang, Tuyên Quang, Thanh Hóa, Nghệ An.","https://vnn-imgs-f.vgcloud.vn/2020/05/21/10/nang-nong-sap-cham-dut-canh-bao-lu-quet-va-ngap-lut.jpg"," 21/05/2020 11:42 GMT+7","");
       New n3 = new New(8,2,1,"Nhà máy xử lý rác tai tiếng ở Hải Dương đổ tro xỉ ra môi trường","Nhà máy xử lý rác thải Việt Hồng (huyện Thanh Hà, Hải Dương) từng bị phản ánh gây ô nhiễm, giờ đây tiếp tục đổ tro thải ra môi trường.","Gần chục ngày nay, người dân 2 xã Cổ Dũng, Tuấn Việt (huyện Kim Thành) phát hiện những đống tro xỉ rác thải của nhà máy xử lý rác thải Việt Hồng được đơn vị chủ quản là công ty CP Quản lý công trình đô thị Hải Dương “tập kết” bên ngoài khuôn viên nhà máy, để san lấp mặt bằng.\nTheo người dân, vị trí đổ xỉ thải trước đây là đất ruộng, sau khi nhà máy xử lý rác đưa vào hoạt động, khu ruộng thành vùng sình lầy, nước luôn đen kịt.\nTrong xỉ thải đổ ra môi trường có lẫn nhiều tạp chất, thậm chí là rác thải như vải, nilon, nhựa, sắt vụn… chưa cháy hết trộn lẫn với nhau.\nLúc mới đổ ra, “vật liệu san lấp” này bốc mùi xú uế nồng nặc, người hít nhiều bị nhức đầu, chóng mặt.","https://vnn-imgs-f.vgcloud.vn/2020/05/19/23/nha-may-xu-ly-rac-thai-bi-to-1.jpg","21/05/2020 06:03 GMT+7","");


        newDTO.addNew(n2);
        newDTO.addNew(n3);
        newList = newDTO.getNewByCategory(1);
    }

    void setRV(){
        rvPost = findViewById(R.id.rv_post);
        rvPost.setLayoutManager(new LinearLayoutManager(this));
        rvPost.setItemAnimator(new DefaultItemAnimator());
        PostAdapter postAdapter = new PostAdapter(this,newList);
        postAdapter.setListener(new AdapterListener() {
            @Override
            public void onItemClickListener(Object o, int pos, RecyclerView.ViewHolder holder) {

            }

            @Override
            public void onClick(New n) {
                Intent intent = new Intent(MainActivity.this, NewDetailActivity.class);
                Bundle bundle = new Bundle();
                String newJson = Utils.getGsonParser().toJson(n);
                bundle.putString(NEW,newJson);
                intent.putExtra(BUNDEL,bundle);
                startActivity(intent);
            }
        });

        rvPost.setAdapter(postAdapter);
        rvPost.setHasFixedSize(true);
    }

    void setMenuAdapter(List<New> newList){
        PostAdapter postAdapter = new PostAdapter(context,newList);
        postAdapter.setListener(new AdapterListener() {
            @Override
            public void onItemClickListener(Object o, int pos, RecyclerView.ViewHolder holder) {

            }
            @Override
            public void onClick(New n) {
                Intent intent = new Intent(MainActivity.this, NewDetailActivity.class);
                Bundle bundle = new Bundle();
                String newJson = Utils.getGsonParser().toJson(n);
                bundle.putString(NEW,newJson);
                intent.putExtra(BUNDEL,bundle);
                startActivity(intent);
            }
        });
        rvPost.setAdapter(postAdapter);

        menuAdapter.notifyDataSetChanged();
    }

    void handleSearch( ArrayList<New> newLists ){

         final ArrayList<Suggestion> suggestionList = initData(newLists);

        final FloatingSearchView searchView= (FloatingSearchView) findViewById(R.id.floating_search_view);

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    searchView.clearSuggestions();
                } else {
                    searchView.showProgress();
                    searchView.swapSuggestions(getSuggestion(newQuery,suggestionList));
                    searchView.hideProgress();
                }
            }
        });
        searchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                searchView.showProgress();
                searchView.swapSuggestions(getSuggestion(searchView.getQuery(),suggestionList));
                searchView.hideProgress();
            }

            @Override
            public void onFocusCleared() {

            }
        });
        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Suggestion suggestion= (Suggestion) searchSuggestion;
                newList = newDTO.getNewByName(suggestion.getName());
                setMenuAdapter(newList);
            }

            @Override
            public void onSearchAction(String currentQuery) {

            }
        });
    }

    private ArrayList<Suggestion> initData(ArrayList<New> newLists){
        ArrayList<Suggestion> list = new ArrayList<>();

        for(New n : newLists){
            Suggestion suggestion = new Suggestion(n.getName());
            list.add(suggestion);
        }
        return list;
    }

    private List<Suggestion> getSuggestion(String query , ArrayList<Suggestion> list){
        List<Suggestion> suggestions=new ArrayList<>();
        for(Suggestion suggestion: list){
            if(suggestion.getName().toLowerCase().contains(query.toLowerCase())){
                suggestions.add(suggestion);
            }
        }
        return suggestions;
    }
}
