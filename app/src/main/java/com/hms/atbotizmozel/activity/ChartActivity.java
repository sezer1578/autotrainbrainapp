package com.hms.atbotizmozel.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.data.call.AtbCalls;
import com.hms.atbotizmozel.data.model.SummaryModel;
import com.hms.atbotizmozel.data.model.UserModel;
import com.hms.atbotizmozel.data.model.response.BaseResponse;
import com.hms.atbotizmozel.util.ErrorUtils;
import com.hms.atbotizmozel.util.PrefUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartActivity extends AppCompatActivity {

    @BindView(R.id.chart1)
    LineChart chart1;
    @BindView(R.id.chart2)
    LineChart chart2;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private List<SummaryModel> summaryModels;

    private ArrayList<Entry> gamma = new ArrayList<>();
    private ArrayList<Entry> beta2 = new ArrayList<>();
    private ArrayList<Entry> beta1 = new ArrayList<>();
    private ArrayList<Entry> alpha = new ArrayList<>();
    private ArrayList<Entry> theta = new ArrayList<>();
    private ArrayList<Entry> workout = new ArrayList<>();
    private ArrayList<Entry> wellness = new ArrayList<>();
    private ArrayList<Entry> score = new ArrayList<>();

    private SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/dd/mm HH:mm:ss");
    private SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        userModel = PrefUtils.with(this).getUserModel();
        load();
    }

    private void load() {
        AtbCalls.getAtbSummaryList(this, userModel, new Callback<BaseResponse<List<SummaryModel>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<SummaryModel>>> call, Response<BaseResponse<List<SummaryModel>>> response) {
                if (ErrorUtils.isSuccess(ChartActivity.this, response)) {
                    summaryModels = response.body().data;
                    if (summaryModels == null || summaryModels.size() == 0) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ChartActivity.this, getString(R.string.have_no_data_for_chart), Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < summaryModels.size(); i++) {
                            SummaryModel model = summaryModels.get(i);
                            gamma.add(new Entry(i, model.avggamma));
                            beta2.add(new Entry(i, model.avgbeta2));
                            beta1.add(new Entry(i, model.avgbeta1));
                            alpha.add(new Entry(i, model.avgalpha));
                            theta.add(new Entry(i, model.avgtheta));
                            workout.add(new Entry(i, model.workout));
                            wellness.add(new Entry(i, model.wellness));
                            score.add(new Entry(i, model.score));
                        }
                        init();
                    }
                } else {
                    Toast.makeText(ChartActivity.this, getString(R.string.error_unexpected), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<SummaryModel>>> call, Throwable t) {
                ErrorUtils.parseThrowable(ChartActivity.this, t);
            }
        });
    }

    private void init() {
        progressBar.setVisibility(View.GONE);
        chart1.setVisibility(View.VISIBLE);
        chart2.setVisibility(View.VISIBLE);

        chart1.setDrawGridBackground(false);
        chart1.getDescription().setEnabled(false);
        chart1.setTouchEnabled(true);
        chart1.setDragEnabled(true);
        chart1.setScaleEnabled(true);
        chart1.setPinchZoom(true);
        YAxis leftAxis1 = chart1.getAxisLeft();
        leftAxis1.removeAllLimitLines();
        leftAxis1.setDrawZeroLine(true);
        leftAxis1.setDrawLimitLinesBehindData(true);
        chart1.getXAxis().setEnabled(true);
        chart1.getAxisRight().setEnabled(false);

        XAxis xAxis1 = chart1.getXAxis();
        xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis1.setTextSize(8f);
        xAxis1.setTextColor(Color.BLACK);
        xAxis1.setDrawAxisLine(false);
        xAxis1.setDrawGridLines(true);
        xAxis1.setCenterAxisLabels(false);
        xAxis1.setGranularity(1f); // one hour
        xAxis1.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                try {
                    SummaryModel model = summaryModels.get((int) value);
//                    Date date = inputFormat.parse(model.date);
                    String axisStr = model.session + ". " + getString(R.string.session) /*+ "\n" + outputFormat.format(date)*/;
                    return axisStr;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }
        });

        chart2.setDrawGridBackground(false);
        chart2.getDescription().setEnabled(false);
        chart2.setTouchEnabled(true);
        chart2.setDragEnabled(true);
        chart2.setScaleEnabled(true);
        chart2.setPinchZoom(true);
        YAxis leftAxis2 = chart2.getAxisLeft();
        leftAxis2.removeAllLimitLines();
        leftAxis2.setDrawZeroLine(true);
        leftAxis2.setDrawLimitLinesBehindData(true);
        chart2.getXAxis().setEnabled(true);
        chart2.getAxisRight().setEnabled(false);
        XAxis xAxis2 = chart2.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setTextSize(8f);
        xAxis2.setTextColor(Color.BLACK);
        xAxis2.setDrawAxisLine(false);
        xAxis2.setDrawGridLines(true);
        xAxis2.setCenterAxisLabels(false);
        xAxis2.setGranularity(1f); // one hour
        xAxis2.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                try {
                    SummaryModel model = summaryModels.get((int) value);
//                    Date date = inputFormat.parse(model.date);
                    String axisStr = model.session + ". " + getString(R.string.session) /*+ "\n" + outputFormat.format(date)*/;
                    return axisStr;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }
        });

        ArrayList<ArrayList<Entry>> values1 = new ArrayList<>();
        values1.add(theta);
        values1.add(alpha);
        values1.add(beta1);
        values1.add(beta2);
        values1.add(gamma);

        setData(chart1, values1, new String[]{"Theta", "Alpha", "Beta 1", "Beta 2", "Gamma"},
                new int[]{ContextCompat.getColor(this, R.color.orange),
                        ContextCompat.getColor(this, R.color.red), ContextCompat.getColor(this, R.color.green),
                        ContextCompat.getColor(this, R.color.blue), ContextCompat.getColor(this, R.color.dark_pink)});

        ArrayList<ArrayList<Entry>> values2 = new ArrayList<>();
        values2.add(workout);
        values2.add(wellness);
        values2.add(score);
        setData(chart2, values2, new String[]{"Workout", "Wellness", "Score"},
                new int[]{ContextCompat.getColor(this, R.color.colorAccent),
                        ContextCompat.getColor(this, R.color.red), ContextCompat.getColor(this, R.color.green)});

        chart1.animateX(1000);
        chart1.getLegend().setForm(Legend.LegendForm.LINE);
        chart2.animateX(1000);
        chart2.getLegend().setForm(Legend.LegendForm.LINE);
    }

    private void setData(LineChart chart, ArrayList<ArrayList<Entry>> values, String[] title, int[] color) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            LineDataSet set1 = new LineDataSet(values.get(i), title[i]);
            set1.setDrawIcons(false);
            set1.setColor(color[i]);
            set1.setDrawCircles(false);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(false);
            set1.setDrawValues(false);
            set1.setFormSize(15.f);
            dataSets.add(set1);
        }

        LineData data = new LineData(dataSets);
        chart.setData(data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.profile:
                Intent profileIntent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(profileIntent);
                return true;
            case R.id.action_logout:
                PrefUtils.with(ChartActivity.this).savePassword("");
                PrefUtils.with(ChartActivity.this).saveUserName("");
                PrefUtils.with(ChartActivity.this).removeUserModel();
                PrefUtils.with(ChartActivity.this).clear();
                Intent i = new Intent(getBaseContext(),LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
