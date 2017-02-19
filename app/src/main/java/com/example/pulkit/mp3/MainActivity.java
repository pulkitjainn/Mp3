package com.example.pulkit.mp3;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.pulkit.mp3.R.id.pause;
import static com.example.pulkit.mp3.R.id.play;
import static com.example.pulkit.mp3.R.id.stop;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    String[] items;
    Button play,pause,stop;
    MediaPlayer mp ;

    public static final String TAG = "MA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);
        play = (Button) findViewById(R.id.play);
        stop = (Button) findViewById(R.id.stop);
        pause = (Button) findViewById(R.id.pause);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
            }
        });



        final ArrayList<File> mySongs = findSongs(Environment.getExternalStorageDirectory());
        items = new String[mySongs.size()];

        Log.d(TAG, "onCreate: "+this.getFilesDir().getAbsolutePath());
        for(int i =0;i<mySongs.size();i++){
            items[i] = mySongs.get(i).getName();
        }

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(),R.layout.songs_list,R.id.textView,items);
        lv.setAdapter(adp);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(mp == null){
                    Uri u = Uri.parse(mySongs.get(position).toString());
                    mp = MediaPlayer.create(getApplicationContext(),u);
                    mp.start();


                }
                else {
                    mp.stop();
                    Uri u = Uri.parse(mySongs.get(position).toString());
                    mp = MediaPlayer.create(getApplicationContext(),u);
                    mp.start();

                }
            }
        });

    }



    public void toast(String text){
        Toast.makeText(getApplicationContext(), text , Toast.LENGTH_SHORT).show();
    }


    public ArrayList<File> findSongs(File root)
    {
        ArrayList<File> aI = new ArrayList<File>();
        File[] files = root.listFiles();
        for (File singleFile : files )
        {
                if(singleFile.isDirectory() && !singleFile.isHidden())
                {
                    aI.addAll(findSongs(singleFile));
                }
                else
                {
                    if (singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".mp3"))
                    {
                        aI.add(singleFile);
                    }
                }
        }
        return aI;
    }
}