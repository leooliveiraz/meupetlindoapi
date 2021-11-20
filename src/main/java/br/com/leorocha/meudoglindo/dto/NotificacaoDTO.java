package br.com.leorocha.meudoglindo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class NotificacaoDTO {
    private String title;
    private String body;
    private String icon;
    private List<Integer> vibrate;
    private LocalDateTime dateOfArrival;
    private int primaryKey;
    private String action;
    private String actionTitle;
    private String sub;
}
