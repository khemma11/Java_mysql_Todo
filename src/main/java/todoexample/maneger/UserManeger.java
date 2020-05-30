package todoexample.maneger;
import todoexample.db.DBConnectionProvider;
import todoexample.model.Gender;
import todoexample.model.User;
import java.sql.*;


public class UserManeger {
    private Connection connection;

    public UserManeger() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public void addUser(User user) throws SQLException {

        PreparedStatement preparedStatement =
                connection.prepareStatement("Insert into `user`(`name`,`surname`,`age`,`email`,`gender`,`password`) Values(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setInt(3, user.getAge());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, String.valueOf(user.getGender()));
        preparedStatement.setString(6, user.getPassword());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            user.setId(id);

        }
    }

    public User getUserByEmailAndPassword(String email, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SElECT * FROM user WHERE `email` = ? AND `password` = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setSurname(resultSet.getString(3));
                user.setAge(resultSet.getInt(4));
                user.setEmail(resultSet.getString(5));
                user.setGender(Gender.valueOf(resultSet.getString(6)));
                user.setPassword(resultSet.getString(7));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


