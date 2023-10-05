package com.eanswer.dadaiksan.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ShopDto {
    private Long id;
    private String shopName;
    private String category;
    private String address;
    private String lat;
    private String lon;
    private String shopDesc;
}
