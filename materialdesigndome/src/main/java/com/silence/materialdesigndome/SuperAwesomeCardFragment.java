package com.silence.materialdesigndome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * @作者: PJ
 * @创建时间: 2017/10/24 / 14:17
 * @描述: 这是一个 SuperAwesomeCardFragment 类.
 */
public class SuperAwesomeCardFragment extends Fragment {

    public static final String ARG_POSITION = "position";
    private static final int[] drawables = {R.drawable.one, R.drawable.two, R.drawable.four, R.drawable
            .three};

    private int position;

    public static SuperAwesomeCardFragment newInstance(int position) {
        SuperAwesomeCardFragment superAwesomeCardFragment = new SuperAwesomeCardFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_POSITION, position);
        superAwesomeCardFragment.setArguments(bundle);
        return superAwesomeCardFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.setLayoutParams(params);

        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
        TextView textView = new TextView(getActivity());

        params.setMargins(margin, margin, margin, margin);

        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(drawables[position]);
        textView.setText("内容" + (position + 1));

        frameLayout.addView(textView);

        return frameLayout;

    }

    public static int getBackroundBitmapPosition(int position) {
        return drawables[position];
    }
}
