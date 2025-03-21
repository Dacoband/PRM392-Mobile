package com.example.bookmanagementapp.adapter;

import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookmanagementapp.R;
import com.example.bookmanagementapp.database.DatabaseHelper;
import com.example.bookmanagementapp.model.Author;
import java.util.List;

import java.util.List;
public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder> {
    private List<Author> authorList;
    private OnAuthorActionListener listener;
    private DatabaseHelper dbHelper;

    public interface OnAuthorActionListener {
        void onUpdateAuthor(int authorId);
    }

    public AuthorAdapter(List<Author> authorList, OnAuthorActionListener listener, DatabaseHelper dbHelper) {
        this.authorList = authorList;
        this.listener = listener;
        this.dbHelper = dbHelper;
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

        // Xử lý sự kiện nhấn nút Update
        holder.updateButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUpdateAuthor(author.getId());
            }
        });

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
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Kiểm tra xem tác giả có sách liên quan không
        int bookCount = db.query("Book", null, "Author_ID = ?", new String[]{String.valueOf(authorId)}, null, null, null).getCount();
        if (bookCount > 0) {
            // Nếu tác giả có sách, không cho phép xóa
            android.widget.Toast.makeText(dbHelper.getContext(), "Cannot delete author with associated books", android.widget.Toast.LENGTH_SHORT).show();
            return;
        }

        // Xóa tác giả
        int result = db.delete("Author", "Author_ID = ?", new String[]{String.valueOf(authorId)});
        if (result > 0) {
            // Xóa thành công, cập nhật danh sách
            authorList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, authorList.size());
            android.widget.Toast.makeText(dbHelper.getContext(), "Author deleted successfully", android.widget.Toast.LENGTH_SHORT).show();
        } else {
            android.widget.Toast.makeText(dbHelper.getContext(), "Failed to delete author", android.widget.Toast.LENGTH_SHORT).show();
        }
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

