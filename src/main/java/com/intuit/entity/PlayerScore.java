package com.intuit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerScore {

    @Id
    @GeneratedValue
    private UUID playerId;

    @Valid
    @Size(min = 2, message = "A persons name must be longer than 2 characters.")
    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private Long score;


}
