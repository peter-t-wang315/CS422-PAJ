package cpts422PAJ.personalPlanner.entities;

import jakarta.persistence.*;

import java.util.Objects;
@Entity
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    private String name; // "Complete" or "Incomplete"
    private Long dueDateIncrement;

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) && Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Long getDueDateIncrement() {
        return dueDateIncrement;
    }

    public void setDueDateIncrement(Long dueDateIncrement) {
        this.dueDateIncrement = dueDateIncrement;
    }
}
