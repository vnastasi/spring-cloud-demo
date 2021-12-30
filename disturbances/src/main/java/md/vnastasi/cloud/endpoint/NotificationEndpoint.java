package md.vnastasi.cloud.endpoint;

import md.vnastasi.cloud.endpoint.model.Notification;
import md.vnastasi.cloud.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class NotificationEndpoint {

    private final NotificationService notificationService;

    public NotificationEndpoint(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notification")
    public ResponseEntity<Flux<Notification>> getNotifications() {
        return ResponseEntity.ok(notificationService.getNotifications());
    }
}
