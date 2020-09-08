package org.leeinseo108.bookdatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> implements OnBookItemClickListener {
    //BookAdapter 역할. 1. 뷰홀더 객체 생성 2. 각 항목 누를 때 토스트메시지 생성
    ArrayList<BookInfo> items = new ArrayList<BookInfo>();
    OnBookItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.book_item, parent, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookInfo item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(BookInfo item) { items.add(item); }
    public void setItems(ArrayList<BookInfo> items) { this.items = items;}
    public BookInfo getItem(int position) { return items.get(position);}
    public void setItem(int position, BookInfo item) { items.set(position, item);}


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;
        TextView textView3;
        ImageView imageView;

        public ViewHolder(@NonNull final View itemView, final OnBookItemClickListener listener) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
        }

        public void setItem(BookInfo item){
            textView.setText(item.getName());
            textView2.setText(item.getAuthor());
            textView3.setText(item.getContents());
        }
    }

    @Override
    public void onItemClick(int position) {
        if (listener != null) {
            listener.onItemClick(position);
        }
    }

    public void setOnItemClickListener(OnBookItemClickListener listener) {
        this.listener = listener; //set in fragment2
    }
}
