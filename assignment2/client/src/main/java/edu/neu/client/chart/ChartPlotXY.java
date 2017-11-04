package edu.neu.client.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChartPlotXY extends JFrame {
    private XYSeriesCollection dataCollection;

    public ChartPlotXY(){
        dataCollection = new XYSeriesCollection();
    }

    public void getChart(CopyOnWriteArrayList<Long> latencyList, CopyOnWriteArrayList<Long> starTimeList, String chartTitle) throws IOException {
        XYSeries plotXY = new XYSeries(chartTitle);
        for(int i = 0; i < latencyList.size(); i++){
            plotXY.add(starTimeList.get(i), latencyList.get(i));
        }
        dataCollection.addSeries(plotXY);
        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, "Time",
                "Latancy", dataCollection);

        File file = new File(chartTitle);
        ChartUtilities.saveChartAsJPEG(file, chart, 1000, 500);
    }
}



