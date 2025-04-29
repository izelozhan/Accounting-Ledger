import Screens.HomeScreen;
import Screens.LedgerScreen;
import Screens.ReportsScreen;

public class Main {
    public static void main(String[] args) {
        HomeScreen homeScreen = new HomeScreen();
        LedgerScreen ledgerScreen = new LedgerScreen();
        ReportsScreen reportsScreen = new ReportsScreen();

        homeScreen.showHomeScreenOptionsMenu();
        String userOption = homeScreen.receiveUserOption();
        homeScreen.performUserOption(userOption);

    }
}
