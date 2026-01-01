package com.Mahmoud.NotificationService.Repository;

import com.Mahmoud.NotificationService.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query("select n from Notification n where n.userId =:id")
    List<Notification> findAllByUserId(@Param("id")Integer id);


}
