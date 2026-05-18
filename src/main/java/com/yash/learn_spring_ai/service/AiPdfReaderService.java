package com.yash.learn_spring_ai.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AiPdfReaderService {
    
    @Autowired
    private ChatClient chatClient;

    @Autowired
    private VectorStore VectorStore;

    @Value("Project.pdf")
    private String pdfFile;


    public String ingestPdfToVectorStore(){

        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfFile);

        List<Document> documents = pdfReader.get();

        TokenTextSplitter splitter =  TokenTextSplitter.builder()
                .withChunkSize(350)
                .build();

        List<Document> splitDocuments = splitter.split(documents);

        VectorStore.add(splitDocuments);

        return "Ingestion PDF completed";
    }

    public String askAiFromPdf(String text){

        List<Document> relevantChunks = VectorStore.similaritySearch(
            SearchRequest.builder()
            .query(text)
            .topK(3)
            .build()
        );

        String context = relevantChunks.stream()
            .map(Document:: getText)
            .collect(Collectors.joining("\n\n"));

        String userPrompt = 
                """
                   You are Helpful AI assistant for learners. Give answers based on following chunks of information from pdf. 

                   Rules: 
                   1) If answer is not found in context, say "I don't know"
                   2) Give precise user friendly answer without any additional information.

                   Context: {context}

                   Question: {text} 

                """;

        PromptTemplate promptTemplate= new PromptTemplate(userPrompt);

        String systemPrompt= promptTemplate.render(Map.of("context", context, "text", text));

        String response= chatClient.prompt()
                .system(systemPrompt)
                .user(text)
                .call()
                .content();

        return response;

    }

}
