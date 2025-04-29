import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        Home home = new Home();

        home.showHomeScreenOptionsMenu();
        String userOption = home.receiveUserOption();
        home.performUserOption(userOption);
    }
}
