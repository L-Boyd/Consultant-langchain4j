package com.lbytech.consultant.controller;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private OpenAiChatModel chatModel;

    @RequestMapping("/chat")
    public String chat(String message) {
        String result = chatModel.chat(message);
        return result;
    }

}
