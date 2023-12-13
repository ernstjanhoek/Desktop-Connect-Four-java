import java.util.Scanner;

abstract class SocialNetwork {

    public void connect() {
        login();
        post();
        logout();
    }
    abstract public void login();
    abstract public void post();
    abstract public void logout();

      // write your code here ...

}

class Instagram extends SocialNetwork {
    public void login() {
        System.out.println("Log into Instagram");
    }
    public void post() {
        System.out.println("Post: Hello, Instagram!");
    }
    public void logout() {
        System.out.println("Log out of Instagram");
    }
}


class Facebook extends SocialNetwork {
    public void login() {
        System.out.println("Log into Facebook");
    }
    public void post() {
        System.out.println("Post: Hello, Facebook!");
    }
    public void logout() {
        System.out.println("Log out of Facebook");
    }
}

// Do not change the code below
class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final String type = scanner.nextLine();
        scanner.close();
        SocialNetwork network = null;
        if ("facebook".equalsIgnoreCase(type)) {
            network = new Facebook();
        } else if ("instagram".equalsIgnoreCase(type)) {
            network = new Instagram();
        } else {
            System.out.println("Error!");
            System.exit(0);
        }
        network.connect();
    }
}