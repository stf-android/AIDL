package mvvm.com.git1.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import mvvm.com.git1.Bean.BookBean;
import mvvm.com.git1.R;
import mvvm.com.git1.view.OpenBookView;

/**
 * Created by stf on 2020/4/1.
 */

public class OpenBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<BookBean> list;
    private Context context;

    public OpenBookAdapter(ArrayList<BookBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ListViewHodler listViewHodler = new ListViewHodler(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.openbook_item_layout, viewGroup, false));
        return listViewHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        final BookBean bookBean = list.get(i);
        final ListViewHodler hodler2 = (ListViewHodler) holder;
        hodler2.nameTv.setText((i + 1) + "  " + bookBean.getName());
        final ImageView imageView = hodler2.imageView;

        Glide.with(context).load(Uri.parse(bookBean.getImgUrl())).error(R.mipmap.bookpage).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listenr != null) {
                    listenr.OnClickListener(hodler2.openBookView, imageView, i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHodler extends RecyclerView.ViewHolder {

        private final OpenBookView openBookView;
        private final ImageView imageView;
        private final TextView nameTv;

        public ListViewHodler(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_bookshlef_book);
            openBookView = itemView.findViewById(R.id.openbookview);
            nameTv = itemView.findViewById(R.id.nameTv);
        }
    }

    public ItemOnClickListenr listenr;

    public void setItemOnClickListenr(ItemOnClickListenr listenr) {
        this.listenr = listenr;
    }

    public interface ItemOnClickListenr {
        void OnClickListener(OpenBookView openBookView, ImageView imageView, int postion);
    }
}
