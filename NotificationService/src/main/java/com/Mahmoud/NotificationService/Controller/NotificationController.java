package com.Mahmoud.NotificationService.Controller;

import com.Mahmoud.NotificationService.Model.Notification;
import com.Mahmoud.NotificationService.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/")
    public ResponseEntity<List<Notification>> getNotifications() {
        return  new ResponseEntity<>(notificationService.getAllNotifications(), HttpStatus.OK);

    }
    @PutMapping("/seen/{id}")
    public ResponseEntity<?> seenNotification(@PathVariable Integer id) {
        return new ResponseEntity<>(notificationService.markNotificationAsSeen(id) , HttpStatus.OK);
    }
}
