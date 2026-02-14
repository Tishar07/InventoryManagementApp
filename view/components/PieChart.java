package view.components;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PieChart extends JPanel {

    private final Map<String, Integer> data;


    private static final double GOLDEN_ANGLE = 137.508;

    public PieChart(Map<String, Integer> data) {
        this.data = data;
        setBackground(Color.WHITE);
    }


    private Color generateColor(int index) {
        float hue = (float) ((index * GOLDEN_ANGLE) % 360) / 360f;

        // Strong saturation & brightness (avoid pale colors)
        float saturation = 0.75f;
        float brightness = 0.85f;

        return Color.getHSBColor(hue, saturation, brightness);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (data == null || data.isEmpty()) return;

        int total = data.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        if (total == 0) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        int diameter = Math.min(width - 200, height - 100);
        int x = 50;
        int y = 50;

        double startAngle = 0;
        int legendY = 70;
        int colorIndex = 0;

        for (Map.Entry<String, Integer> entry : data.entrySet()) {

            String key = entry.getKey();
            int value = entry.getValue();

            double angle = (value * 360.0) / total;

            Color sliceColor = generateColor(colorIndex);


            g2.setColor(sliceColor);
            g2.fillArc(x, y, diameter, diameter,
                    (int) startAngle,
                    (int) Math.round(angle));


            g2.setColor(Color.WHITE);
            g2.drawArc(x, y, diameter, diameter,
                    (int) startAngle,
                    (int) Math.round(angle));


            double midAngle = Math.toRadians(startAngle + angle / 2);
            int labelX = (int) (x + diameter / 2 +
                    (diameter / 3) * Math.cos(midAngle));
            int labelY = (int) (y + diameter / 2 -
                    (diameter / 3) * Math.sin(midAngle));

            String percent = String.format("%.1f%%",
                    (value * 100.0) / total);

            g2.setColor(Color.BLACK);
            g2.drawString(percent, labelX, labelY);


            g2.setColor(sliceColor);
            g2.fillRect(width - 150, legendY, 20, 20);

            g2.setColor(Color.BLACK);
            g2.drawRect(width - 150, legendY, 20, 20);

            g2.drawString(key + " (" + value + ")",
                    width - 120,
                    legendY + 15);

            legendY += 30;
            startAngle += angle;
            colorIndex++;
        }
    }
}
