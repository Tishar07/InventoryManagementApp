package view;

import model.Product;
import utilities.Navigator;
import viewModel.ProductViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

public class ProductGridView extends JPanel {

    private final JPanel gridPanel;
    private ProductViewModel viewModel ;

    JButton viewBtn;
    JButton deleteBtn;

    public ProductGridView(ProductViewModel viewModel) {
        this.viewModel= viewModel;
        setLayout(new BorderLayout());
        setBackground(new Color(235, 235, 235)); // softer page background

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(0, 3, 15, 15)); // tighter spacing
        gridPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        gridPanel.setBackground(new Color(235, 235, 235));

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void loadProducts(List<Product> products) {

        gridPanel.removeAll();

        if (products == null || products.isEmpty()) {

            gridPanel.setLayout(new BorderLayout());

            JLabel emptyLabel = new JLabel("No Products Available", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));

            gridPanel.add(emptyLabel, BorderLayout.CENTER);
            gridPanel.revalidate();
            gridPanel.repaint();
            return;
        }

        gridPanel.setLayout(new GridLayout(0, 3, 15, 15));

        for (Product p : products) {
            gridPanel.add(createCard(p));
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    // ================= CREATE MODERN CARD =================
    private JPanel createCard(Product p) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(340, 520));
        card.setMaximumSize(new Dimension(340, 520));

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // ================= IMAGE =================
        JLabel imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(300, 300));
        imageLabel.setMaximumSize(new Dimension(300, 300));

        try {
            String imagePath = p.getImagePath();

            if (imagePath != null && !imagePath.isEmpty()) {

                if (imagePath != null) {

                    ImageIcon icon = new ImageIcon(imagePath);
                    Image img = icon.getImage().getScaledInstance(
                            300,
                            280,
                            Image.SCALE_SMOOTH
                    );

                    imageLabel.setIcon(new ImageIcon(img));

                } else {
                    imageLabel.setText("No Image");
                }

            } else {
                imageLabel.setText("No Image");
            }

        } catch (Exception e) {
            imageLabel.setText("Image Error");
        }

        // ================= DETAILS =================
        JPanel detailPanel = new JPanel();
        detailPanel.setBackground(Color.WHITE);
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel name = new JLabel(p.getProductName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 20));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel price = new JLabel("Rs " + p.getUnitPrice());
        price.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        price.setForeground(new Color(0, 153, 0));
        price.setAlignmentX(Component.CENTER_ALIGNMENT);

        detailPanel.add(name);
        detailPanel.add(Box.createVerticalStrut(10));
        detailPanel.add(price);

        // ================= BUTTON PANEL =================
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setBackground(Color.WHITE);

        viewBtn = new JButton("VIEW / UPDATE");
        viewBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navigator.showProductFormEditView(p.getProductId());
            }
        });
        deleteBtn = new JButton("DELETE");
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String deleteResponse = viewModel.deleteProduct(p.getProductId());
                JOptionPane.showMessageDialog(
                        null,
                        deleteResponse,
                        "Delete Status",
                        JOptionPane.INFORMATION_MESSAGE
                        );
                loadProducts(viewModel.getProducts());
                gridPanel.revalidate();
                gridPanel.repaint();
            }
        });

        Font btnFont = new Font("Segoe UI", Font.BOLD, 14);
        viewBtn.setFont(btnFont);
        deleteBtn.setFont(btnFont);

        viewBtn.setFocusPainted(false);
        deleteBtn.setFocusPainted(false);

        viewBtn.setBackground(new Color(33, 150, 243));
        viewBtn.setForeground(Color.WHITE);

        deleteBtn.setBackground(new Color(244, 67, 54));
        deleteBtn.setForeground(Color.WHITE);

        viewBtn.setPreferredSize(new Dimension(160, 40));
        deleteBtn.setPreferredSize(new Dimension(110, 40));

        btnPanel.add(viewBtn);
        btnPanel.add(deleteBtn);

        // ================= ADD COMPONENTS =================
        card.add(imageLabel);
        card.add(Box.createVerticalStrut(15));
        card.add(detailPanel);
        card.add(Box.createVerticalGlue());
        card.add(btnPanel);

        return card;
    }
}