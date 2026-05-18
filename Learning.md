# Prompt Template- 

A template for creating prompts. It allows you to define a template string with
placeholders for variables, and then render the template with specific values for those
variables.

# Advisors- 

Advisors are interceptors that wrap around AI requests and responses. They allow you to add cross-cutting concerns like memory, logging, filtering, or RAG — without changing your core AI logic.
Think of it like middleware for your AI calls.

User Request → [Advisor Chain] → AI Model → [Advisor Chain] → Response

# Types of Advisors

1. MessageChatMemoryAdvisor
Adds conversation memory to the chat — stores and retrieves previous messages.
2. QuestionAnswerAdvisor
Adds RAG (Retrieval Augmented Generation) — searches vector store and injects relevant context.
3. SafeGuardAdvisor
Filters sensitive content from requests before sending to the model.
4. SimpleLoggerAdvisor
Logs all requests and responses for debugging.
5. ReReadingAdvisor
Re-reads the question to improve reasoning accuracy (RE2 technique).

# Advisor Execution Order

Request
   ↓
SimpleLoggerAdvisor     (logs request)
   ↓
MessageChatMemoryAdvisor (injects past messages)
   ↓
QuestionAnswerAdvisor   (injects vector context)
   ↓
AI Model
   ↑
MessageChatMemoryAdvisor (saves new message)
   ↑
SimpleLoggerAdvisor     (logs response)
   ↑
Response

Note- You can control order using .order() method on each advisor.