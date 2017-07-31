package com.szb.szb.Home.frag;

import com.szb.szb.R;

/**
 * Created by cwh62 on 2017-05-15.
 */

public class ImageSelector_2 {
    public int imageSelector_2(String region){
        switch (region){
            case "서울역":
                return R.drawable.new_one;
            case "삼성역":
                return R.drawable.new_two;
            case "명동역":
                return R.drawable.new_three;
            case "안국역":
                return R.drawable.new_four;
            case "혜화역":
                return R.drawable.new_five;
            case "인천역":
                return R.drawable.new_six;
            case "잠실역":
                return R.drawable.new_seven;
            case "여의도역":
                return R.drawable.new_eight;
            case "동대문역":
                return R.drawable.new_nine;
            case "Seoul Station":
                return R.drawable.new_one;
            case "Samsung Station":
                return R.drawable.new_two;
            case "Myeongdong Station":
                return R.drawable.new_three;
            case "Anguk Station":
                return R.drawable.new_four;
            case "Hyehwa Station":
                return R.drawable.new_five;
            case "Incheon Station":
                return R.drawable.new_six;
            case "Jamsil Station":
                return R.drawable.new_seven;
            case "Yeouido Station":
                return R.drawable.new_eight;
            case "Dongdaemun Station":
                return R.drawable.new_nine;
            default:
                return 0;
        }

    }
}
