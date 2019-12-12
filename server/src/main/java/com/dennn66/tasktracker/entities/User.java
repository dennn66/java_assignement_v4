package com.dennn66.tasktracker.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Long id;//id,
    @Column(name = "name")
    private String name;// user,
    @Column(name = "email")
    private String email;// ,

    @Column(name = "status")
    private Status status; // статус

    public enum Status {
        ENABLED("Enabled"), DISABLED("Disabled");
        String name;

        Status(String name) {
            this.name = name;
        }
        public String getDisplayValue() {
            return name;
        }
    }
    public enum Filter {
        ALL("All"), ENABLED("Enabled"), DISABLED("Disabled");
        String name;

        Filter(String name) {
            this.name = name;
        }
        public String getDisplayValue() {
            return name;
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof User)) return false;
        return ((User)obj).id.equals(id) && ((User)obj).email.equals(email);
    }

    @Override
    public int hashCode() { return id.intValue() + name.hashCode(); }
}
