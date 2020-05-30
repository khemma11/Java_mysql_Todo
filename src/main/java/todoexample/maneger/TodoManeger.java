package todoexample.maneger;

import todoexample.db.DBConnectionProvider;
import todoexample.model.Status;
import todoexample.model.Todo;
import todoexample.model.User;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TodoManeger {

    private Connection connection;

    public TodoManeger() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public void addTodo(Todo todo) throws SQLException {

        PreparedStatement preparedStatement =
                connection.prepareStatement("Insert into `todo`(`name`,`created_date`,`deadline`,`status`,`user_id`) Values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, todo.getName());
        preparedStatement.setDate(2, (Date) todo.getCreatedDate());
        preparedStatement.setDate(3, (Date) todo.getDeadline());
        preparedStatement.setString(4, String.valueOf(todo.getStatus()));
        preparedStatement.setInt(5, todo.getUser().getId());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            todo.setId(id);

        }
    }

    public List<Todo> getTodoByUserId(User user) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todo WHERE user_id = '" + user.getId() + "'");
        List<Todo> todos = new LinkedList<Todo>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt(1));
            todo.setName(resultSet.getString(2));
            todo.setCreatedDate(resultSet.getDate(3));
            todo.setDeadline(resultSet.getDate(4));
            todo.setStatus(Status.valueOf(resultSet.getString(5)));
            todo.setUser(user);
            todos.add(todo);
            if (todo.getUser().equals(user)) {
                System.out.println(todo);
            }
        }
        return todos;

    }

    public List<Todo> myInProgressList(User user) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todo WHERE status = 'IN_PROGRESS' AND user_id = '" + user.getId() + "'");
        List<Todo> todos = new LinkedList<Todo>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt(1));
            todo.setName(resultSet.getString(2));
            todo.setCreatedDate(resultSet.getDate(3));
            todo.setDeadline(resultSet.getDate(4));
            todo.setStatus(Status.valueOf(resultSet.getString(5)));
            todo.setUser(user);
            todos.add(todo);
            System.out.println(todo);
        }
        return todos;
    }

    public List<Todo> myFinishedList(User user) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todo WHERE status = 'FINISHED' AND user_id = '" + user.getId() + "'");
        List<Todo> todos = new LinkedList<Todo>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt(1));
            todo.setName(resultSet.getString(2));
            todo.setCreatedDate(resultSet.getDate(3));
            todo.setDeadline(resultSet.getDate(4));
            todo.setStatus(Status.valueOf(resultSet.getString(5)));
            todo.setUser(user);
            todos.add(todo);
            System.out.println(todo);
        }
        return todos;
    }

    public void changeTodoStatus(User user, int id, Enum<Status> status) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE todo SET status = ? WHERE user_id = ? AND id = ?", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, String.valueOf(status));
        preparedStatement.setInt(2, user.getId());
        preparedStatement.setInt(3, id);
        preparedStatement.executeUpdate();
    }

    public void deleteTodoById(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM todo WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }
}
