package todoexample;

import todoexample.command.Commands;
import todoexample.maneger.TodoManeger;
import todoexample.maneger.UserManeger;
import todoexample.model.Gender;
import todoexample.model.Status;
import todoexample.model.Todo;
import todoexample.model.User;
import java.sql.SQLException;
import java.util.Scanner;

public class Main implements Commands {

    private static Scanner scanner = new Scanner(System.in);
    private static UserManeger userManeger = new UserManeger();
    private static TodoManeger todoManeger = new TodoManeger();
    private static User currentUser = null;

    public static void main(String[] args) throws SQLException {

        boolean isRun = true;
        while (isRun){
            Commands.printMainCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e) {
                command = -1;
            }
            switch (command){
                case EXIT:
                    isRun = false;
                    break;
                case LOGIN:
                    loginUser();
                    break;
                case REGISTER:
                    registerUser();
                    break;
                default:
                    System.out.println("Wrong command!!!");
            }
        }

    }

    private static void registerUser()  {
        System.out.println("Please input user data " +
                "name,surname,age,email,gender(MALE,FEMALE),password");
        try {
            String userDataStr = scanner.nextLine();
            String[] userData = userDataStr.split(",");
            User user = new User();
            user.setName(userData[0]);
            user.setSurname(userData[1]);
            user.setAge(Integer.parseInt(userData[2]));
            user.setEmail(userData[3]);
            user.setGender(Gender.valueOf(userData[4].toUpperCase()));
            user.setPassword(userData[5]);
            userManeger.addUser(user);
        } catch (SQLException | ArrayIndexOutOfBoundsException e) {
            e.getMessage();
        }
    }
    private static void loginUser() throws SQLException {

        System.out.println("Please input email, password for login");
        try {
            String loginStr = scanner.nextLine();
            String[] loginArr = loginStr.split(",");
            User users = userManeger.getUserByEmailAndPassword(loginArr[0],loginArr[1]);

                if (users != null ){
                    currentUser= users;
                    loginSuccess();
                }else {
                    System.out.println("Wrong phoneNumber or password");
                }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong Data!");
        }
    }

    private static void loginSuccess() throws SQLException {
        boolean isRun = true;
        while (isRun){
            Commands.printCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e) {
                command = -1;
            }
            switch (command){
                case LOGOUT:
                    isRun = false;
                    break;
                case ADD_TODO:
                   addTodos();
                    break;
                case MY_LIST:
                 todoManeger.getTodoByUserId(currentUser);
                    break;
                case MY_IN_PROGRESS_LIST:
                   todoManeger.myInProgressList(currentUser);
                    break;
                case MY_FINISHED_LIST:
                   todoManeger.myFinishedList(currentUser);
                    break;
                case CHANGE_TODO:
                    cangeTodoStatus();
                    break;
                case DELETE_TODO_BY_ID:
                   deleteTodoById();
                   break;
                default:
                    System.out.println("Wrong command!!!");
            }
        }

    }

    private static void deleteTodoById() throws SQLException {
            System.out.println("Please input id for delete todo");
            int id = Integer.parseInt(scanner.nextLine());
            todoManeger.deleteTodoById(id);

    }

    private static void cangeTodoStatus() throws SQLException {
        todoManeger.getTodoByUserId(currentUser);
        System.out.println("Please input id for change status todo");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Please input new status");
        String st = scanner.nextLine();
        todoManeger.changeTodoStatus(currentUser,id, Status.valueOf(st));
    }
    

    private static void addTodos() {
        System.out.println("Please input todo data: name, status(TODO, IN_PROGRESS, FINISHED) for added todo " );
        String todoStr = scanner.nextLine();
        String [] tod = todoStr.split(",");
        Todo todo = new Todo();
        todo.setName(tod[0]);
        todo.setStatus(Status.valueOf(tod[1].toUpperCase()));
        todo.setUser(currentUser);
        try {
            todoManeger.addTodo(todo);
        } catch (SQLException |ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}

