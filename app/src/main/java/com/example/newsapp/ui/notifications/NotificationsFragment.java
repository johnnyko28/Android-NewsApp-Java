package com.example.newsapp.ui.notifications;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.newsapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnDrawLineChartTouchListener;

import java.util.ArrayList;
import java.util.Map;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    LineChart mpLineChart;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        mpLineChart=(LineChart)root.findViewById(R.id.line_chart);
        LineDataSet lineDataSet1 = new LineDataSet(dataValue1(), "Trending Chart for Coronavirus");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);

        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();

        mpLineChart.getXAxis().setTextSize(12f);
        mpLineChart.getAxisLeft().setTextSize(12f);
        mpLineChart.getAxisRight().setTextSize(12f);
        mpLineChart.getAxisLeft().setDrawGridLines(false);
        mpLineChart.getAxisRight().setDrawGridLines(false);
        mpLineChart.getXAxis().setDrawGridLines(false);
        lineDataSet1.setColor(Color.parseColor("#7F00FF"));
        lineDataSet1.setValueTextSize(8f);
        lineDataSet1.setValueTextColor(Color.parseColor("#7F00FF"));
        Legend I = mpLineChart.getLegend();
        I.setTextSize(16f);
        I.setFormSize(10f);

        return root;
    }
    private ArrayList<Entry> dataValue1(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 0));
        dataVals.add(new Entry(1, 0));
        dataVals.add(new Entry(2, 0));
        dataVals.add(new Entry(3, 0));
        dataVals.add(new Entry(4, 0));
        dataVals.add(new Entry(5, 0));
        dataVals.add(new Entry(6, 0));
        dataVals.add(new Entry(7, 0));
        dataVals.add(new Entry(8, 0));
        dataVals.add(new Entry(9, 0));
        dataVals.add(new Entry(10, 0));
        dataVals.add(new Entry(11, 0));
        dataVals.add(new Entry(12, 0));
        dataVals.add(new Entry(13, 0));
        dataVals.add(new Entry(14, 0));
        dataVals.add(new Entry(15, 0));
        dataVals.add(new Entry(16, 0));
        dataVals.add(new Entry(17, 0));
        dataVals.add(new Entry(18, 0));
        dataVals.add(new Entry(19, 0));
        dataVals.add(new Entry(20, 0));
        dataVals.add(new Entry(21, 0));
        dataVals.add(new Entry(22, 0));
        dataVals.add(new Entry(23, 0));
        dataVals.add(new Entry(24, 0));
        dataVals.add(new Entry(25, 0));
        dataVals.add(new Entry(26, 0));
        dataVals.add(new Entry(27, 0));
        dataVals.add(new Entry(28, 0));
        dataVals.add(new Entry(29, 0));
        dataVals.add(new Entry(30, 0));
        dataVals.add(new Entry(31, 0));
        dataVals.add(new Entry(32, 0));
        dataVals.add(new Entry(33, 0));
        dataVals.add(new Entry(34, 0));
        dataVals.add(new Entry(35, 15));
        dataVals.add(new Entry(36, 8));
        dataVals.add(new Entry(37, 7));
        dataVals.add(new Entry(38, 6));
        dataVals.add(new Entry(39, 26));
        dataVals.add(new Entry(40, 35));
        dataVals.add(new Entry(41, 73));
        dataVals.add(new Entry(42, 100));
        dataVals.add(new Entry(43, 83));
        dataVals.add(new Entry(44, 67));
        dataVals.add(new Entry(45, 66));
        dataVals.add(new Entry(46, 56));
        dataVals.add(new Entry(47, 42));
        dataVals.add(new Entry(48, 35));
        return dataVals;
    }
}
