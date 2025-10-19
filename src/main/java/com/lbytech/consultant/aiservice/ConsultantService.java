package com.lbytech.consultant.aiservice;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,   // AiServices创建代理对象时，手动装配
        chatModel = "openAiChatModel", // langchain4j会自动往IOC容器里注入openAiChatModel（名字默认是首字母小写）
        streamingChatModel = "openAiStreamingChatModel",
        chatMemory = "chatMemory"   // 会话记忆对象
)
//@AiService  // 默认自动装配（创建代理对象时，自动去IOC容器里找chatModel）
public interface ConsultantService {

    /**
     * 用于聊天的方法
     * @param message
     * @return
     */
    public String chat(String message);

    /**
     * 用于流式聊天的方法
     * @param message
     * @return
     */
    //@SystemMessage("你是博哥的助理")
    @SystemMessage(fromResource = "static/system.txt")
    @UserMessage("我是广东考生，一百个字介绍一下广东高考情况！然后回答：{{it}}") // 在用户信息前面拼接信息{{it}} 是占位符，会被message替换
    public Flux<String> chatFluxy(String message);

}
