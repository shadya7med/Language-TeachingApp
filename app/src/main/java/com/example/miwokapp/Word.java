package com.example.miwokapp;

public class Word {
    private String englishWord;
    private String miwokWord;
    private int imgResID;
    private int audioResID;
    private int playbackImageID;


    Word(String english, String miwok, int audioResID) {
        this.englishWord = english;
        this.miwokWord = miwok;
        this.imgResID = 0;
        this.audioResID = audioResID;
        this.playbackImageID = R.drawable.outline_arrow_right_white_24;

    }

    Word(String english, String miwwok, int audioResID, int imgResID) {
        this.englishWord = english;
        this.miwokWord = miwwok;
        this.audioResID = audioResID;
        this.imgResID = imgResID;
        this.playbackImageID = R.drawable.outline_arrow_right_white_24;
    }

    public String getEnglishWord() {
        return this.englishWord;
    }

    public String getMiwokWord() {
        return this.miwokWord;
    }

    public int getImgResID() {
        return this.imgResID;
    }

    public int getAudioResID() {
        return audioResID;
    }


}
