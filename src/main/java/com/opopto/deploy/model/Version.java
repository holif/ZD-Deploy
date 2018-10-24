package com.opopto.deploy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: jone
 * @date: Created in 2018/8/12 23:24
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Version implements Serializable {

    private static final long serialVersionUID = -70748304775106712L;

    private String version;
    private Long modifyTime;
}