package com.opopto.deploy.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "op_log")
public class Log implements Serializable {

    private static final long serialVersionUID = -5333255093090193135L;

    public Log(){}

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String operatorName;

    @NotNull
    private String comment;

    private Date time;

    @NotNull
    private String serviceName;

    @NotNull
    private String serviceVersion;

    @NotNull
    private Integer isSuccess;

    @NonNull
    private String loginIp;

}
