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
 * Use the {@link FamilyMembersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FamilyMembersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static MediaPlayer audioFile;
    static AudioManager fmAudioManager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FamilyMembersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FamilyMembersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FamilyMembersFragment newInstance(String param1, String param2) {
        FamilyMembersFragment fragment = new FamilyMembersFragment();
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
        View FamilyMembersRootView = inflater.inflate(R.layout.activity_family_members, container, false);
        fmAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        final AudioManager.OnAudioFocusChangeListener fmFCL = new AudioManager.OnAudioFocusChangeListener() {
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
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        audioFile.pause();
                        break;

                }
            }
        };

        ArrayList<Word> familyMmebersList = new ArrayList<>();
        familyMmebersList.add(new Word("Father", "әpә", R.raw.family_father, R.drawable.family_father));
        familyMmebersList.add(new Word("Mother", "әṭa", R.raw.family_mother, R.drawable.family_mother));
        familyMmebersList.add(new Word("Son", "angsi", R.raw.family_son, R.drawable.family_son));
        familyMmebersList.add(new Word("daughter", "tune", R.raw.family_daughter, R.drawable.family_daughter));
        familyMmebersList.add(new Word("older brother", "taachi", R.raw.family_older_brother, R.drawable.family_older_brother));
        familyMmebersList.add(new Word("younger brother", "chalitti", R.raw.family_younger_brother, R.drawable.family_younger_brother));
        familyMmebersList.add(new Word("older sister", "teṭe", R.raw.family_older_sister, R.drawable.family_older_sister));
        familyMmebersList.add(new Word("younger sister ", "kolliti", R.raw.family_younger_sister, R.drawable.family_younger_sister));
        familyMmebersList.add(new Word("grandmother", "ama", R.raw.family_grandmother, R.drawable.family_grandmother));
        familyMmebersList.add(new Word("grandfather", "paapa", R.raw.family_grandfather, R.drawable.family_grandfather));


        CustomArrayAdapter familyMemeberArrayAdapter = new CustomArrayAdapter(getContext(), R.color.FamilyMemberButton, familyMmebersList);
        ListView familyMembersListView = (ListView) FamilyMembersRootView.findViewById(R.id.family_members_List_View);
        familyMembersListView.setAdapter(familyMemeberArrayAdapter);
        familyMembersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word currentItem = (Word) parent.getItemAtPosition(position);
                //mediaPlayers can be released from any STATE , null is not even a state
                //releasing resources before creating a new MediaPlayer object
                releaseMediaFile();
                //request audio focus
                fmAudioManager.requestAudioFocus(fmFCL, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                //creating MediaPlayer Object
                audioFile = MediaPlayer.create(getContext(), currentItem.getAudioResID());
                audioFile.start();
                audioFile.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        releaseMediaFile();
                        fmAudioManager.abandonAudioFocus(fmFCL);
                    }
                });

            }
        });



        return FamilyMembersRootView ;
    }

    private void releaseMediaFile() {
        if (audioFile != null) {
            audioFile.release();
        }
        audioFile = null;
    }
}