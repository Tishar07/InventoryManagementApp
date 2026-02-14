package view;


import utilities.LabelFactory;
import view.components.BarChart;
import view.components.GroupedBarChartPanel;
import view.components.PieChart;
import view.components.SideMenuBar;
import viewModel.DashboardViewModel;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class DashboardView extends JPanel {

    private DashboardViewModel viewModel;
    view.components.SideMenuBar skeleton = new SideMenuBar();
    JPanel DashboardPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane();

    JPanel PiechartPanel=new JPanel();

    JLabel SubcategoryLabel = LabelFactory.creatFormLabel();
    JPanel PieChart_SubcategoryPanel =new JPanel();

    JLabel GenderLabel = LabelFactory.creatFormLabel();
    JPanel PieChart_GenderPanel =new JPanel();


    JLabel CategoryLabel = LabelFactory.creatFormLabel();
    JPanel BarChart_CategoryPanel =new JPanel();


    JLabel StockLabel = LabelFactory.creatFormLabel();
    JPanel GroupedBarChart_StockPanel =new JPanel();



    public DashboardView(DashboardViewModel ViewModel){
        this.viewModel = ViewModel;
        skeleton.SideBarInt();
        initComponents();

    }

    public void initComponents(){
        setLayout(new BorderLayout());
        JPanel pagePanel = new JPanel(new BorderLayout());
        DashboardPanel.setLayout(new BoxLayout(DashboardPanel, BoxLayout.Y_AXIS));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        //Pie charts Sections
        PiechartPanel.setLayout( new GridLayout(1,2));


        //Subcategory Pie chart
        PieChart_SubcategoryPanel.setLayout(new BoxLayout(PieChart_SubcategoryPanel,BoxLayout.Y_AXIS));
        SubcategoryLabel.setText("Subcategory Distribution");
        Map<String, Integer> dataSub = viewModel.FetchSubCategoryTotal();
        PieChart PieSub= new PieChart(dataSub);
        PieSub.setBackground(Color.white);
        PieChart_SubcategoryPanel.add(SubcategoryLabel);
        PieChart_SubcategoryPanel.add(PieSub);
        PiechartPanel.add(PieChart_SubcategoryPanel);


        //Gender Pie Chart
        PieChart_GenderPanel.setLayout(new BoxLayout(PieChart_GenderPanel,BoxLayout.Y_AXIS));
        GenderLabel.setText("Gender Distribution");
        Map<String, Integer> dataGender = viewModel.FetchGenderTotal();
        PieChart PieGender= new PieChart(dataGender);
        PieGender.setBackground(Color.white);
        PieChart_GenderPanel.add(GenderLabel);
        PieChart_GenderPanel.add(PieGender);
        PiechartPanel.add(PieChart_GenderPanel);


        //Barchart Category
        BarChart_CategoryPanel.setLayout(new BorderLayout());
        CategoryLabel.setText("Stock per Category");
        Map<String, Integer> data = viewModel.FetchCategoryTotal();
        BarChart BarChartCategory = new BarChart(data);
        BarChartCategory.setBackground(Color.white);
        BarChartCategory.setXAxisLabel("Category Name");
        BarChartCategory.setYAxisLabel("Quantity");
        BarChart_CategoryPanel.add(CategoryLabel,BorderLayout.NORTH);
        BarChart_CategoryPanel.add(BarChartCategory,BorderLayout.CENTER);


        //Grouped Barchart Stock
        GroupedBarChart_StockPanel.setLayout(new BorderLayout());
        StockLabel.setText("Stock Transaction Overview");
        Map<String, List<Integer>> Stockdata = viewModel.FetchStockPerMonthChange();
        String series1 = "Stock In";
        String series2 = "Stock Out";
        int yInterval = 20;
        GroupedBarChartPanel gb = new GroupedBarChartPanel(Stockdata,series1, series2, yInterval);
        gb.setXAxisLabel("Month");
        gb.setYAxisLabel("Quantity");
        gb.setBackground(Color.white);
        GroupedBarChart_StockPanel.add(gb,BorderLayout.CENTER);

        PiechartPanel.setPreferredSize(new Dimension(800, 350));
        BarChart_CategoryPanel.setPreferredSize(new Dimension(800, 400));
        GroupedBarChart_StockPanel.setPreferredSize(new Dimension(800, 400));

        DashboardPanel.add(PiechartPanel);
        DashboardPanel.add(BarChart_CategoryPanel);
        DashboardPanel.add(GroupedBarChart_StockPanel);
        DashboardPanel.setBackground(Color.white);


        scrollPane.setViewportView(DashboardPanel);
        pagePanel.add(skeleton, BorderLayout.WEST);
        pagePanel.add(scrollPane, BorderLayout.CENTER);
        add(pagePanel, BorderLayout.CENTER);

    }






}
