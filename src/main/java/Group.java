import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="Groups")
public class Group {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL)


    private List<Client> clients = new ArrayList<>();


    public Group() {}

    public Group(String name) {
        this.name = name;
    }

    public void addClient(Client client) {
        if (!clients.contains(client))
            clients.add(client);

    }

    public List<Client> getClients() {
        return Collections.unmodifiableList(clients);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return clients.size();
    }




    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
