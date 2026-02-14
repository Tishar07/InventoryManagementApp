package view.components;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class BarChart extends JPanel {

    private Map<String, Integer> data;
    private String xAxisLabel = "X-Axis";
    private String yAxisLabel = "Y-Axis";

    public BarChart(Map<String, Integer> data) {
        this.data = data;
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

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Get max value from Integer map
        int maxValue = data.values()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(1);

        int divisions = 5;

        FontMetrics metrics = g2.getFontMetrics();

        // ---- Calculate max Y-axis number width ----
        int maxLabelWidth = 0;
        for (int i = 0; i <= divisions; i++) {
            int value = (maxValue * i) / divisions;
            String label = String.valueOf(value);
            maxLabelWidth = Math.max(maxLabelWidth, metrics.stringWidth(label));
        }

        int leftPadding = maxLabelWidth + 60;
        int bottomPadding = 60;
        int topPadding = 40;
        int rightPadding = 40;

        int chartWidth = width - leftPadding - rightPadding;
        int chartHeight = height - topPadding - bottomPadding;


        g2.drawLine(leftPadding, height - bottomPadding,
                width - rightPadding, height - bottomPadding);

        g2.drawLine(leftPadding, topPadding,
                leftPadding, height - bottomPadding);


        for (int i = 0; i <= divisions; i++) {
            int y = height - bottomPadding - (i * chartHeight / divisions);
            int value = (maxValue * i) / divisions;
            String label = String.valueOf(value);

            g2.drawLine(leftPadding - 5, y, leftPadding, y);
            g2.drawString(label,
                    leftPadding - 10 - metrics.stringWidth(label),
                    y + 5);
        }


        int numberOfBars = data.size();
        int barWidth = chartWidth / numberOfBars - 20;

        int x = leftPadding + 10;

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            int value = entry.getValue();


            int barHeight = (int) (((double) value / maxValue) * chartHeight);
            int y = height - bottomPadding - barHeight;

            g2.setColor(new Color(66, 135, 245));
            g2.fillRect(x, y, barWidth, barHeight);

            g2.setColor(Color.BLACK);
            g2.drawString(entry.getKey(),
                    x + barWidth / 4,
                    height - bottomPadding + 20);

            x += barWidth + 20;
        }


        int xLabelWidth = metrics.stringWidth(xAxisLabel);
        g2.drawString(xAxisLabel,
                leftPadding + chartWidth / 2 - xLabelWidth / 2,
                height - 15);


        g2.rotate(-Math.PI / 2);

        int yLabelX = -topPadding - chartHeight / 2 - metrics.stringWidth(yAxisLabel) / 2;
        int yLabelY = 20;

        g2.drawString(yAxisLabel, yLabelX, yLabelY);

        g2.rotate(Math.PI / 2);
    }
}

