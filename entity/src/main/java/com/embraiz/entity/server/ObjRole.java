package com.embraiz.entity.server;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "obj_role")
public class ObjRole implements Serializable {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "status")
    private Integer status;
}
