package com.lbytech.consultant.mapper;

import com.lbytech.consultant.pojo.Reservation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReservationMapper {

    /**
     * 添加预约信息
     */
    @Insert("insert into reservation(name, gender, phone, communicatio_time, province, estimated_score) " +
            "values(#{name}, #{gender}, #{phone}, #{communicationTime}, #{province}, #{estimatedScore})")
    void insert(Reservation reservation);

    /**
     * 根据手机号查询预约信息
     */
    @Select("select * from reservation where phone = #{phone}")
    Reservation findByPhone(String phone);
}
