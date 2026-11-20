package com.example.pocketnotes;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    private LinearLayout messagesLayout;
    private EditText input;
    private ScrollView scrollView;
    private SQLiteDatabase database;

    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }

    private GradientDrawable background(int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(dp(radius));
        return drawable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = new DatabaseHelper(this).getWritableDatabase();

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.rgb(235, 229, 220));

        LinearLayout header = new LinearLayout(this);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(dp(18), dp(12), dp(18), dp(12));
        header.setBackgroundColor(Color.rgb(18, 40, 34));

        TextView title = new TextView(this);
        title.setText("Pocket Notes");
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setTypeface(null, Typeface.BOLD);

        TextView subtitle = new TextView(this);
        subtitle.setText("Your personal space");
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setTextSize(12);

        LinearLayout titleBox = new LinearLayout(this);
        titleBox.setOrientation(LinearLayout.VERTICAL);
        titleBox.addView(title);
        titleBox.addView(subtitle);

        header.addView(titleBox);
        root.addView(header);

        scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);

        messagesLayout = new LinearLayout(this);
        messagesLayout.setOrientation(LinearLayout.VERTICAL);
        messagesLayout.setPadding(dp(12), dp(12), dp(12), dp(12));

        scrollView.addView(messagesLayout);

        root.addView(scrollView, new LinearLayout.LayoutParams(
                -1, 0, 1
        ));

        LinearLayout bottom = new LinearLayout(this);
        bottom.setGravity(Gravity.CENTER_VERTICAL);
        bottom.setPadding(dp(8), dp(8), dp(8), dp(8));
        bottom.setBackgroundColor(Color.rgb(245, 245, 245));

        input = new EditText(this);
        input.setHint("Write a note...");
        input.setTextSize(15);
        input.setSingleLine(false);
        input.setMaxLines(4);
        input.setPadding(dp(16), dp(10), dp(16), dp(10));
        input.setBackground(background(Color.WHITE, 24));

        bottom.addView(input, new LinearLayout.LayoutParams(
                0, -2, 1
        ));

        Button send = new Button(this);
        send.setText("SEND");
        send.setTextColor(Color.WHITE);
        send.setTextSize(12);
        send.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(
                        Color.rgb(0, 120, 90)
                )
        );

        LinearLayout.LayoutParams sendParams =
                new LinearLayout.LayoutParams(-2, dp(48));
        sendParams.setMargins(dp(6), 0, 0, 0);

        bottom.addView(send, sendParams);

        root.addView(bottom);

        setContentView(root);

        send.setOnClickListener(v -> sendMessage());

        loadMessages();
    }

    private void sendMessage() {
        String text = input.getText().toString().trim();

        if (text.isEmpty()) return;

        String time = new SimpleDateFormat(
                "hh:mm a", Locale.getDefault()
        ).format(new Date());

        android.content.ContentValues values =
                new android.content.ContentValues();

        values.put("message", text);
        values.put("time", time);

        long id = database.insert("notes", null, values);

        if (id != -1) {
            input.setText("");
            hideKeyboard();
            loadMessages();
        }
    }

    private void loadMessages() {
        messagesLayout.removeAllViews();

        Cursor cursor = database.rawQuery(
                "SELECT id, message, time FROM notes ORDER BY id ASC",
                null
        );

        if (cursor.getCount() == 0) {
            TextView empty = new TextView(this);
            empty.setText("No notes yet. Send yourself a message!");
            empty.setTextColor(Color.DKGRAY);
            empty.setGravity(Gravity.CENTER);
            empty.setPadding(0, dp(40), 0, dp(40));
            messagesLayout.addView(empty);
        }

        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            String message = cursor.getString(1);
            String time = cursor.getString(2);

            addMessage(id, message, time);
        }

        cursor.close();

        scrollView.post(() ->
                scrollView.fullScroll(View.FOCUS_DOWN)
        );
    }

    private void addMessage(long id, String message, String time) {

        LinearLayout row = new LinearLayout(this);
        row.setGravity(Gravity.RIGHT);
        row.setPadding(0, dp(4), 0, dp(4));

        LinearLayout bubble = new LinearLayout(this);
        bubble.setOrientation(LinearLayout.VERTICAL);
        bubble.setPadding(dp(12), dp(8), dp(12), dp(6));
        bubble.setBackground(background(
                Color.rgb(210, 245, 205), 12
        ));

        TextView text = new TextView(this);
        text.setText(message);
        text.setTextColor(Color.BLACK);
        text.setTextSize(16);

        TextView timestamp = new TextView(this);
        timestamp.setText(time);
        timestamp.setTextColor(Color.DKGRAY);
        timestamp.setTextSize(10);
        timestamp.setGravity(Gravity.RIGHT);

        bubble.addView(text);
        bubble.addView(timestamp);

        row.addView(bubble, new LinearLayout.LayoutParams(
                -2, -2
        ));

        row.setOnLongClickListener(v -> {
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Delete note?")
                    .setMessage("This note will be permanently deleted.")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Delete", (dialog, which) -> {
                        database.delete(
                                "notes", "id=?",
                                new String[]{String.valueOf(id)}
                        );
                        loadMessages();
                    })
                    .show();
            return true;
        });

        messagesLayout.addView(row);
    }

    private void hideKeyboard() {
        InputMethodManager manager =
                (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE
                );

        if (manager != null) {
            manager.hideSoftInputFromWindow(
                    input.getWindowToken(), 0
            );
        }
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, "PocketNotes.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(
                    "CREATE TABLE notes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "message TEXT NOT NULL," +
                    "time TEXT NOT NULL)"
            );
        }

        @Override
        public void onUpgrade(
                SQLiteDatabase db, int oldVersion, int newVersion
        ) {
        }
    }
}
