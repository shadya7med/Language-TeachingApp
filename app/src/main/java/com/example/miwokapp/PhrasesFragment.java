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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhrasesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhrasesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static MediaPlayer audioFile;
    static AudioManager phAudioManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PhrasesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhrasesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhrasesFragment newInstance(String param1, String param2) {
        PhrasesFragment fragment = new PhrasesFragment();
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
        View PhrasesRootView = inflater.inflate(R.layout.activity_phrases, container, false);
        phAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        //create onAudioFocusChange Listener to be used with requesting and abandoning Audio focus
        final AudioManager.OnAudioFocusChangeListener afcl = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                        audioFile.start();
                        break;

                    case AudioManager.AUDIOFOCUS_LOSS:
                        releaseMediaFile();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        audioFile.pause();
                        break;
                }
            }
        };

        ArrayList<Word> phrasesList = new ArrayList<>();
        phrasesList.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        phrasesList.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        phrasesList.add(new Word("My name is..", "oyaaset..", R.raw.phrase_my_name_is));
        phrasesList.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        phrasesList.add(new Word("i'm feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        phrasesList.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        phrasesList.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        phrasesList.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        phrasesList.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        phrasesList.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        phrasesList.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));


        CustomArrayAdapter phrasesArrayAdapter = new CustomArrayAdapter(getContext(), R.color.PhrasesButton, phrasesList);
        ListView phrasesListView = (ListView) PhrasesRootView.findViewById(R.id.phrases_list_view);
        phrasesListView.setAdapter(phrasesArrayAdapter);
        phrasesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word currentItem = (Word) parent.getItemAtPosition(position);
                releaseMediaFile();

                int res = phAudioManager.requestAudioFocus(afcl, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    audioFile = MediaPlayer.create(getContext(), currentItem.getAudioResID());
                    audioFile.start();
                }
            }
        });



        return PhrasesRootView ;
    }



    private void releaseMediaFile() {
        if (audioFile != null) {
            audioFile.release();
        }
        audioFile = null;
    }
}