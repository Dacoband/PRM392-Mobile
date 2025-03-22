package com.example.sp25_huynhthiennhan_net1720;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Cấu hình Google Sign-In để đăng xuất
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // Thiết lập ViewPager2
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Kết nối TabLayout với ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Books");
                    break;
                case 1:
                    tab.setText("Authors");
                    break;
            }
        }).attach();

        // Nút Add Book
        findViewById(R.id.addBookButton).setOnClickListener(v -> {
            startActivity(new Intent(this, AddBookActivity.class));
        });

        // Nút Add Author
        findViewById(R.id.addAuthorButton).setOnClickListener(v -> {
            startActivity(new Intent(this, AddAuthorActivity.class));
        });
        // Xử lý sự kiện nhấn nút Logout
        findViewById(R.id.logoutButton).setOnClickListener(v -> logout());
    }
    private void logout() {
        // Đăng xuất khỏi Google Sign-In
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            // Chuyển về MainActivity sau khi đăng xuất
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}