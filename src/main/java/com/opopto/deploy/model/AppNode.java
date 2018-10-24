package com.opopto.deploy.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "op_app_node")
public class AppNode implements Serializable {

    private static final long serialVersionUID = -5333251393090193135L;

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private Integer appId;

    @NotNull
    private Integer nodeId;

    private Integer delFlag;

    @Transient
    private String project;

    @Transient
    private String fileName;

    @Transient
    private String serviceName;

    @Transient
    private String deployPath;

    @Transient
    private String hostName;

    @Transient
    private String host;
}
