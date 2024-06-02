package org.example.simplechatting.dto;

import lombok.*;

/**
 * @author : kangtaegyung
 * @fileName : ChatMemberDto
 * @since : 24. 5. 27.
 * description : 채팅 회원 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatMemberDto {
    private String userId;
    private String chatMessage;
}
