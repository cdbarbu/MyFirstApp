package com.example.myfirstapp;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String EXTRA_MESSAGE2 = "com.example.myfirstapp.MESSAGE2";
    private static final int PERMISSIONS_REQUEST_WRITE_CONTACTS = 100;

    String message;
    String message2;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        intent = new Intent(this, DisplayMessageActivity.class);

        EditText editText = (EditText) findViewById(R.id.editText);
        message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);

        EditText editText2 = (EditText) findViewById(R.id.editText2);
        message2 = editText2.getText().toString();
        intent.putExtra(EXTRA_MESSAGE2, message2);

    /*    try {
            ContentValues values = new ContentValues();
            values.put(ContactsContract.Data.RAW_CONTACT_ID, 001);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, message2);
            values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM);
            values.put(ContactsContract.CommonDataKinds.Phone.LABEL, message);
            values.put(ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID, message);
            Uri dataUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[] {Manifest.permission.WRITE_CONTACTS}, PERMISSIONS_REQUEST_WRITE_CONTACTS);
//            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
//        }

       try {
            ContentValues values = new ContentValues();
//            values.put(Contacts.People.NUMBER, message2);
//            values.put(Contacts.People.TYPE, /*ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM*/ Contacts.People.TYPE_MOBILE);
            values.put(Contacts.People.LABEL, message);
            values.put(Contacts.People.NAME, message);
            Uri dataUri = getContentResolver().insert(Contacts.People.CONTENT_URI, values);

            Uri updateUri = Uri.withAppendedPath(dataUri, Contacts.People.Phones.CONTENT_DIRECTORY);
            values.clear();
            values.put(Contacts.People.Phones.TYPE, Contacts.People.TYPE_MOBILE);
            values.put(Contacts.People.NUMBER, message2);
            updateUri = getContentResolver().insert(updateUri, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        startActivity(intent);

    }

}
