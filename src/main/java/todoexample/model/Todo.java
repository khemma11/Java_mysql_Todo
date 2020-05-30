package todoexample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Todo {
    private int id;
    private String name;
    private Date createdDate;
    private Date deadline;
    private Status status;
    private  User user;

    public Todo(String name, Date createdDate, Date deadline, Status status, User user) {
        this.name = name;
        this.createdDate = createdDate;
        this.deadline = deadline;
        this.status = status;
        this.user = user;
    }

}
