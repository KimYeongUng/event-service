package com.eventservice.vo;

import java.util.List;
import lombok.Data;

@Data
public class UserVO {
    private String id;
    private String name;
    private String passwd;
    List<ProductVO> products;
}
