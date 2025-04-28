import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        Home home = new Home();

//        home.showOptionsMenu();
        String userOption = home.receiveUserOption();
        System.out.println(userOption);
        //option is ledger => call ledger
        // reports => call reports

    }
}
