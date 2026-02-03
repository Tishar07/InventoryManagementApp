package view;

import model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductGridView extends JPanel {

    Font fontBold = new Font("Segoe UI", Font.BOLD, 16);
    JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));

    public ProductGridView() {
        setLayout(new BorderLayout());
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(gridPanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll, BorderLayout.CENTER);
    }

    public void loadProducts(List<Product> products) {
        gridPanel.removeAll();

        for (Product p : products) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            card.setBackground(Color.WHITE);
            card.setPreferredSize(new Dimension(220, 170));
// sa ti problem la mo fini drece li ban ancine function pa ti exister
            JLabel idLabel     = new JLabel("ID: " + p.getProductId());
            JLabel nameLabel   = new JLabel("Name: " + p.getProductName());
            JLabel priceLabel  = new JLabel("Price: $" + p.getUnitPrice());
            JLabel statusLabel = new JLabel("Status: " + p.getProductStatus());
            JLabel genderLabel = new JLabel("Gender: " + p.getGender());

            idLabel.setFont(fontBold);
            nameLabel.setFont(fontBold);

            card.add(idLabel);
            card.add(nameLabel);
            card.add(priceLabel);
            card.add(statusLabel);
            card.add(genderLabel);

            gridPanel.add(card);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }
}
