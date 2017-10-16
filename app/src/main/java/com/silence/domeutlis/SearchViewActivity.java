package com.silence.domeutlis;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.silence.domeutlis.R.id.searchView;

public class SearchViewActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @InjectView(searchView)
    SearchView mSearchView;
    private SearchView.SearchAutoComplete mSearchEditView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        ButterKnife.inject(this);


        mSearchEditView = mSearchView.findViewById(R.id.search_src_text);

        mTextView = ((TextView) findViewById(R.id.tv));

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mSearchView.setOnQueryTextListener(this);
        mSearchEditView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {

                    /*隐藏软键盘*/
                    mSearchView.clearFocus();

                    Editable text = mSearchEditView.getText();
                    if (!"".equals(text) && null != text) {

                        showText(text.toString().trim());
                    }
                    return true;
                }
                return false;
            }
        });

        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.onActionViewExpanded();
        mSearchView.setIconified(true);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*隐藏软键盘*/
                mSearchView.clearFocus();
                Editable text = mSearchEditView.getText();
                if (!"".equals(text) && null != text) {

                    showText(text.toString().trim());
                }
            }
        });


    }

    private void showText(String text) {
        Toast.makeText(SearchViewActivity.this, "正在搜索:" + text, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (mSearchView != null) {
            // 得到输入管理对象
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                // 这将让键盘在所有的情况下都被隐藏，但是一般我们在点击搜索按钮后，输入法都会乖乖的自动隐藏的。
                imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
            }
            mSearchView.clearFocus(); // 不获取焦点
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!"".equals(newText) && null != newText) {
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(String.format(getResources().getString(R.string.search_text), mSearchEditView.getText().toString().trim()));
        } else {
            mTextView.setVisibility(View.GONE);
        }
        return true;
    }
}
