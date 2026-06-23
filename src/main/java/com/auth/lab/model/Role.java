package com.auth.lab.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Role implements GrantedAuthority {

    @Id
    private Long id;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

}
