package com.project.shop.global.config.security;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authority")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @Id
    @Column(name = "authName")
    private String authName;


}
