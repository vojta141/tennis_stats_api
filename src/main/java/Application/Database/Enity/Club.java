package Application.Database.Enity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Club extends BaseEntity{

    @NotNull
    private String name;

    public Club() {

    }

    public Club(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
