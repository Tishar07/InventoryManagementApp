package view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SideMenuBar extends  JPanel{
    private JPanel LeftPanel;
    private JPanel RightPanel;


    public void SideBarInt(){
        setLayout(new BorderLayout());

        //SET Side menu Bar
        LeftPanel = new JPanel();
        LeftPanel.setLayout(new BoxLayout(LeftPanel,BoxLayout.Y_AXIS));
        LeftPanel.setPreferredSize(new Dimension(200, 0));
        LeftPanel.setBackground(new Color(30, 75, 176));
        add(LeftPanel,BorderLayout.WEST);
        String Address = "./assets/";

        //SET Image Icons and Name
        String [][] SideMenuItems = {
                {"Dashboard",Address+"Dashboard.png"},
                {"User",Address+"User.png"},
                {"Product",Address+"Product.png"},
                {"Supplier",Address+"Supplier.png"},
                {"Retailer",Address+"Retailer.png"},
                {"Transaction",Address+"Transaction.png"}
        };

        for (String[] sideMenuItem : SideMenuItems) {

            //Icon Image processing
            ImageIcon icon = new ImageIcon(sideMenuItem[1]);
            Image img = icon.getImage();
            Image newImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            icon = new ImageIcon(newImg);

            //Icon Item Creation
            JLabel IconItem = new JLabel(sideMenuItem[0], icon, JLabel.LEFT);
            IconItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            //Icon Item Styling
            IconItem.setForeground(Color.white);
            IconItem.setBackground(new Color(30, 75, 176));
            IconItem.setOpaque(true);

            IconItem.setBorder(BorderFactory.createEmptyBorder(10,0,10,10));
            IconItem.setIconTextGap(15);
            //Icon Item Event Action
            IconItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //SET Direction
                    switch (IconItem.getText()){
                        case "Dashboard":
                            //Redirect to Dashboard
                        case "User":
                            //Redirect to User
                        case "Product":
                            //Redirect to Product
                        case "Supplier" :
                            //Redirect to Supplier
                        case "Retailer" :
                            //Redirect to Retailer
                        case "Transaction" :
                            //Redirect to Transaction
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e){
                    IconItem.setForeground(Color.black);
                    IconItem.setBackground(new Color(1, 195, 255));
                }
                @Override
                public void mouseExited(MouseEvent e){
                    IconItem.setForeground(Color.white);
                    IconItem.setBackground(new Color(30, 75, 176));

                }
            });

            //Add to Icon Item to Panel
            LeftPanel.add(IconItem);
        }

        //SET Main Panel
        RightPanel = new JPanel();
        add(RightPanel,BorderLayout.CENTER);

    }

    //Getters
    public JPanel getSideBarPanel(){
        return this.LeftPanel;
    }
    public JPanel getMainPanel(){
        return this.RightPanel;
    }


}
