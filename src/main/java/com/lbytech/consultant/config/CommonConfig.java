package com.lbytech.consultant.config;

import com.lbytech.consultant.aiservice.ConsultantService;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Autowired
    private OpenAiChatModel chatModel;

    @Autowired
    private ChatMemoryStore redisChatMemoryStore;

    /*@Bean
    public ConsultantService consultantService() {
        ConsultantService consultantService = AiServices.builder(ConsultantService.class) // 创建哪个接口的代理对象，就传哪个接口的类型
                .chatModel(chatModel)
                .build();
        return consultantService;
    }*/

    @Bean
    // 构建会话记忆对象
    public ChatMemory chatMemory() {
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(20)    // 最多记忆20条消息
                .build();
        return chatMemory;
    }

    @Bean
    // 构建ChatMemoryProvider对象，前端传memoryId，langchain4j根据memoryId匹配话记忆对象，没匹配到就会用get方法来获取chatMemory
    public ChatMemoryProvider chatMemoryProvider() {
        ChatMemoryProvider chatMemoryProvider = new ChatMemoryProvider() {
            @Override
            public ChatMemory get(Object memoryId) {
                return MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(20)
                        .chatMemoryStore(redisChatMemoryStore)
                        .build();
            }
        };
        return chatMemoryProvider;
    }
}
