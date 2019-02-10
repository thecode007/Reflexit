package com.reflex;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.reflex.util.BroadCastFilters;

import java.io.File;


public class HomeFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        view.findViewById(R.id.send).setOnClickListener(c -> {
            File file = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath(),
                    ((EditText)view.findViewById(R.id.broadcast)).getText().toString());
            Toast.makeText(getContext(),
                    "file:" + file.exists()+":" + file.getAbsolutePath(),
                    Toast.LENGTH_SHORT).show();

            deleteRecursive(file);
        });



        return view;
    }

    void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()){
            for (File child : fileOrDirectory.listFiles()){
                Log.wtf("deleting",child.getAbsolutePath());
                deleteRecursive(child);
            }
        }
        fileOrDirectory.delete();
    }
}
