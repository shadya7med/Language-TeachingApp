package com.example.miwokapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<Word> {

    int viewColorID;

    public CustomArrayAdapter(Context context, int viewColorID, List<Word> items) {
        super(context, 0, items);
        this.viewColorID = viewColorID;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //get Item
        final Word currentItem = getItem(position);

        //get View to be updated
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_sample_layout, parent, false);
        }
        //update View
        TextView miwokTextView = (TextView) itemView.findViewById(R.id.miwok_word);
        miwokTextView.setText(currentItem.getMiwokWord());

        TextView englishTextView = (TextView) itemView.findViewById(R.id.eng_word);
        englishTextView.setText(currentItem.getEnglishWord());

        ImageView imageView = (ImageView) itemView.findViewById(R.id.image_view);

        if (currentItem.getImgResID() != 0) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(currentItem.getImgResID());
        } else {
            imageView.setVisibility(View.GONE);
        }

        /*final MediaPlayer audioFile = MediaPlayer.create(getContext(), currentItem.getAudioResID());
        //create and attach onClick listener for each View
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (audioFile.isPlaying()) {

                    audioFile.stop();
                    try {
                        audioFile.prepare();
                    } catch (IOException e) {

                    }

                } else {

                    //create MediaPlayer object from audio file and Start it
                    audioFile.start();

                }

            }
        });*/

        //update background color of the View
        miwokTextView.setBackgroundColor(ContextCompat.getColor(getContext(), viewColorID));
        englishTextView.setBackgroundColor(ContextCompat.getColor(getContext(), viewColorID));
        //return updated View
        return itemView;
    }
}
