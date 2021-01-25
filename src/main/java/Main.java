import TheRealEngine.Window;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        try{
            Window window = Window.get();
            window.run();
        } catch (Exception e){
            System.out.println("wow\n" + e);
            Scanner scanner = new Scanner(System.in);
            String userName = scanner.nextLine();
        }
    }
}
