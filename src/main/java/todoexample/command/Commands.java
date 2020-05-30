package todoexample.command;

public interface Commands {
    int EXIT = 0;
    int LOGIN = 1;
    int REGISTER = 2;

    int LOGOUT = 0;
    int ADD_TODO = 1;
    int MY_LIST = 2;
    int MY_IN_PROGRESS_LIST = 3;
    int MY_FINISHED_LIST = 4;
    int CHANGE_TODO = 5;
    int DELETE_TODO_BY_ID = 6;


    static void printMainCommands() {
        System.out.println("Please input " + EXIT + " for EXIT");
        System.out.println("Please input " + LOGIN + " for LOGIN");
        System.out.println("Please input " + REGISTER + " for REGISTER");

    }

    static void printCommands() {

        System.out.println("Please input " + LOGOUT + " for LOGOUT");
        System.out.println("Please input " + ADD_TODO + " for ADD_TODO");
        System.out.println("Please input " + MY_LIST + " for MY_LIST");
        System.out.println("Please input " + MY_IN_PROGRESS_LIST + " for MY_IN_PROGRESS_LIST ");
        System.out.println("Please input " + MY_FINISHED_LIST + " for MY_FINISHED_LIST");
        System.out.println("Please input " + CHANGE_TODO + " for CHANGE_TODO");
        System.out.println("Please input " + DELETE_TODO_BY_ID + " for DELETE_TODO_BY_ID");


    }
}

