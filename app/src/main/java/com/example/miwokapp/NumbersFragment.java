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
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NumbersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumbersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    static MediaPlayer audioFile;
    static AudioManager audioManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NumbersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NumbersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NumbersFragment newInstance(String param1, String param2) {
        NumbersFragment fragment = new NumbersFragment();
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
        View rootView = inflater.inflate(R.layout.activity_numbers, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final AudioManager.OnAudioFocusChangeListener fmFCL = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {

                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                        if (audioFile != null) {
                            audioFile.start();
                        }
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        releaseMediaFile();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        audioFile.pause();
                        break;

                }
            }
        };
        /*
        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.child_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);*/


        final ArrayList<Word> enNumbers = new ArrayList<>();
        enNumbers.add(new Word("One", "Lutti", R.raw.number_one, R.drawable.number_one));
        enNumbers.add(new Word("Two", "Otiiko", R.raw.number_two, R.drawable.number_two));
        enNumbers.add(new Word("Three", "tolookosu", R.raw.number_three, R.drawable.number_three));
        enNumbers.add(new Word("Four", "oyyisa", R.raw.number_four, R.drawable.number_four));
        enNumbers.add(new Word("Five", "massokka", R.raw.number_five, R.drawable.number_five));
        enNumbers.add(new Word("Six", "temmokka", R.raw.number_six, R.drawable.number_six));
        enNumbers.add(new Word("Seven", "kenekaku", R.raw.number_seven, R.drawable.number_seven));
        enNumbers.add(new Word("Eight", "kawinta", R.raw.number_eight, R.drawable.number_eight));
        enNumbers.add(new Word("Nine", "wo'e", R.raw.number_nine, R.drawable.number_nine));
        enNumbers.add(new Word("Ten", "na'aacha", R.raw.number_ten, R.drawable.number_ten));

        CustomArrayAdapter wordAdapter = new CustomArrayAdapter(getActivity(), R.color.NumbersButton, enNumbers);
        ListView numbersListView = (ListView) rootView.findViewById(R.id.numbers_list);
        numbersListView.setAdapter(wordAdapter);

        numbersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final View currentView = view;
                Word currentItem = (Word) parent.getItemAtPosition(position);
                releaseMediaFile();

                audioManager.requestAudioFocus(fmFCL, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                audioFile = MediaPlayer.create(getActivity(), currentItem.getAudioResID());
                audioFile.start();
                audioFile.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        releaseMediaFile();
                        audioManager.abandonAudioFocus(fmFCL);
                        ImageView playbackImage = (ImageView) currentView.findViewById(R.id.playback_image);
                        playbackImage.setImageResource(R.drawable.outline_arrow_right_white_24);
                    }
                });
                ImageView playbackImage = (ImageView) currentView.findViewById(R.id.playback_image);
                playbackImage.setImageResource(R.drawable.outline_pause_white_24);
            }

        });




        // Inflate the layout for this fragment
        return rootView ;
    }

    private void releaseMediaFile() {
        if (audioFile != null) {
            audioFile.release();
        }
        audioFile = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaFile();
    }
}