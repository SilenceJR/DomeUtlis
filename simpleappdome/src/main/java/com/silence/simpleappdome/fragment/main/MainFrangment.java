package com.silence.simpleappdome.fragment.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.silence.simpleappdome.R;
import com.silence.simpleappdome.fragment.BaseFragment;
import com.silence.simpleappdome.utils.ConstantUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @作者: PJ
 * @创建时间: 2017/11/3 / 17:21
 * @描述: 这是一个 MainFrangment 类.
 */
public class MainFrangment extends BaseFragment {
    @InjectView(R.id.home_month_income)
    TextView mHomeMonthIncome;
    @InjectView(R.id.test_day)
    TextView mTestDay;
    @InjectView(R.id.home_day_income)
    TextView mHomeDayIncome;
    @InjectView(R.id.test_year)
    TextView mTestYear;
    @InjectView(R.id.home_year_income)
    TextView mHomeYearIncome;
    @InjectView(R.id.home_convert_percent)
    TextView mHomeConvertPercent;
    @InjectView(R.id.home_use_percent)
    TextView mHomeUsePercent;
    @InjectView(R.id.home_online_park)
    TextView mHomeOnlinePark;
    @InjectView(R.id.home_offline_park)
    TextView mHomeOfflinePark;
    @InjectView(R.id.home_oc_image)
    ImageView mHomeOcImage;
    @InjectView(R.id.home_operation_center)
    RelativeLayout mHomeOperationCenter;
    @InjectView(R.id.home_cc_image)
    ImageView mHomeCcImage;
    @InjectView(R.id.home_consume_center)
    RelativeLayout mHomeConsumeCenter;
    @InjectView(R.id.home_mc_image)
    ImageView mHomeMcImage;
    @InjectView(R.id.home_manager_center)
    RelativeLayout mHomeManagerCenter;
    @InjectView(R.id.home_dc_image)
    ImageView mHomeDcImage;
    @InjectView(R.id.home_data_center)
    RelativeLayout mHomeDataCenter;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_main, null);
        setListener();
        return view;
    }

    private void setListener() {
        mHomeOperationCenter.setOnClickListener(this);
        mHomeConsumeCenter.setOnClickListener(this);
        mHomeManagerCenter.setOnClickListener(this);
        mHomeDataCenter.setOnClickListener(this);

    }

    @Override
    protected Object requestData() {
        return ConstantUtil.STATE_SUCCESSED;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onClick(View view) {
    }
}
