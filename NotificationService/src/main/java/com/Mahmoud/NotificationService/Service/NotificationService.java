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
import java.time.LocalDateTime;
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
        List<TaskDto> tasks = taskClient.dueTomorrow(0);
        for(TaskDto task : tasks) {
            Notification notification = new Notification();
            notification.setUserId(task.getUserId());
            notification.setMessage("Task :" + task.getTaskName() + "   will end tomorrow");
            notification.setSeen(false);
            notification.setCreatedAt(LocalDateTime.now());
            notificationRepo.save(notification);
        }

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
