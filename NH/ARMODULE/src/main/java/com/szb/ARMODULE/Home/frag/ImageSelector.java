package com.szb.ARMODULE.Home.frag;

import com.szb.ARMODULE.R;

/**
 * Created by cwh62 on 2017-05-15.
 */

public class ImageSelector {
    public int imageSelector(String region){
        switch (region){
            case "서울역":
                return R.drawable.one;
            case "삼성역":
                return R.drawable.two;
            case "명동역":
                return R.drawable.three;
            case "안국역":
                return R.drawable.four;
            case "혜화역":
                return R.drawable.five;
            case "인천역":
                return R.drawable.six;
            case "잠실역":
                return R.drawable.seven;
            case "여의도역":
                return R.drawable.eight;
            case "동대문역":
                return R.drawable.nine;
            case "Seoul Station":
                return R.drawable.one;
            case "Samsung Station":
                return R.drawable.two;
            case "Myeongdong Station":
                return R.drawable.three;
            case "Anguk Station":
                return R.drawable.four;
            case "Hyehwa Station":
                return R.drawable.five;
            case "Incheon Station":
                return R.drawable.six;
            case "Jamsil Station":
                return R.drawable.seven;
            case "Yeouido Station":
                return R.drawable.eight;
            case "Dongdaemun Station":
                return R.drawable.nine;
            default:
                return 0;
        }

    }
}
