package com.hms.atbotizmozel.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hms.atbotizmozel.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReportFragment extends Fragment {

    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;

    @BindView(R.id.leftLayout1)
    LinearLayout leftLayout1;
    @BindView(R.id.leftLayout2)
    LinearLayout leftLayout2;
    @BindView(R.id.leftLayout3)
    LinearLayout leftLayout3;
    @BindView(R.id.leftLayout4)
    LinearLayout leftLayout4;
    @BindView(R.id.leftLayout5)
    LinearLayout leftLayout5;
    @BindView(R.id.leftLayout6)
    LinearLayout leftLayout6;
    @BindView(R.id.leftLayout7)
    LinearLayout leftLayout7;

    @BindView(R.id.rightLayout1)
    LinearLayout rightLayout1;
    @BindView(R.id.rightLayout2)
    LinearLayout rightLayout2;
    @BindView(R.id.rightLayout3)
    LinearLayout rightLayout3;
    @BindView(R.id.rightLayout4)
    LinearLayout rightLayout4;
    @BindView(R.id.rightLayout5)
    LinearLayout rightLayout5;
    @BindView(R.id.rightLayout6)
    LinearLayout rightLayout6;
    @BindView(R.id.rightLayout7)
    LinearLayout rightLayout7;

    @BindView(R.id.leftKeyTxt1)
    TextView leftKeyTxt1;
    @BindView(R.id.leftKeyTxt2)
    TextView leftKeyTxt2;
    @BindView(R.id.leftKeyTxt3)
    TextView leftKeyTxt3;
    @BindView(R.id.leftKeyTxt4)
    TextView leftKeyTxt4;
    @BindView(R.id.leftKeyTxt5)
    TextView leftKeyTxt5;
    @BindView(R.id.leftKeyTxt6)
    TextView leftKeyTxt6;
    @BindView(R.id.leftKeyTxt7)
    TextView leftKeyTxt7;
    @BindView(R.id.leftValueTxt1)
    TextView leftValueTxt1;
    @BindView(R.id.leftValueTxt2)
    TextView leftValueTxt2;
    @BindView(R.id.leftValueTxt3)
    TextView leftValueTxt3;
    @BindView(R.id.leftValueTxt4)
    TextView leftValueTxt4;
    @BindView(R.id.leftValueTxt5)
    TextView leftValueTxt5;
    @BindView(R.id.leftValueTxt6)
    TextView leftValueTxt6;
    @BindView(R.id.leftValueTxt7)
    TextView leftValueTxt7;

    @BindView(R.id.rightKeyTxt1)
    TextView rightKeyTxt1;
    @BindView(R.id.rightKeyTxt2)
    TextView rightKeyTxt2;
    @BindView(R.id.rightKeyTxt3)
    TextView rightKeyTxt3;
    @BindView(R.id.rightKeyTxt4)
    TextView rightKeyTxt4;
    @BindView(R.id.rightKeyTxt5)
    TextView rightKeyTxt5;
    @BindView(R.id.rightKeyTxt6)
    TextView rightKeyTxt6;
    @BindView(R.id.rightKeyTxt7)
    TextView rightKeyTxt7;
    @BindView(R.id.rightValueTxt1)
    TextView rightValueTxt1;
    @BindView(R.id.rightValueTxt2)
    TextView rightValueTxt2;
    @BindView(R.id.rightValueTxt3)
    TextView rightValueTxt3;
    @BindView(R.id.rightValueTxt4)
    TextView rightValueTxt4;
    @BindView(R.id.rightValueTxt5)
    TextView rightValueTxt5;
    @BindView(R.id.rightValueTxt6)
    TextView rightValueTxt6;
    @BindView(R.id.rightValueTxt7)
    TextView rightValueTxt7;

    private LinearLayout[] leftLayouts;
    private LinearLayout[] rightLayouts;
    private TextView[] leftKeyTxts;
    private TextView[] rightKeyTxts;
    private TextView[] leftValueTxts;
    private TextView[] rightValueTxts;

    private boolean created = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        ButterKnife.bind(this, view);

        leftLayouts = new LinearLayout[]{leftLayout1, leftLayout2, leftLayout3, leftLayout4, leftLayout5, leftLayout6, leftLayout7};
        rightLayouts = new LinearLayout[]{rightLayout7, rightLayout6, rightLayout5, rightLayout4, rightLayout3, rightLayout2, rightLayout1};
        leftKeyTxts = new TextView[]{leftKeyTxt1, leftKeyTxt2, leftKeyTxt3, leftKeyTxt4, leftKeyTxt5, leftKeyTxt6, leftKeyTxt7};
        rightKeyTxts = new TextView[]{rightKeyTxt7, rightKeyTxt6, rightKeyTxt5, rightKeyTxt4, rightKeyTxt3, rightKeyTxt2, rightKeyTxt1};
        leftValueTxts = new TextView[]{leftValueTxt1, leftValueTxt2, leftValueTxt3, leftValueTxt4, leftValueTxt5, leftValueTxt6, leftValueTxt7};
        rightValueTxts = new TextView[]{rightValueTxt7, rightValueTxt6, rightValueTxt5, rightValueTxt4, rightValueTxt3, rightValueTxt2, rightValueTxt1};

        created = true;
        contentLayout.setVisibility(View.GONE);
        return view;
    }

    public void updateReport(String[] keys, String values[]) {

        if (created) {
            contentLayout.setVisibility(View.VISIBLE);
            if (keys != null) {
                int rightStart = keys.length / 2;
                for (int i = 0; i < rightStart; i++) {
                    leftKeyTxts[i].setText(keys[i]);
                    leftLayouts[i].setVisibility(View.VISIBLE);
                }
                if(rightStart > 4) {
                    rightLayouts = new LinearLayout[]{rightLayout7, rightLayout6, rightLayout5, rightLayout4, rightLayout3, rightLayout2, rightLayout1};
                    rightKeyTxts = new TextView[]{rightKeyTxt7, rightKeyTxt6, rightKeyTxt5, rightKeyTxt4, rightKeyTxt3, rightKeyTxt2, rightKeyTxt1};
                    rightValueTxts = new TextView[]{rightValueTxt7, rightValueTxt6, rightValueTxt5, rightValueTxt4, rightValueTxt3, rightValueTxt2, rightValueTxt1};
                } else{
                    rightLayouts = new LinearLayout[]{rightLayout1, rightLayout2, rightLayout3, rightLayout4, rightLayout5, rightLayout6, rightLayout7};
                    rightKeyTxts = new TextView[]{rightKeyTxt1, rightKeyTxt2, rightKeyTxt3, rightKeyTxt4, rightKeyTxt5, rightKeyTxt6, rightKeyTxt7};
                    rightValueTxts = new TextView[]{rightValueTxt1, rightValueTxt2, rightValueTxt3, rightValueTxt4, rightValueTxt5, rightValueTxt6, rightValueTxt7};
                }
                for (int i = rightStart; i < keys.length; i++) {
                    rightKeyTxts[i - rightStart].setText(keys[i]);
                    rightLayouts[i - rightStart].setVisibility(View.VISIBLE);
                }
            }
            if (values != null) {
                int rightStart = values.length / 2;
                for (int i = 0; i < rightStart; i++) {
                    leftValueTxts[i].setText(values[i]);
                }

                if(rightStart > 4) {
                    rightLayouts = new LinearLayout[]{rightLayout7, rightLayout6, rightLayout5, rightLayout4, rightLayout3, rightLayout2, rightLayout1};
                    rightKeyTxts = new TextView[]{rightKeyTxt7, rightKeyTxt6, rightKeyTxt5, rightKeyTxt4, rightKeyTxt3, rightKeyTxt2, rightKeyTxt1};
                    rightValueTxts = new TextView[]{rightValueTxt7, rightValueTxt6, rightValueTxt5, rightValueTxt4, rightValueTxt3, rightValueTxt2, rightValueTxt1};
                } else{
                    rightLayouts = new LinearLayout[]{rightLayout1, rightLayout2, rightLayout3, rightLayout4, rightLayout5, rightLayout6, rightLayout7};
                    rightKeyTxts = new TextView[]{rightKeyTxt1, rightKeyTxt2, rightKeyTxt3, rightKeyTxt4, rightKeyTxt5, rightKeyTxt6, rightKeyTxt7};
                    rightValueTxts = new TextView[]{rightValueTxt1, rightValueTxt2, rightValueTxt3, rightValueTxt4, rightValueTxt5, rightValueTxt6, rightValueTxt7};
                }
                for (int i = rightStart; i < values.length; i++) {
                    rightValueTxts[i - rightStart].setText(values[i]);
                }
            }
            if (values == null && keys == null) {
                contentLayout.setVisibility(View.GONE);
                for (int i = 0; i < leftLayouts.length; i++) {
                    leftLayouts[i].setVisibility(View.GONE);
                    rightLayouts[i].setVisibility(View.GONE);
                }
            }

        }
    }
}
