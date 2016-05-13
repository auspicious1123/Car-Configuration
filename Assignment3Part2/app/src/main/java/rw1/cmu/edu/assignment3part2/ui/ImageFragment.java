package rw1.cmu.edu.assignment3part2.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rw1.cmu.edu.assignment3part2.R;

/**
 * Created by Rui on 3/31/16.
 */
public class ImageFragment extends Fragment {
    public ImageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

}
