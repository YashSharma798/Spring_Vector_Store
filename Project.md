Internal Working Explaination- 

docker-compose -f filename.yml up -d

==========

# How to start application locally- 

1) Run/Start the postgres server.

2) Connect the postgres DB (postgresql://localhost:5433/pgvector-testing)

3) Now, run springboot application via command- mvn spring-boot:run


# Check AI-Model configuration- 

# Docs: https://ollama.com/library

ollama list ( To get list of all installed AI models)

ollama list                 Show installed models
ollama serve                Start Ollama server
ollama run llama3.2         Run a model
ollama pull mistral         Download a new model
ollama rm modelname         Remove a model
ollama ps                   Show currently running model