package com.example.assios.mobimaging;

import java.util.HashMap;

/**
 * Created by assios on 3/14/15.
 */
public class FENCreator {

    public static int coordinate(int c) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>() {{
            put(1, 32);
            put(3, 31);
            put(5, 30);
            put(7, 29);
            put(8, 28);
            put(10, 27);
            put(12, 26);
            put(14, 25);
            put(16, 24);
            put(18, 23);
            put(20, 22);
            put(22, 21);
            put(23, 20);
            put(25, 19);
            put(27, 18);
            put(29, 17);
            put(32, 16);
            put(34, 15);
            put(36, 14);
            put(38, 13);
            put(39, 12);
            put(41, 11);
            put(43, 10);
            put(45, 9);
            put(48, 8);
            put(50, 7);
            put(52, 6);
            put(54, 5);
            put(55, 4);
            put(57, 3);
            put(59, 2);
            put(61, 1);
        }};

        return map.get(c);

    }

}
