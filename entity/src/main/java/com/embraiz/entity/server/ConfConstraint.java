package com.embraiz.entity.server;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "conf_constraint")
public class ConfConstraint implements Serializable {
    @Id
    @Column(name = "constraint_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long constraintId;

    @Column(name = "tb_name")
    private String tbName;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "check_type")
    private String checkType;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "error_code")
    private Integer errorCode;
}
