package com.old.business.user.domain.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class DeptTreeResp implements Serializable {

    private Integer id;

    private String label;

    private List<DeptTreeResp> children;
}
