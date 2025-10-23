package com.lbytech.consultant.service;

import com.lbytech.consultant.mapper.ReservationMapper;
import com.lbytech.consultant.pojo.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;

    /**
     * 添加预约信息
     */
    public void insert(Reservation reservation) {
        reservationMapper.insert(reservation);
    }

     /**
     * 根据手机号查询预约信息
     */
    public Reservation findByPhone(String phone) {
        return reservationMapper.findByPhone(phone);
    }
}
