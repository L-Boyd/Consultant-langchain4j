package com.lbytech.consultant.controller;

import com.lbytech.consultant.aiservice.ConsultantService;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    @Autowired
    private OpenAiChatModel chatModel;

    @Autowired
    private ConsultantService consultantService;

    @RequestMapping("/chat")
    public String chat(String message) {
        String result = chatModel.chat(message);
        return result;
    }

    @RequestMapping("/chat2")
    public String chat2(String message) {
        String result = consultantService.chat(message);
        return result;
    }

     @RequestMapping(value = "/chatFluxy", produces = "text/html;charset=UTF-8")    // produces解决乱码问题
    public Flux<String> chatFluxy(String memoryId, String message) {
        Flux<String> result = consultantService.chatFluxy(memoryId, message);
        return result;
    }

}
