package org.example.simplechatting.controller;

import org.example.simplechatting.dto.ChatMemberDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : kangtaegyung
 * @fileName : ChatController
 * @since : 24. 6. 1.
 * description :
 *     @MessageMapping("/pub") : /pub 를 메시지를 받을 URL
 *     @SendTo("/sub") : /sub 로 메시지를 전달할 URL
 *     => 의미 : /pub 로 메시지를 받고, /sub 로 메시지를 보내줍니다.
 */
@RestController
public class ChatController {
    @MessageMapping("/pub")
    @SendTo("/sub")
    public ChatMemberDto startChatting(ChatMemberDto chatMember) {
        String userId = chatMember.getUserId();
        String chatMessage = chatMember.getChatMessage();

        ChatMemberDto chatMember2 = new ChatMemberDto(userId, chatMessage);

        return chatMember2;
    }
}
