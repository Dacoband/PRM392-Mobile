package com.example.sp25_huynhthiennhan_net1720.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sp25_huynhthiennhan_net1720.R;
import com.example.sp25_huynhthiennhan_net1720.database.DatabaseHelper;
import com.example.sp25_huynhthiennhan_net1720.model.Author;
import java.util.List;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder> {
    private List<Author> authorList;
    private DatabaseHelper dbHelper;
    private Context context;


    public AuthorAdapter(List<Author> authorList, DatabaseHelper dbHelper, Context context) {
        this.authorList = authorList;
        this.dbHelper = dbHelper;
        this.context = context;
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_author, parent, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        Author author = authorList.get(position);
        holder.nameTextView.setText(author.getName());
        holder.addressTextView.setText("Address: " + author.getAddress());
        // Xử lý sự kiện nhấn nút Delete
        holder.deleteButton.setOnClickListener(v -> {
            deleteAuthor(author.getId(), position);
        });
    }

    @Override
    public int getItemCount() {
        return authorList.size();
    }

    private void deleteAuthor(int authorId, int position) {
        // Hiển thị hộp thoại xác nhận trước khi xóa
        new android.app.AlertDialog.Builder(context)
                .setTitle("Delete Author")
                .setMessage("Are you sure you want to delete this author?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    // Kiểm tra xem tác giả có sách liên quan không
                    int bookCount = db.query("Book", null, "Author_ID = ?", new String[]{String.valueOf(authorId)}, null, null, null).getCount();
                    if (bookCount > 0) {
                        // Nếu tác giả có sách, không cho phép xóa
                        android.widget.Toast.makeText(context, "Cannot delete author with associated books", android.widget.Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Xóa tác giả
                    int result = db.delete("Author", "Author_ID = ?", new String[]{String.valueOf(authorId)});
                    if (result > 0) {
                        // Xóa thành công, cập nhật danh sách
                        authorList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, authorList.size());
                        android.widget.Toast.makeText(context, "Author deleted successfully", android.widget.Toast.LENGTH_SHORT).show();
                    } else {
                        android.widget.Toast.makeText(context, "Failed to delete author", android.widget.Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, addressTextView;
        Button updateButton, deleteButton;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            updateButton = itemView.findViewById(R.id.updateButton);
            deleteButton = itemView.findViewById(R.id.deleteButton); // Khởi tạo nút Delete
        }
    }
}
