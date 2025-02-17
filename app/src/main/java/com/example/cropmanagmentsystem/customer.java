package com.example.cropmanagmentsystem;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.razorpay.PaymentResultListener;

public class customer extends AppCompatActivity implements PaymentResultListener {
private BottomNavigationView bnView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer);
        bnView=findViewById(R.id.bottom_nav_customer);
        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id =item.getItemId();
                if(id==R.id.nav_crop_customer){
                    load_admin_freg(new customer_crops(),false);
                } else if (id==R.id.nav_cart_customer) {
                    load_admin_freg(new customer_cart(),false);
                } else if (id==R.id.nav_orders_customer) {
                    load_admin_freg(new customer_OrdersDetails(),false);
                } else {
                    //for profile details
                    load_admin_freg(new customer_profile(),true);
                }
                return true;
            }
        });
        bnView.setSelectedItemId(R.id.nav_home_admin);



    }

    private void load_admin_freg(Fragment fragment, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        // Replace the fragment in the container
        ft.replace(R.id.container_customer, fragment);

        // If flag is true, add the fragment transaction to the back stack
        if (addToBackStack) {
            ft.addToBackStack(null);  // Null means it will use the default back stack name
        }

        // Commit the transaction
        ft.commit();
    }

    @Override
    public void onPaymentSuccess(String paymentID) {
        String paymentStatus = "Paid";
        String paymentMethod = "Online Payment";
        String paymentDateTime = String.valueOf(System.currentTimeMillis());

        // Find the customer_cart fragment and call updatePaymentStatus
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_customer);
        if (fragment instanceof customer_cart) {
            // Call the updatePaymentStatus method in the customer_cart fragment
            ((customer_cart) fragment).updatePaymentStatus(paymentStatus, paymentMethod, paymentDateTime, paymentID);
        }
    }

    @Override
    public void onPaymentError(int code, String description) {
        Toast.makeText(this, "Payment Failed: " + description, Toast.LENGTH_SHORT).show();

        // Notify the customer_cart fragment
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_customer);
        if (fragment instanceof customer_cart) {
            // Call the updatePaymentStatus method in the customer_cart fragment
            ((customer_cart) fragment).updatePaymentStatus("Payment Failed", "Online Payment", "", "");
        }
    }





}