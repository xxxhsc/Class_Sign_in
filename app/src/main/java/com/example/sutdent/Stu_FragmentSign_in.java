package com.example.sutdent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lmy.class_sign_in.R;

/**
 * Created by 何盛昌 on 2018/12/12.
 */

public class Stu_FragmentSign_in extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student2, container, false);
    }


}
