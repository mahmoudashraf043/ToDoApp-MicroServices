package com.Mahmoud.NotificationService.Service;

import com.Mahmoud.NotificationService.Dto.TaskDto;
import com.Mahmoud.NotificationService.Dto.UserDto;
import com.Mahmoud.NotificationService.FeignClient.TaskClient;
import com.Mahmoud.NotificationService.FeignClient.UserClient;
import com.Mahmoud.NotificationService.Model.Notification;
import com.Mahmoud.NotificationService.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepo;
    @Autowired
    UserClient userClient;
    @Autowired
    TaskClient taskClient;

    @Scheduled(cron = "0 0 9 * * *") // every day at 9 AM
    public void sendNotification() {
        UserDto user = userClient.getUser();
        TaskDto task = taskClient.dueTomorrow(user.getUserId());

        notificationRepo.findTasksEndTomorrow(user.getUserId() , tomorrow);
    }

    public List<Notification> getAllNotifications() {
        UserDto userDto = userClient.getUser();
        return notificationRepo.findAllByUserId(userDto.getUserId());
    }

    public String markNotificationAsSeen(Integer id) {
        Notification notification = notificationRepo.findById(id).orElseThrow(() -> new RuntimeException("Notification Not Found"));
        notification.setSeen(true);
        notificationRepo.save(notification);
        return "Notification Marked as Seen";
    }
}
