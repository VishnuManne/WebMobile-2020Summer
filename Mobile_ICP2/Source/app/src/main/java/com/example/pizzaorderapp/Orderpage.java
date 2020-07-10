package com.example.pizzaorderapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.BooleanUtils;

import androidx.appcompat.app.AppCompatActivity;

public class Orderpage extends AppCompatActivity {
    private static final Integer base_rate = 10;
    private static final Integer chicken_price = 4;
    private static final Integer pepperoni_price = 2;
    private static final Integer onion_price = 3;
    private static final Integer pineapple_price = 2;
    float total_price;
    Integer quantity = 1;
    String orderSummary;

    EditText customer_name;
    TextView selected_quantity;
    CheckBox chicken, pepperoni, onion, pineapple;
    RadioButton smallpizza, mediumpizza, largepizza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_page);

        selected_quantity = findViewById(R.id.quantity_text_view);
        customer_name = findViewById(R.id.name);
        chicken = findViewById(R.id.ck_check);
        pepperoni = findViewById(R.id.pep_check);
        onion = findViewById(R.id.oni_check);
        pineapple = findViewById(R.id.pin_check);
        smallpizza = findViewById(R.id.small_pizza);
        mediumpizza = findViewById(R.id.medium_pizza);
        largepizza = findViewById(R.id.large_pizza);
    }

    private boolean isUserEmpty() {
        String userName = customer_name.getText().toString();
        if (userName == null || userName.isEmpty()) {
            Context context = getApplicationContext();
            String upperLimitToast = "Customer Name is Required";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return true;
        }
        return false;
    }

    private String fetchDetails() {
        boolean chicken_flag = chicken.isChecked();
        boolean pepperoni_flag = pepperoni.isChecked();
        boolean onion_flag = onion.isChecked();
        boolean pineapple_flag = pineapple.isChecked();
        boolean small_flag = smallpizza.isChecked();
        boolean medium_flag = mediumpizza.isChecked();
        boolean large_flag = largepizza.isChecked();

        total_price = calculate_price(chicken_flag, pepperoni_flag, onion_flag, pineapple_flag, quantity);

        return fetchOrderSummary(customer_name.getText().toString(), chicken_flag, pepperoni_flag, onion_flag, pineapple_flag,small_flag,medium_flag,large_flag, total_price);
    }

    public void orderSummary(View view) {
        if (!isUserEmpty()) {
            orderSummary = fetchDetails();
            Intent intent = new Intent(Orderpage.this, Summary.class);
            intent.putExtra("orderSummary", orderSummary);
            startActivity(intent);
        }
    }

    public void orderPizzaMain(View view) {
        if (!isUserEmpty()) {
            orderSummary = fetchDetails();
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"customer@gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order Summary");
            emailIntent.putExtra(Intent.EXTRA_TEXT, orderSummary);
            startActivity(Intent.createChooser(emailIntent, ""));
        }
    }
    private String fetchOrderSummary(String userName, boolean chicken_flag, boolean pepperoni_flag,
                                     boolean onion_flag, boolean pineapple_flag,boolean small_flag,boolean medium_flag, boolean large_flag, float total_price) {
        return getString(R.string.order_summary_name,userName) +"\n"+
                getString(R.string.order_summary_small,BooleanUtils.toStringYesNo(small_flag)) +"\n"+
                getString(R.string.order_summary_medium,BooleanUtils.toStringYesNo(medium_flag)) +"\n"+
                getString(R.string.order_summary_large,BooleanUtils.toStringYesNo(large_flag)) +"\n"+
                getString(R.string.order_summary_chicken, BooleanUtils.toStringYesNo(chicken_flag))+"\n"+
                getString(R.string.order_summary_pepperoni,BooleanUtils.toStringYesNo(pepperoni_flag)) +"\n"+
                getString(R.string.order_summary_onion,BooleanUtils.toStringYesNo(onion_flag)) +"\n"+
                getString(R.string.order_summary_pineapple,BooleanUtils.toStringYesNo(pineapple_flag)) +"\n"+
                getString(R.string.order_summary_quantity,quantity)+"\n"+
                getString(R.string.order_summary_total_price,total_price) +"\n"+
                getString(R.string.thank_you);
    }

    private float calculate_price(boolean chicken, boolean pepperoni, boolean onion, boolean pineapple, Integer quantity) {
        int basePrice = base_rate;
        if (chicken) {
            basePrice += chicken_price;
        }
        if (pepperoni) {
            basePrice += pepperoni_price;
        }
        if (onion){
            basePrice += onion_price;
        }
        if(pineapple){
            basePrice += pineapple_price;
        }
        return quantity * basePrice;
    }

    public void more(View view) {
        if (quantity < 10) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("PizzaOrder", "Please select less than 20 Pizzas");
            Context context = getApplicationContext();
            String lowerLimitToast = "Please select less than 20 Pizzas";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }


    public void less(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("CoffeOrder", "Please select atleast one Pizza");
            Context context = getApplicationContext();
            String upperLimitToast = "Please select atleast 1 Pizza";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }
    
    private void display(int number) {
        selected_quantity.setText("" + number);
    }

}