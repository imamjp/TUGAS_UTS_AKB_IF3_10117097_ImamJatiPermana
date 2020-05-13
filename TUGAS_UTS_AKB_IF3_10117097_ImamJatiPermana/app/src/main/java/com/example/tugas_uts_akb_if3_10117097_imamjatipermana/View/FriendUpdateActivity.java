package com.example.tugas_uts_akb_if3_10117097_imamjatipermana.View;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tugas_uts_akb_if3_10117097_imamjatipermana.Database.FriendHelper;
import com.example.tugas_uts_akb_if3_10117097_imamjatipermana.Database.friend;
import com.example.tugas_uts_akb_if3_10117097_imamjatipermana.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.tugas_uts_akb_if3_10117097_imamjatipermana.Database.DatabaseAtribut.NoteColumns.DATE;
import static com.example.tugas_uts_akb_if3_10117097_imamjatipermana.Database.DatabaseAtribut.NoteColumns.EMAIL;
import static com.example.tugas_uts_akb_if3_10117097_imamjatipermana.Database.DatabaseAtribut.NoteColumns.Facebook;
import static com.example.tugas_uts_akb_if3_10117097_imamjatipermana.Database.DatabaseAtribut.NoteColumns.KELAS;
import static com.example.tugas_uts_akb_if3_10117097_imamjatipermana.Database.DatabaseAtribut.NoteColumns.NAMA;
import static com.example.tugas_uts_akb_if3_10117097_imamjatipermana.Database.DatabaseAtribut.NoteColumns.NIM;
import static com.example.tugas_uts_akb_if3_10117097_imamjatipermana.Database.DatabaseAtribut.NoteColumns.TELPON;

/** NIM : 10117097
 * Nama : Imam Jati Permana
 * Kelas : IF-3
 * Tanggal : 12-05-2020**/

public class FriendUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNim, edtNama, edtKelas, edtNohp, edtEmail, edtIg;
    private Button btnSubmit;

    private boolean isEdit = false;
    private friend friend;
    private int position;
    private FriendHelper friendHelper;

    public static final String EXTRA_NOTE = "extra_note";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_add);

        edtNim = findViewById(R.id.edt_nim);
        edtNama = findViewById(R.id.edt_nama);
        edtKelas = findViewById(R.id.edt_kelas);
        edtNohp = findViewById(R.id.edt_telpon);
        edtEmail = findViewById(R.id.edt_email);
        edtIg = findViewById(R.id.edt_ig);
        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        friendHelper = FriendHelper.getInstance(getApplicationContext());

        friend = getIntent().getParcelableExtra(EXTRA_NOTE);
        if (friend != null){
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
        }else {
            friend = new friend();
        }

        String actionBarTitle;
        String btnTitle;

        if (isEdit){
            actionBarTitle = "Update";
            btnTitle = "Update";
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b5b3ac")));

            if (friend != null){
                edtNim.setText(friend.getNim());
                edtNama.setText(friend.getNama());
                edtKelas.setText(friend.getKelas());
                edtNohp.setText(friend.getTelpon());
                edtEmail.setText(friend.getEmail());
                edtIg.setText(friend.getIg());
            }
        }else {
            actionBarTitle = "Add";
            btnTitle = "Save";
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b5b3ac")));
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        btnSubmit.setText(btnTitle);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit){
            String nim = edtNim.getText().toString().trim();
            String nama = edtNama.getText().toString().trim();
            String kelas = edtKelas.getText().toString().trim();
            String nohp = edtNohp.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String ig = edtIg.getText().toString().trim();

            if (TextUtils.isEmpty(nim)){
                edtNim.setError("Nim Is Empty");
                return;
            }else if (TextUtils.isEmpty(nama)){
                edtNama.setError("Nama Is Empty");
                return;
            }else if (TextUtils.isEmpty(kelas)){
                edtKelas.setError("Kelas Is Empty");
                return;
            }else if (TextUtils.isEmpty(nohp)){
                edtNohp.setError("No Hp Is Empty");
                return;
            }else if (TextUtils.isEmpty(email)){
                edtEmail.setError("E-Mail Is Empty");
                return;
            }else if (TextUtils.isEmpty(ig)){
                edtIg.setError("Instagram Is Empty");
                return;
            }

            friend.setNim(nim);;
            friend.setNama(nama);
            friend.setKelas(kelas);
            friend.setTelpon(nohp);
            friend.setEmail(email);
            friend.setIg(ig);

            Intent intent = new Intent();
            intent.putExtra(EXTRA_NOTE, friend);
            intent.putExtra(EXTRA_POSITION, position);

            ContentValues values = new ContentValues();
            values.put(NIM, nim);
            values.put(NAMA, nama);
            values.put(KELAS, kelas);
            values.put(TELPON, nohp);
            values.put(EMAIL, email);
            values.put(Facebook, ig);

            if (isEdit){
                long result = friendHelper.update(String.valueOf(friend.getId()), values);
                if (result > 0){
                    setResult(RESULT_UPDATE, intent);
                    finish();
                }else {
                    Toast.makeText(FriendUpdateActivity.this, "Failed to update data", Toast.LENGTH_SHORT).show();
                }
            }else {
                friend.setDate(getCurrentDate());
                values.put(DATE, getCurrentDate());
                long result = friendHelper.insert(values);

                if (result > 0){
                    friend.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    finish();
                }else {
                    Toast.makeText(FriendUpdateActivity.this, "Fail to add data", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit){
            getMenuInflater().inflate(R.menu.menuform, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type){
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogtitle, dialogMessage;

        if (isDialogClose){
            dialogtitle = "Cancel";
            dialogMessage ="Do you want to cancel the changes to the form?";
        }else {
            dialogMessage = "Are you sure want to delete this item?";
            dialogtitle = "Delete Friend";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogtitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isDialogClose){
                            finish();
                        }else {
                            long result = friendHelper.deleteById(String.valueOf(friend.getId()));
                            if (result > 0){
                                Intent intent = new Intent();
                                intent.putExtra(EXTRA_POSITION, position);
                                setResult(RESULT_DELETE, intent);
                                finish();
                            }else {
                                Toast.makeText(FriendUpdateActivity.this, "Failed to delete data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
