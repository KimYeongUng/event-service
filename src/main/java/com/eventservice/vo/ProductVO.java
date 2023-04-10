package com.eventservice.vo;

import lombok.Data;

@Data
public class ProductVO {
    private String productid;
    private String category;
    private String userid;
    private Long price;
    UserVO userVO;
}
