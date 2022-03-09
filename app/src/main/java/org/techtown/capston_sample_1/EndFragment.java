package org.techtown.capston_sample_1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class EndFragment extends Fragment {

    TextView textInput;
    TextView styleInput;
    ImageView styleImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_end, container, false);

        textInput = (TextView) view.findViewById(R.id.textInputText);
        styleInput = (TextView) view.findViewById(R.id.textSelectedStyle);
        styleImage = (ImageView) view.findViewById(R.id.imageStyle);

        return view;
    }
}