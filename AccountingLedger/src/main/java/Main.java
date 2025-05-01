
import Screens.HomeScreen;

public class Main {

    public static void main(String[] args) {
        HomeScreen homeScreen = new HomeScreen();
        boolean isExitFromHome = false;
        while(!isExitFromHome) {
            homeScreen.showHomeScreenOptionsMenu();
            String userOption = homeScreen.receiveUserOption();
            isExitFromHome = homeScreen.performUserOption(userOption);
        }
        System.out.println("Exiting the application, see you next time!");
    }
}
