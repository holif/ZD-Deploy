package com.opopto.deploy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "op_app")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class App implements Serializable {

    private static final long serialVersionUID = -5333255995090195335L;

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String project;

    @NotNull
    private String fileName;

    @NotNull
    private String serviceName;

    @NotNull
    private String deployPath;

    private String user;

    private String group;

    @Transient
    private String version;

}
