package com.lbytech.consultant;

import com.lbytech.consultant.pojo.Reservation;
import com.lbytech.consultant.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Test
    // 测试添加
    void testInsert() {
        Reservation reservation = new Reservation(null, "张三", "男", "13800000000", LocalDateTime.now(), "广东省", 800);
        reservationService.insert(reservation);
    }

    @Test
    // 测试查询
    void testFindByPhone() {
        Reservation reservation = reservationService.findByPhone("13800000000");
        System.out.println(reservation);
    }

}
