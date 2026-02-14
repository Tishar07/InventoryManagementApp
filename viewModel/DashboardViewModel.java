package viewModel;

import DAO.DashboardDAO;

import java.util.List;
import java.util.Map;

public class DashboardViewModel {
    private DashboardDAO dashboardDAO;
    public DashboardViewModel(DashboardDAO dashboardDAO){
        this.dashboardDAO =dashboardDAO;
    }

    public Map<String,Integer> FetchSubCategoryTotal(){
        return dashboardDAO.getSubCategoryTotal();
    }

    public Map<String,Integer> FetchGenderTotal(){
        return dashboardDAO.getGenderTotal();
    }

    public Map<String,Integer> FetchCategoryTotal(){
        return dashboardDAO.getCategoryTotal();
    }

    public Map<String, List<Integer>> FetchStockPerMonthChange(){
        return dashboardDAO.getStockInOutPerMonth();
    }



}
