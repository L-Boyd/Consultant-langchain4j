package com.lbytech.consultant.aiservice;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,   // AiServices创建代理对象时，手动装配
        chatModel = "openAiChatModel" // langchain4j会自动往IOC容器里注入openAiChatModel（名字默认是首字母小写）

)
public interface ConsultantService {

    /**
     * 用于聊天的方法
     * @param message
     * @return
     */
    public String chat(String message);

}
