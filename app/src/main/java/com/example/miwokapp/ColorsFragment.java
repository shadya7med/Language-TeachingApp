package com.example.miwokapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ColorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColorsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MediaPlayer audioFile;
    AudioManager audioManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ColorsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ColorsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ColorsFragment newInstance(String param1, String param2) {
        ColorsFragment fragment = new ColorsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View ColorsRootView =  inflater.inflate(R.layout.activity_colors, container, false);
        audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        final AudioManager.OnAudioFocusChangeListener fmFCL = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                        audioFile.start();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        releaseMediaPlayer();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        audioFile.pause();

                        break;

                }
            }
        };

        ArrayList<Word> colorsList = new ArrayList<>();
        colorsList.add(new Word("red", "wetetti", R.raw.color_red, R.drawable.color_red));
        colorsList.add(new Word("green", "chokokki", R.raw.color_green, R.drawable.color_green));
        colorsList.add(new Word("brown", "takaakki", R.raw.color_brown, R.drawable.color_brown));
        colorsList.add(new Word("gray", "topoppi", R.raw.color_gray, R.drawable.color_gray));
        colorsList.add(new Word("black", "kululli", R.raw.color_black, R.drawable.color_black));
        colorsList.add(new Word("white", "kelelli", R.raw.color_white, R.drawable.color_white));
        colorsList.add(new Word("dusty yellow", "ṭopiisә", R.raw.color_dusty_yellow, R.drawable.color_dusty_yellow));
        colorsList.add(new Word("mustard yellow", "chiwiiṭә", R.raw.color_mustard_yellow, R.drawable.color_mustard_yellow));

        CustomArrayAdapter colorsAdapter = new CustomArrayAdapter(getContext(), R.color.ColorsButton, colorsList);
        ListView colorsListView = (ListView) ColorsRootView.findViewById(R.id.colors_list_view);
        colorsListView.setAdapter(colorsAdapter);
        colorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word currentItem = (Word) parent.getItemAtPosition(position);
                audioManager.requestAudioFocus(fmFCL, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                releaseMediaPlayer();
                audioFile = MediaPlayer.create(getContext(), currentItem.getAudioResID());
                audioFile.start();
                audioFile.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
                        releaseMediaPlayer();
                        audioManager.abandonAudioFocus(fmFCL);
                    }
                });

            }
        });

        return ColorsRootView ;
    }
    private void releaseMediaPlayer() {
        if (audioFile != null) {
            audioFile.release();
        }
        audioFile = null;
    }
}