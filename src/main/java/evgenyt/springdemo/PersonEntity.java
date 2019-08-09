package evgenyt.springdemo;

/**
 * Entity to translate data to table person
 * we must point to table, columns, primary key
 */

import javax.persistence.*;

@Entity
@Table(name = "person")
public class PersonEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "person_name")
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id=" + id + " name=" + name;
    }
}
