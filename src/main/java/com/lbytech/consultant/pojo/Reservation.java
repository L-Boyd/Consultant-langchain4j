package com.lbytech.consultant.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 预约实体类
 */
@Data
@NoArgsConstructor  // 无参构造方法
@AllArgsConstructor // 全参构造方法
public class Reservation {

    private Long id;
    private String name;
    private String gender;
    private String phone;
    private LocalDateTime communicationTime;
    private String province;
    private Integer estimatedScore;

}
