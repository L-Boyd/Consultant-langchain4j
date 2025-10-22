package com.lbytech.consultant.config;

import com.lbytech.consultant.aiservice.ConsultantService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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

    // 构建向量数据库操作对象
    @Bean
    public EmbeddingStore embeddingStore2() {   // 如果叫embeddingStore就和引入的依赖自动注入的embeddingStore冲突了
        // 加载文档进内存
        List<Document> documents = ClassPathDocumentLoader.loadDocuments("content");
        // 构建向量数据库操作对象
        InMemoryEmbeddingStore store = new InMemoryEmbeddingStore();
        // 构建一个EmbeddingStoreIngestor对象，完成文本数据切割，向量化，存储
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .build();
        ingestor.ingest(documents);
        return store;
    }

    // 构建向量数据库检索对象
    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore embeddingStore2) {   // spring里要用IOC容器内的对象可以直接声明
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore2)
                .minScore(0.5)  // 最小的可选入的预选相似度值
                .maxResults(3)  // 最多可查询出的片段
                .build();
    }
}
