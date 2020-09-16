package com.embraiz.entity.server;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "obj")
public class Obj implements Serializable {
    @Id
    @Column(name = "obj_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer objId;

    @Column(name = "obj_parent_id")
    private Integer objParentId;

    @Column(name = "obj_title")
    private String objTitle;

}
