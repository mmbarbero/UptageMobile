package com.miguel.uptagemobile;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Future;


public class UploadActivity extends AppCompatActivity {

    Button fileSelect,submit;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        EditText titleInput = findViewById(R.id.titleId);
        EditText descriptionInput = findViewById(R.id.decriptionId);
        EditText tagsInput = findViewById(R.id.tagsId);

        Ion.getDefault(this).configure().setLogging("ion-upload", Log.DEBUG);
         fileSelect = findViewById(R.id.selectFileButton);
         submit = findViewById(R.id.submitButton);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

/**
                    JsonObject params = new JsonObject();

                        params.addProperty("videoTitle", "testtitle");
                        params.addProperty("videoDescription","testDescription");
                        params.addProperty("videoTags", "testtags");
                        params.addProperty("videoCategory", "category");
                        params.addProperty("username", "testauthor");
                        params.addProperty("videoDate", "2018-04-27T15:39:08.631Z");
                        params.addProperty("videoYCoord", "28");
                        params.addProperty("videoXCoord", "40");



                    Future uploadData = Ion.with(UploadActivity.this).load("http://192.168.5.10:8000/api/file-upload").setJsonObjectBody(params).asJsonObject()
                            .withResponse().setCallback(new FutureCallback<Response<JsonObject>>() {
                        @Override
                        public void onCompleted(Exception e, Response<JsonObject> result) {

                            try {
                                JSONObject jobj = new JSONObject(result.toString());
                                Toast.makeText(getApplicationContext(), jobj.getString("response"), Toast.LENGTH_SHORT).show();

                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                        }
                    });



**/



                    if (Build.VERSION.SDK_INT >= 23) {
                        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_GRANTED) {
                            Log.v("access","Permission is granted");

                            ProgressBar progressBar = findViewById(R.id.progressBar);

                            File f = new File(path);

                            Future uploadVideo = Ion.with(UploadActivity.this).load("http://192.168.5.10:8000/api/file-upload").progressBar(progressBar)
                                    .setMultipartParameter("videoTitle", "testtitle")
                                    .setMultipartParameter("videoDescription","testDescription")
                                    .setMultipartParameter("videoTags", "testtags")
                                    .setMultipartParameter("videoCategory", "category")
                                    .setMultipartParameter("username", "testauthor")
                                    .setMultipartParameter("videoDate", "2018-04-27T15:39:08.631Z")
                                    .setMultipartParameter("videoYCoord", "28")
                                    .setMultipartParameter("videoXCoord", "40")
                                    .setMultipartFile("videoUpload", f).asString().withResponse()
                                    .setCallback(new FutureCallback<Response<String>>() {
                                        @Override
                                        public void onCompleted(Exception e, Response<String> result) {

                                            try {
                                                JSONObject jobj = new JSONObject(result.getResult());
                                                Toast.makeText(getApplicationContext(), jobj.getString("response"), Toast.LENGTH_SHORT).show();

                                            } catch (JSONException e1) {
                                                e1.printStackTrace();
                                            }

                                        }
                                    });

                        } else {

                            Log.v("access","Permission is revoked");
                            ActivityCompat.requestPermissions(UploadActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                        }
                    }
                    else { //permission is automatically granted on sdk<23 upon installation
                        Log.v("access","Permission is granted");

                    }

                    }
            });

            fileSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fintent = new Intent(Intent.ACTION_GET_CONTENT);
                Log.v("button", "pressed");
                fintent.setType("video/*");
                try {
                    startActivityForResult(fintent, 100);
                } catch (ActivityNotFoundException e) {

                }
            }
        });

    }
    Uri myUri;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    myUri = data.getData();
                        path = getPath(this, myUri);
                        Log.v("path", "messsage: "+path);
                }
        }
    }
    private String getPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Video.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    public void submit(View view){



/**
        RequestParams rp = new RequestParams();
        rp.add("videoTitle", "testtitle");
        rp.add("videoDescription","testDescription");
        rp.add("videoTags", "testtags");
        rp.add("videoCategory", "category");
        rp.add("username", "testauthor");
        rp.add("videoDate", "2018-04-27T15:39:08.631Z");
        rp.add("videoYCoord", "28");
        rp.add("videoXCoord", "40");
        rp.add("filename", "filename");


        HttpUtils.post("/file-upload/", rp, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.v("verbose", response.toString());
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                int code = statusCode;
                if (code == 403){
                    Toast.makeText(UploadActivity.this, "Sum Tim Wong", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(UploadActivity.this,statusCode, Toast.LENGTH_SHORT).show();
                }
            }
        });
**/

    }


    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


}
