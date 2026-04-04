package com.yash.learn_spring_ai.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
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

}
