package com.example.assios.mobimaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by assios on 3/14/15.
 */
public class FEN {

    public char turn;

    public List<Integer> white = new ArrayList<>();
    public List<Integer> black = new ArrayList<>();

    public String toString() {
        Collections.sort(white);
        Collections.sort(black);

        String string = "[FEN \"B:W";

        for (int i = 0; i < white.size(); i++) {
            string+=white.get(i);
            if (i<white.size()-1)
                string+=",";
        }

        string+=":B";

        for (int i = 0; i < black.size(); i++) {
            string+=black.get(i);
            if (i<black.size()-1)
                string+=",";
        }

        string+="\"]";

        return string;
    }

}
