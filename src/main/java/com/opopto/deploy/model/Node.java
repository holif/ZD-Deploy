package com.opopto.deploy.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "op_node")
public class Node implements Serializable {

    private static final long serialVersionUID = -5333255995090294235L;

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String hostName;

    @NotNull
    private String host;
}
