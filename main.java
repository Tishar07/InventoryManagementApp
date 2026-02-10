import java.sql.SQLException;
import utilities.Navigator;
import view.components.MainFrame;

public class main {
    public static void main(String[] args) {

        MainFrame frame = new MainFrame();
        Navigator.init(frame);

        frame.setVisible(true);
        Navigator.showLogin();
    }
}

