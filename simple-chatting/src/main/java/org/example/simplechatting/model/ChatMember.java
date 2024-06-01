package org.example.simplechatting.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatMember {
    private String userId;
    private String chatMessage;
}
