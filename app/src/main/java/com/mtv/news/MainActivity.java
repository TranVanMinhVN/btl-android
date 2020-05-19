package com.mtv.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mtv.news.activity.NewDetailActivity;
import com.mtv.news.adapter.MenuAdapter;
import com.mtv.news.adapter.PostAdapter;
import com.mtv.news.entity.DAO.AuthorDAO;
import com.mtv.news.entity.DAO.CategoryDAO;
import com.mtv.news.entity.Category;
import com.mtv.news.entity.DAO.NewDTO;
import com.mtv.news.entity.New;
import com.mtv.news.interfaces.AdapterListener;
import com.mtv.news.json.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String NEW = "NEW";
    public static final String BUNDEL = "BUNDEL";

    private String url = "http://127.0.0.1:8080/new/all/category";

    private ImageView imgView;
    private RecyclerView rvMenu;

    private MenuAdapter menuAdapter;

    private RecyclerView rvPost;

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
            //  menuAdapter.notifyDataSetChanged();
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

    public void addNew(){
       New n = new New(1,1,3,"Giá vàng hôm nay","Sau 3 phiên giảm liên tiếp, sáng nay 30/4 giá vàng thế giới bật tăng trong bối cảnh kinh tế Mỹ trải qua quý tệ nhất từ khủng hoảng 2008","Giá vàng trong nước, tính tới 8h30 sáng 12.5, giá vàng miếng trong nước được Tập Đoàn Vàng bạc đá quý Doji niêm yết ở mức: 47,8 triệu đồng/lượng mua vào và 48,1 triệu đồng/lượng bán ra, không đổi ở chiều mua vào và giảm 50 ngàn đồng chiều bán ra so với cuối phiên liền trước.\n" +
               "Công ty vàng bạc đá quý Sài Gòn niêm yết giá vàng SJC ở mức 47,7 triệu đồng/lượng mua vào và 48,17 triệu đồng/lượng bán ra, giảm 50 ngàn đồng ở chiều mua vào và giảm 100 ngàn đồng chiều bán ra so với cuối phiên giao dịch 11.5.\n" +
               "Giá vàng thế giới hôm nay hiện đang giao ngay ở ngưỡng 1.695 USD/ounce. Giá vàng bất ngờ quay đầu giảm trong bối cảnh đồng USD tăng cao. Thị trường lo ngại một làn sóng COVID-19 mới khi xuất hiện ổ dịch ở Vũ Hán (Trung Quốc) sau một tháng dỡ phong tỏa. Tại Hàn Quốc, số ca nhiễm SARS-CoV-2 tăng vọt do ổ dịch tại các quán bar ở Itaewon, Seoul. \n" +
               "Các nước mở rộng quy mô các gói hỗ trợ, kích thích kinh tế nhằm phục hồi sản xuất, lấy lại đà tăng trưởng. cũng ảnh hưởng đến đà tăng của giá vàng Ngoài ra, căng thẳng Mỹ- Trung Quốc cũng khiến các nhà đầu tư \"chững lại\" để chờ những tín hiệu mới.\n" +
               "Dù vậy, các chuyên gia phân tích thị trường và nhà đầu tư vẫn kỳ vọng giá kim loại quý này sẽ đi lên trong những ngày tới.","https://vnn-imgs-f.vgcloud.vn/2020/04/27/21/gia-vang-hom-nay-28-4-vang-van-o-tren-dinh-du-giam-manh-1.jpg","","","Thứ Hai, ngày 04/05/2020 09:19 AM (GMT+7)\n","");
     //   New n2 = new New(4,1,4,"Phản ứng của ông Trump trước tin ông Kim Jong Un bất ngờ xuất hiện","Tổng thống Mỹ Donald Trump đã lên tiếng khi được hỏi về thông tin nhà lãnh đạo Triều Tiên Kim Jong Un tái xuất sau 20 ngày vắng mặt.","a","https://bom.to/IVybnM");
        New n2 = new New(2,1,2,"Bí mật về Messi: Dòng máu châu Á trong huyết quản","Lionel Messi là một huyền thoại của bóng đá thế giới với sự nghiệp gắn bó cùng Barcelona.",
                "Ý nghĩa cách ăn mừng của Messi\n" +
                        "Màn ăn mừng bàn thắng chỉ tay và hướng lên bầu trời của Leo Messi là để tri ân người bà đã mất năm 1998, Celia Olivera Cuccittini. Chính bà là người ủng hộ Leo Messi đi thi đấu khi anh còn là một đứa trẻ. Những nỗ lực của bà Celia Olivera đã đem đến một cầu thủ vĩ đại cho Barcelona nói riêng, cũng như bóng đá thế giới nói chung.\nĐam mê với xăm mình\n" +
                        "Messi thích xăm mình, điều này có thể nhận ra vì cơ thể anh đầy những hình vẽ ở tay phải và chân trái với tên vợ con anh. Messi ban đầu không phải người ưa thích xăm mình. Anh giữ cơ thể không có vết mực nào trong suốt nhiều năm, mặc cho các đồng nghiệp đua nhau 'kể chuyện cuộc đời' bằng hình xăm." +
                        "\n" +
                        "Hình xăm đầu tiên của Messi là hình mẹ anh, bà Celia Maria Cuccittini. Anh xăm lên bờ vai trái, ngay đằng sau trái tim mình.","https://media.thethao247.vn/upload/anhtuan/2019/12/22/messi.jpg","","","12/05/2020 14:53:00 (GMT+7)","");
        New n3 = new New(4,1,2,"Lịch thi đấu của Barca hiểm họa khôn lường","Một tờ báo thể thao nổi tiếng thân Barcelona vừa tiết lộ lịch thi đấu gây sốc mà Messi và các đồng đội sẽ phải trải qua trong thời gian tới ở La Liga.","Mới đây, chia sẻ trên kênh truyền hình Vamos (Tây Ban Nha), Chủ tịch La Liga - ông Javier Tebas đã tiết lộ về mong muốn giải đấu này sẽ trở lại vào vào ngày 12/6 để thi đấu nốt 11 vòng còn lại của mùa giải 2019/20 đang dang dở vì đại dịch Covid-19:\n\"Tôi không biết chắc chắn ngày nào La Liga sẽ trở lại. Thời điểm có khả năng nhất là ngày 19/6, nhưng tôi mong muốn mốc đó sẽ sớm hơn 1 tuần, vào ngày 12/6 chẳng hạn. Nó phụ thuộc vào những diễn biến sắp tới (của dịch Covid-19 và ý kiến của Chính phủ Tây Ban Nha).\"\nÔng Tebas cũng hy vọng rằng các khán giả truyền hình sẽ được theo dõi mỗi ngày ít nhất một trận đấu ở La Liga khi 110 trận đấu ở 11 vòng còn lại của giải đấu này diễn ra liên tục trong hơn 1 tháng tới.","https://cdn.24h.com.vn/upload/2-2020/images/2020-05-11/Barca-960-1589195208-970-width660height426.jpg","Thứ Hai, ngày 11/05/2020 19:05 PM (GMT+7)\n","");


       // newDTO.deleteNew(2);
       // newDTO.addNew(n2);

        New n4 = new New(4,1,2,"Nội bộ Barca dậy sóng","Mối bất hòa giữa đội trưởng Lionel Messi và tân HLV Barcelona - Quique Setien đang được đẩy lên cao trào trong bối cảnh đội chủ sân Nou Camp vẫn đang khát khao săn cú đúp danh hiệu.","Barcelona và 19 CLB khác của La Liga sẽ được phép trở lại tập luyện theo nhóm đông cầu thủ (14 người/lần) trên sân tập theo sự cho phép của chính phủ Tây Ban Nha từ thứ Hai (19/5). Tuy nhiên, nội bộ của đội ĐKVĐ Tây Ban Nha lại đang nảy sinh mâu thuẫn đáng lo ngại.\nChia sẻ trên tờ Sport (Tây Ban Nha), Messi khẳng khái nói: \"Tôi không bao giờ nghi ngờ vào đội hình câu lạc bộ đang sở hữu và cũng chẳng nghi ngờ về khả năng Barcelona sẽ vô địch ở những giải đấu còn lại trong mùa giải (La Liga và Champions League).\nMỗi cầu thủ có ý kiến cá nhân riêng và đều cần được tôn trọng. Tôi cảm thấy may mắn khi được chơi bóng hàng năm ở Champions League và biết rằng vô địch là nhiệm vụ bất khả thi nếu toàn đội vẫn cứ chơi bóng như cách từng làm.\"","https://cdn.24h.com.vn/upload/2-2020/images/2020-05-17/Noi-bo-Barca-day-song-Bi-Messi-chi-trich-kem-tai-HLV-Setien-phan-phao-lionel-messiquique-setien-barcelona-2019-20_1gn9an-1589689928-251-width660height371.jpg","Chủ Nhật, ngày 17/05/2020 13:05 PM (GMT+7)\n","");

        New n5 = new New (5,1,2,"Barca mừng lớn vụ SAO 111 triệu euro","Inter Milan chịu bán Lautaro Martinez cho Barcelona nhưng thương vụ vẫn đang chờ diễn biến mới.","Barcelona trong thời gian qua đã ngày một tập trung hơn vào việc sở hữu chữ ký của tiền đạo người Argentina Lautaro Martinez, người đang có phong độ cao trong màu áo Inter Milan và cùng với Lionel Messi thi đấu tại Copa America 2018 và đoạt hạng Ba. Mới đây báo giới TBN cho biết thương vụ này đã có bước tiến triển quan trọng." +
                "\nTờ Mundo Deportivo tại Barcelona cho biết mặc dù Inter Milan đã duy trì quan điểm Barca phải trả số tiền 111 triệu euro trong điều khoản thanh lý hợp đồng của Lautaro, nhưng mới đây đội bóng xứ Catalunya đã thành công trong việc thuyết phục Inter Milan giảm giá.\nSố tiền Barca thanh toán sẽ là 60 triệu euro, còn lại Barca đổi một vài cầu thủ sang Inter.","https://cdn.24h.com.vn/upload/2-2020/images/2020-05-17/15894863076083-660-1589659061-23-width660height371.jpg","Chủ Nhật, ngày 17/05/2020 19:01 PM (GMT+7)","");

        newDTO.addNew(n5);
        //newDTO.addNew(n4);
       // newDTO.deleteNew(4);
       // newDTO.addNew(n3);
        newList = newDTO.getNewByCategory(1);
    }
}
