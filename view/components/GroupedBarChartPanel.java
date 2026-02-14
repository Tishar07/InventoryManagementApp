package view.components;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.List;

public class GroupedBarChartPanel extends JPanel {

    private Map<String, List<Integer>> data;
    private String series1Name;
    private String series2Name;
    private int yInterval;

    private String xAxisLabel = "Month";
    private String yAxisLabel = "Value";

    private final Color series1Color = new Color(66, 135, 245);
    private final Color series2Color = new Color(245, 66, 66);

    // Fixed 12 months
    private final String[] months = {
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
    };


    public GroupedBarChartPanel(Map<String, List<Integer>> data,
                                String series1Name,
                                String series2Name,
                                int yInterval) {
        this.data = data;
        this.series1Name = series1Name;
        this.series2Name = series2Name;
        this.yInterval = yInterval;
    }

    public void setXAxisLabel(String label) {
        this.xAxisLabel = label;
    }

    public void setYAxisLabel(String label) {
        this.yAxisLabel = label;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (data == null) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();


        double maxValue = 0;

        for (String month : months) {
            List<Integer> values = data.get(month);
            if (values != null && values.size() >= 2) {
                maxValue = Math.max(maxValue,
                        Math.max(values.get(0), values.get(1)));
            }
        }

        double scaledMax = Math.ceil(maxValue / yInterval) * yInterval;


        if (scaledMax == 0) {
            scaledMax = yInterval;
        }

        FontMetrics metrics = g2.getFontMetrics();


        int maxLabelWidth = 0;
        for (int value = 0; value <= scaledMax; value += yInterval) {
            String label = String.valueOf(value);
            maxLabelWidth = Math.max(maxLabelWidth,
                    metrics.stringWidth(label));
        }


        int leftPadding = maxLabelWidth + 70;
        int rightPadding = 40;
        int topPadding = 60;
        int bottomPadding = 70;

        int chartWidth = width - leftPadding - rightPadding;
        int chartHeight = height - topPadding - bottomPadding;


        g2.drawLine(leftPadding, height - bottomPadding,
                width - rightPadding, height - bottomPadding);

        g2.drawLine(leftPadding, topPadding,
                leftPadding, height - bottomPadding);


        for (int value = 0; value <= scaledMax; value += yInterval) {

            int y = height - bottomPadding
                    - (int)((value / scaledMax) * chartHeight);

            g2.setColor(new Color(220, 220, 220));
            g2.drawLine(leftPadding, y, width - rightPadding, y);

            g2.setColor(Color.BLACK);
            g2.drawString(String.valueOf(value),
                    leftPadding - 10 - metrics.stringWidth(String.valueOf(value)),
                    y + 5);
        }


        int numberOfGroups = 12;
        int groupSpacing = chartWidth / numberOfGroups;
        int barWidth = Math.min(25, groupSpacing / 3);

        int x = leftPadding + groupSpacing / 4;

        for (String month : months) {

            List<Integer> values = data.get(month);

            int v1 = 0;
            int v2 = 0;

            if (values != null && values.size() >= 2) {
                v1 = values.get(0);
                v2 = values.get(1);
            }

            int barHeight1 = (int)((v1 / scaledMax) * chartHeight);
            int barHeight2 = (int)((v2 / scaledMax) * chartHeight);

            int y1 = height - bottomPadding - barHeight1;
            int y2 = height - bottomPadding - barHeight2;

            g2.setColor(series1Color);
            g2.fillRect(x, y1, barWidth, barHeight1);

            g2.setColor(series2Color);
            g2.fillRect(x + barWidth + 8, y2, barWidth, barHeight2);

            // Draw month label centered
            g2.setColor(Color.BLACK);
            int labelWidth = metrics.stringWidth(month);
            g2.drawString(month,
                    x + barWidth - labelWidth / 2,
                    height - bottomPadding + 20);

            x += groupSpacing;
        }


        int xLabelWidth = metrics.stringWidth(xAxisLabel);
        g2.drawString(xAxisLabel,
                leftPadding + chartWidth / 2 - xLabelWidth / 2,
                height - 20);

        // ---- Draw Y-axis label ----
        g2.rotate(-Math.PI / 2);

        int yLabelX = -topPadding - chartHeight / 2
                - metrics.stringWidth(yAxisLabel) / 2;

        int yLabelY = 25;

        g2.drawString(yAxisLabel, yLabelX, yLabelY);

        g2.rotate(Math.PI / 2);

        drawLegend(g2, width, rightPadding);
    }

    private void drawLegend(Graphics2D g2, int width, int rightPadding) {

        int legendX = width - rightPadding - 160;
        int legendY = 40;

        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.drawString("Legend", legendX, legendY - 10);

        g2.setColor(series1Color);
        g2.fillRect(legendX, legendY, 20, 20);
        g2.setColor(Color.BLACK);
        g2.drawRect(legendX, legendY, 20, 20);
        g2.drawString(series1Name, legendX + 30, legendY + 15);

        g2.setColor(series2Color);
        g2.fillRect(legendX, legendY + 35, 20, 20);
        g2.setColor(Color.BLACK);
        g2.drawRect(legendX, legendY + 35, 20, 20);
        g2.drawString(series2Name, legendX + 30, legendY + 50);
    }
}



