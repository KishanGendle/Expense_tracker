package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.expensemanager.databinding.ActivityAddExpenseBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Document;

import java.util.Calendar;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {
    ActivityAddExpenseBinding binding;
    private String type;
    private ExpenseModdel expenseModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        type = getIntent().getStringExtra("type");
        expenseModel = (ExpenseModdel) getIntent().getSerializableExtra("model");

        if (type==null) {
            type = expenseModel.getType();
            binding.amount.setText(String.valueOf(expenseModel.getAmount()));
            binding.category.setText(expenseModel.getCategoty());
            binding.note.setText(expenseModel.getNote());

        }

        if (type.equals("Income")) {
            binding.incomeRadio.setChecked(true);
        } else {
            binding.expenseRadio.setChecked(true);
        }
        binding.incomeRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "Income";
            }
        });
        binding.expenseRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "Expense";
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if (expenseModel == null) {
            menuInflater.inflate(R.menu.add_menu, menu);
        } else {
            menuInflater.inflate(R.menu.update_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.saveExpense) {
            if (type!=null) {
                createExpense();

                finish();
            }else{

                updateExpense();

                finish();
            }
            return true;
        }
        if (id == R.id.deleteExpense) {
            deleteExpense();
        }
        return false;
    }

    private void deleteExpense() {
        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenseModel.getExpenseId())
                .delete();
        finish();
    }

    private void createExpense() {
        String expenseId = UUID.randomUUID().toString();
        String amount = binding.amount.getText().toString();
        String note = binding.note.getText().toString();
        String category = binding.category.getText().toString();
        boolean incomeChecked = binding.incomeRadio.isChecked();
        if(incomeChecked){
            type="Income";
        }else{
            type="Expense";
        }
        if(amount.trim().length()==0){
            binding.amount.setError("Empty");
            return;
        }
        ExpenseModdel expenseModdel = new ExpenseModdel(expenseId,note,category,type,FirebaseAuth.getInstance().getUid(),Long.parseLong(amount), Calendar.getInstance().getTimeInMillis());
        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenseId)
                .set(expenseModdel);
        finish();
    }
    private void updateExpense() {

        String expenseId = expenseModel.getExpenseId();
        String amount = binding.amount.getText().toString();
        String note = binding.note.getText().toString();
        String category = binding.category.getText().toString();


        boolean incomeChecked = binding.incomeRadio.isChecked();
        if (incomeChecked) {
            type = "Income";
        } else {
            type = "Expense";
        }


        ExpenseModdel model = new ExpenseModdel(expenseId, note, category, type, FirebaseAuth.getInstance().getUid(),Long.parseLong(amount),
                expenseModel.getTime());


        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenseId)
                .set(model);
        finish();

    }
}