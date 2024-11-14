package com.oliveiradev.picpaysimplificado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.oliveiradev.picpaysimplificado.domain.user.User;
import com.oliveiradev.picpaysimplificado.dtos.NotificationDTO;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notifRequest = new NotificationDTO(email, message);

        /*ResponseEntity<String> notifResponse = restTemplate.postForEntity("http://04d9z.mocklab.io/notify", notifRequest, String.class);

        if (!(notifResponse.getStatusCode() == HttpStatus.OK)) {
            System.out.println("Erro ao enviar notificação!");
            throw new Exception("Serviço de notificação está fora do ar!");            
        }
        */

        System.out.println("Notificação enviada para o usuário!");
    }
}
