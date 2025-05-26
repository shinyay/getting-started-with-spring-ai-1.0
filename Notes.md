
# Developing an AI Application with Spring AI and Azure OpenAI: A Step-by-Step Guide

**Spring AI** is a new application framework in the Spring ecosystem designed to simplify building AI-powered applications in Java. It provides high-level abstractions and starter integrations for various AI models and providers, including support for **Microsoft Azure OpenAI**[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/). **Azure OpenAI** is a cloud service that offers OpenAI’s advanced language models (like GPT-3.5 and GPT-4) on the Azure platform, with added enterprise features such as enhanced security, compliance, and responsible AI safeguards[[2]](https://piotrminkowski.com/2025/03/25/spring-ai-with-azure-openai/). In this guide, we will walk through the entire process of developing an AI application using Spring AI with Azure OpenAI – from understanding the basics to setting up the environment, integrating the two technologies, writing code with examples, and following best practices.

---

## Introduction

**Spring AI 1.0** (released in May 2025) enables Java/Spring developers to plug AI capabilities into Spring Boot applications with minimal friction[[3]](https://techcommunity.microsoft.com/blog/appsonazureblog/spring-ai-1-0-ga-is-here---build-java-ai-apps-end-to-end-on-azure-today/4414763). It supports multiple AI providers (OpenAI, Azure OpenAI, Anthropic, etc.) through a unified API, allowing you to switch providers with minimal code changes[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/). Spring AI comes with out-of-the-box support for various AI tasks – chat completions, embeddings, image generation, audio transcription, and more – all following familiar Spring Boot patterns[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/). On the other side, **Azure OpenAI Service** gives you access to OpenAI’s GPT models on Azure, meaning you can use powerful generative models with Azure’s infrastructure benefits (scalability, security, Azure AD integration, etc.)[[2]](https://piotrminkowski.com/2025/03/25/spring-ai-with-azure-openai/). By combining Spring AI and Azure OpenAI, developers can build intelligent Spring Boot apps that leverage cutting-edge AI models in a secure and enterprise-ready way.

**What You Will Learn in This Guide:**

- Basics of Spring AI and Azure OpenAI (what they are and their key features).
- How to set up a development environment for a Spring AI project and for Azure OpenAI.
- How to integrate Spring AI with Azure OpenAI step-by-step.
- Code examples demonstrating how to use Spring AI to call Azure OpenAI models.
- Best practices to follow and common pitfalls to avoid.
- How to deploy your Spring AI + Azure OpenAI application.
- Advanced features available for further enhancement (like RAG, tool calling, etc.).
- Troubleshooting tips for common issues.

Let's start by briefly understanding the two main components: Spring AI and Azure OpenAI.

---

## Spring AI Overview: Bringing AI to Spring Boot

**Spring AI** is an official Spring framework project aimed at **integrating AI capabilities into Spring applications**. Its main features include:

- **Broad Model and Provider Support:** Spring AI offers a unified API to work with many AI providers and models. It supports **chat completion models, embeddings, image generation, audio transcription, text-to-speech, and content moderation** out-of-the-box[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/). Under the hood, it integrates with major AI providers like OpenAI, **Microsoft Azure OpenAI**, Anthropic, Amazon Bedrock, Google Vertex AI, and more[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/). This means you can switch between providers (e.g., from OpenAI’s API to Azure’s) with minimal code changes due to portable service abstractions[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/).

- **Spring Boot Auto-configuration:** Spring AI provides Spring Boot **starter dependencies** that auto-configure necessary components. By including the appropriate starter on the classpath, Spring Boot will automatically set up clients/services for AI models[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/). For example, if you include the Azure OpenAI starter, a client for Azure OpenAI is configured for you. This convention-over-configuration approach makes it easy to get started, especially via Spring Initializr.

- **High-Level API (ChatClient & Advisors):** Spring AI introduces higher-level APIs for common AI patterns. One primary class is the `ChatClient`, which provides a fluent, type-safe builder for interacting with chat models (like GPT)[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/). You can send prompts and receive responses in just a few lines of code. Additionally, Spring AI provides “Advisor” components that implement recurring patterns such as retrieval-augmented generation (RAG), conversational memory, and function/tool calling[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/). For instance, a `QuestionAnswerAdvisor` can be attached to a ChatClient to automatically perform a knowledge base search and include context in the prompt[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/).

- **Integration with Data and Tools:** Spring AI is built to work with external data sources and tools to extend model capabilities. It supports **vector databases** (like Azure Cosmos DB, PostgreSQL+pgvector, Redis, Pinecone, etc.) to enable Retrieval Augmented Generation – where the model can fetch relevant documents to ground its answers[[3]](https://techcommunity.microsoft.com/blog/appsonazureblog/spring-ai-1-0-ga-is-here---build-java-ai-apps-end-to-end-on-azure-today/4414763). It also supports **tool/function calling** via an emerging standard called Model Context Protocol (MCP), allowing AI models to invoke external functions in a structured way[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/). These features enable advanced scenarios like “chat with your documentation” or building AI agents that can perform actions.

- **Spring Ecosystem Integration:** Being a Spring project, Spring AI naturally integrates with Spring Boot’s features. It supports configuration via `application.properties/yaml`, dependency injection of AI clients, and even observability. For example, Spring AI integrates with **Micrometer metrics** so you can monitor token usage, latency, and other metrics of your AI model calls[[3]](https://techcommunity.microsoft.com/blog/appsonazureblog/spring-ai-1-0-ga-is-here---build-java-ai-apps-end-to-end-on-azure-today/4414763)[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/). It aligns with Spring’s design principles (portability, modular design) and promotes using Plain Old Java Objects (POJOs) for structured outputs from AI, making it easier to handle model responses in code[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/).


In summary, Spring AI provides a robust, Spring-friendly foundation to add AI capabilities (like GPT-based chat, image generation, etc.) into your applications **without having to write a lot of boilerplate**. It lets you focus on your application logic, while it handles the intricacies of calling AI services.

## Azure OpenAI Overview: OpenAI Models on Azure

**Azure OpenAI Service** is Microsoft’s offering that allows you to use OpenAI’s powerful models (e.g., GPT-3.5, GPT-4, Codex, DALL-E) through Azure’s cloud platform. Key aspects of Azure OpenAI include:

- **Same Models, Azure Infrastructure:** Azure OpenAI provides the same models as OpenAI’s public API, but hosted on Azure. This means you can use ChatGPT or GPT-4, but with the reliability, scalability, and geographic distribution of Azure’s cloud. You create an **Azure OpenAI resource** in your Azure subscription, and within that resource you deploy specific models (each deployment gets a name) to use them via REST API or SDK[[2]](https://piotrminkowski.com/2025/03/25/spring-ai-with-azure-openai/).

- **Enterprise Security and Compliance:** Because it’s on Azure, you benefit from Azure’s enterprise-grade security, compliance certifications, and data privacy features. **Azure OpenAI offers additional safety features and policy enforcement** beyond the base model, aligning with Microsoft’s responsible AI principles[[2]](https://piotrminkowski.com/2025/03/25/spring-ai-with-azure-openai/). For example, Azure OpenAI integrates content filtering and allows configuration of safe completions, making it suitable for enterprise use cases.

- **Azure Integration:** Azure OpenAI can integrate with other Azure services. For instance, it works seamlessly with **Azure Cognitive Search** (to enable Retrieval Augmented Generation scenarios), with **Azure storage** and databases for data retrieval, and with Azure’s identity management (Azure AD/Entra ID) for secure authentication[[4]](https://docs.spring.io/spring-ai/reference/api/chat/azure-openai-chat.html). Azure also provides monitoring for the usage of the OpenAI service (e.g., through Azure Monitor).

- **Access and Pricing:** Azure OpenAI is a controlled service – you may need approval to use it. Once you have access, you can use the Azure Portal to create a resource. Usage is billed per 1,000 tokens similar to OpenAI’s pricing, but billing goes through your Azure subscription. An advantage is that you can set quotas, get enterprise billing, and have **regional control** (choose an Azure region that hosts your data).

- **Differences from OpenAI API:** When using Azure OpenAI, you must specify the **endpoint** and a **deployment name** for the model, rather than just a model name. For example, if you deploy GPT-4 and call it "my-gpt4" on Azure, you will use your endpoint URL and that deployment name in API calls. This is a key difference: _Azure uses a two-step process (deploy model, then use deployment) whereas OpenAI API uses model names directly_. Spring AI’s Azure integration accounts for this by requiring an endpoint and a deployment name in configuration[[4]](https://docs.spring.io/spring-ai/reference/api/chat/azure-openai-chat.html).


Overall, Azure OpenAI lets you incorporate OpenAI’s cutting-edge AI models in applications while leveraging Azure’s ecosystem. In our context, we will use Spring AI to call Azure OpenAI’s models, achieving an enterprise-ready AI application in Spring Boot.

---

## Prerequisites

Before we begin coding, make sure you have the following prerequisites in place:

- **Java Development Kit (JDK) 17+**: Ensure you have JDK 17 or later installed, because Spring Boot 3 (required by Spring AI) supports Java 17 or higher.
- **Build Tool**: Maven or Gradle set up in your environment for building the Spring Boot project. We will show Maven usage in examples.
- **Spring Boot and Spring AI**: Familiarity with Spring Boot basics is helpful. You should have Spring Boot (preferably 3.x) on your classpath. Spring AI is delivered as Spring Boot starter libraries we will add to the project.
- **Azure Subscription with Azure OpenAI Access**: You need an Azure account with access to the Azure OpenAI Service. If you don’t have the OpenAI service enabled, you may need to request access through Azure’s portal. Once enabled:
    - Create an **Azure OpenAI resource** in the Azure Portal (choose a region, resource name, etc.).
    - Within that resource, deploy a model (e.g., GPT-3.5-Turbo or GPT-4). Give the deployment a name (e.g., “`gpt-35-turbo`” or use the default name suggested).
    - Retrieve your **Azure OpenAI API key** and **Endpoint URL** from the Azure Portal. The endpoint URL will look like `https://<your-resource-name>.openai.azure.com/`. Keep these values handy for configuration[[2]](https://piotrminkowski.com/2025/03/25/spring-ai-with-azure-openai/).
- **Internet Access**: The application will need to call the Azure OpenAI service over the internet (unless you set up a private VNet integration). Ensure your development environment can reach Azure endpoints.
- **Development Environment**: An IDE like IntelliJ, Eclipse, or VSCode can be used for convenience, especially when working with Spring projects.

With these prerequisites satisfied, we’re ready to start building the application.

---

## Setting Up the Spring AI Project

We will begin by creating a Spring Boot project and adding Spring AI and Azure OpenAI dependencies.

**1. Create a New Spring Boot Project:** The easiest way is to use https://start.spring.io. Spring Initializr now includes options for Spring AI. You can do the following:

- Go to **start.spring.io** in your browser.
- Fill in project metadata (Group, Artifact, Java version, etc.). Select **Spring Boot 3.x** (latest stable).
- Under **Dependencies**, add **Spring Web** (for a web app with REST endpoints) and **Spring AI**. If Spring AI isn’t directly listed, you can alternatively add the dependency manually as shown next.
- Generate the project to download a pre-configured template. This project will contain a Maven `pom.xml` (or Gradle build file) with Spring Boot and we will add Spring AI starter to it.

Alternatively, if you’re not using Initializr UI, create a standard Spring Boot project and update its pom.xml to include Spring AI. For Azure OpenAI integration, include the **Spring AI Azure OpenAI Starter** dependency. For example, in Maven, add:

```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-azure-openai-spring-boot-starter</artifactId>
    <version>1.0.0</version>  <!-- Use the latest Spring AI version -->
</dependency>
```

[[2]](https://piotrminkowski.com/2025/03/25/spring-ai-with-azure-openai/)[[5]](https://www.sourcecodeexamples.net/2024/05/integrating-spring-ai-with-azure-openai.html)

This will pull in all necessary Spring AI libraries to work with Azure OpenAI. (If you plan to use the OpenAI API directly instead of Azure, you would use `spring-ai-openai-spring-boot-starter`, but in our case we’re focusing on Azure OpenAI.)

**2. (Optional) Include Spring AI BOM:** Spring may provide a Bill of Materials (BOM) for Spring AI. If so, you can import it to manage versions. Otherwise, ensure the `version` (like 1.0.0) is correct for all Spring AI dependencies you add. The starter will transitively include Azure’s AI SDK and Spring AI core libraries.

**3. Verify Project Structure:** After adding the dependency, your project should have Spring Boot and Spring AI on the classpath. You can now write Spring components that use Spring AI’s APIs.

At this point, we have a Spring Boot skeleton application with the necessary libraries to use Spring AI and Azure OpenAI. Next, we need to configure the connection to Azure OpenAI.

---

## Configuring Azure OpenAI in Spring AI

To allow our Spring application to access Azure OpenAI, we must provide the service credentials (endpoint and key) and any model settings. Spring AI’s Azure starter will pick up these settings from the Spring Boot configuration.

**1. Add Azure OpenAI Credentials to Application Properties:** Open your `src/main/resources/application.properties` (or `.yml`) file and add the following properties (replace placeholders with your actual values):

```
spring.ai.azure.openai.api-key = <YOUR_AZURE_OPENAI_API_KEY>
spring.ai.azure.openai.endpoint = <YOUR_AZURE_OPENAI_ENDPOINT_URL>
spring.ai.azure.openai.chat.options.deployment-name = <YOUR_MODEL_DEPLOYMENT_NAME>
```

Here:

- `api-key` is one of the keys from your Azure OpenAI resource (usually labeled as Key1/Key2 in the Azure Portal).
- `endpoint` is the URL of your Azure OpenAI resource (e.g., `https://your-resource-name.openai.azure.com/`).
- `deployment-name` is the name of the model deployment you created (for example, “gpt-35-turbo” or “gpt-4” or a custom name you chose).

Spring AI will use these properties to configure an Azure OpenAI client. The Azure OpenAI starter **requires an endpoint and API key**; with those, it can automatically create an `OpenAIClient` or `ChatClient` bean for you[[4]](https://docs.spring.io/spring-ai/reference/api/chat/azure-openai-chat.html). For example, if we put these in `application.yml` with environment variables, it could look like:

```yaml
spring:
  ai:
    azure:
      openai:
        api-key: ${AZURE_OPENAI_API_KEY}        # set via environment variable for security
        endpoint: ${AZURE_OPENAI_ENDPOINT}      # set via environment variable
        chat:
          options:
            deployment-name: ${AZURE_OPENAI_MODEL}  # e.g., "gpt-35-turbo"
```

Using environment variables (as shown above) is a recommended practice so that you don’t hard-code secrets in your source. Spring AI supports using Spring Expression Language to reference environment vars for these sensitive values[[4]](https://docs.spring.io/spring-ai/reference/api/chat/azure-openai-chat.html).

**2. Verify Configuration:** Double-check that the endpoint URL and deployment name correspond exactly to what you set up on Azure. A common mistake is a typo in the URL or using the wrong region. The endpoint should typically end in `.openai.azure.com/`. Also ensure the deployment name is correct – if you don’t specify one, Spring AI’s default might be a placeholder like `gpt-4o` (a default alias used in examples) which **must match an actual deployment**. If you used a different model or name, you have to override this property[[2]](https://piotrminkowski.com/2025/03/25/spring-ai-with-azure-openai/). For instance, Piotr’s blog notes that the Spring AI Azure starter by default expects a deployment named “gpt-4o”; if you deploy a different model, you must set the `deployment-name` property accordingly[[2]](https://piotrminkowski.com/2025/03/25/spring-ai-with-azure-openai/).

With these properties set, our Spring Boot application is configured to use Azure OpenAI. On startup, Spring AI will detect the Azure OpenAI starter and establish a connection using our provided credentials. Now we can write code to interact with the AI model.

---

## Step-by-Step Development: Integrating Spring AI with Azure OpenAI

Now comes the fun part – writing the code to build an AI-powered feature. We’ll create a simple example: a REST endpoint that takes a user question and returns an answer generated by the Azure OpenAI GPT model. This will demonstrate the essential integration of Spring AI’s API with Azure’s model.

### Step 1: Create a Chat Client or Service

Spring AI abstracts the AI model behind a `ChatClient` interface (for chat models) or specific client classes. Since we have included the Azure OpenAI starter and configured it, we can inject a chat client directly. We’ll use the chat completion capability (suitable for Q&A or conversations).

In a Spring Boot application, you can get a `ChatClient` bean through dependency injection. For example, you might create a REST controller like this:

```java
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    // Spring will auto-configure a ChatClient.Builder for Azure OpenAI and inject it
    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/ask")
    public String askQuestion(@RequestParam String question) {
        // Use the ChatClient to send the user's question and get a response
        return chatClient.prompt()
                         .user(question)
                         .call()
                         .content();
    }
}
```

[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/)

Let’s break down what’s happening here:

- We marked the class with `@RestController` so Spring MVC will recognize it as a web controller.
- We inject a `ChatClient.Builder` in the constructor. The Spring AI starter auto-configures a `ChatClient.Builder` bean that is pre-configured for the Azure OpenAI chat model (using the settings from our properties). We call `chatClientBuilder.build()` to get a `ChatClient` instance.
- The `/ask` endpoint accepts a query parameter `question` from the user. Inside the method, we call `chatClient.prompt().user(question)...` to send this question to the model:
    - `chatClient.prompt()` starts building a prompt.
    - `.user(question)` adds the user’s question as a user message in the prompt (there are also options to add a system message or assistant message if needed).
    - `.call()` sends the prompt to the AI model (Azure OpenAI in this case) and returns a response object.
    - `.content()` extracts the generated answer text from the response.
- The method returns the model’s answer as a simple string. Spring will return this in the HTTP response body.

This concise code leverages Spring AI’s fluent API. In one line, it sends the question and returns the AI’s answer. Under the hood, Spring AI handled constructing the Azure OpenAI client call and retrieving the result.

**Running this endpoint:** If you start your Spring Boot application (e.g., using `mvn spring-boot:run` or running the main class), you can test the `/ask` endpoint. For example, go to `http://localhost:8080/ask?question=What is the capital of France?` – the application should return “Paris” (as an AI-generated answer). Each call will be processed by Azure’s GPT model via the configured deployment.

### Step 2: Create Additional Services or Components (Optional)

In our simple example, we put everything in a controller for brevity. In a larger application, you might separate concerns:

- Have a Service layer component (e.g., `QAService`) that uses the `ChatClient` to perform queries, and the controller calls this service.
- If you want to use a lower-level client, Spring AI also provides classes like `AzureOpenAiClient` for more direct control[[5]](https://www.sourcecodeexamples.net/2024/05/integrating-spring-ai-with-azure-openai.html). For instance, you could use an `AzureOpenAiClient.createCompletion(...)` for completion APIs (non-chat). However, using `ChatClient` as above is often simpler for chat-based interactions.

You could also configure the `ChatClient` with Advisors if implementing advanced flows. For example, adding a memory advisor to carry conversation context, or a retrieval advisor to fetch knowledge from a database. But those go beyond the basic setup.

### Step 3: Test the Integration

After implementing the endpoint or service, test it thoroughly:

- **Local Testing:** Run the app locally and use curl or a browser to hit the endpoints. Verify that you get sensible responses from the model. If there’s any error (like a 401 Unauthorized or time out), re-check your configuration. An HTTP 401 error could indicate an incorrect API key. A 404 or "model not found" error might mean the deployment name or endpoint is wrong.
- **Logging:** Check your application logs. Spring AI and the Azure SDK might log useful information. On startup, you should see logs indicating that an Azure OpenAI client has been configured. On each call, you might see requests being made to the Azure endpoint.
- **Iterate on Prompt:** Try different questions. The example endpoint simply forwards the question to the model. You might want to craft a system message (e.g., “You are an expert assistant…”) for better responses. Spring AI’s API allows adding a system prompt via `.system("...")` in the prompt builder if needed.

Once you have the basic Q&A working, you have essentially integrated Spring AI with Azure OpenAI successfully. You can then extend this skeleton for more complex scenarios.

---

## Best Practices for Spring AI + Azure OpenAI Development

When developing AI applications with Spring AI and Azure OpenAI, keep these best practices in mind:

- **Securely Manage API Keys:** Never hard-code secrets like API keys in your source code. Use environment variables or Azure Key Vault to store keys, and reference them in `application.properties` using placeholders[[4]](https://docs.spring.io/spring-ai/reference/api/chat/azure-openai-chat.html). In our configuration above, we demonstrated using `${AZURE_OPENAI_API_KEY}` from the environment rather than writing the key in plain text. This prevents accidental leakage of credentials (e.g., if you push code to a repository).

- **Optimize Model Usage:** AI model calls can be expensive and have rate limits. Be mindful of how you use the API:

    - Set reasonable limits on the prompt size and response length using parameters (Spring AI allows configuration of options like `maxTokens`, `temperature`, etc. via properties or API)[[4]](https://docs.spring.io/spring-ai/reference/api/chat/azure-openai-chat.html). For example, you can set `spring.ai.azure.openai.chat.options.max-tokens` to control the length of responses.
    - Cache or store results if appropriate, to avoid duplicate calls for the same input when applicable.
    - Use streaming if you need large responses incrementally (Spring AI supports reactive streaming of responses, if your app needs it).
- **Handle Errors and Timeouts:** Integrate proper error handling. Azure OpenAI might throw exceptions for various reasons (invalid input, service downtime, rate limit exceeded). Use try-catch around the `.call()` and provide user-friendly error messages or fallbacks. Also consider setting timeouts for calls if the default is too high, to avoid hanging requests.

- **Monitor Usage and Performance:** Leverage Spring Boot’s monitoring integrations:

    - Spring AI integrates with **Micrometer** metrics to track AI usage[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/). Expose these metrics (for example, via Actuator and a monitoring system) to watch how many calls are made, latency of requests, token consumption, etc. Monitoring will help in optimizing and spotting issues (like spikes in usage or slow responses).
    - Azure also provides metrics on the service side (in Azure Portal) – keep an eye on those, especially token quotas and errors.
- **Use Advisors for Advanced Patterns:** If your application needs features like conversation memory or knowledge base lookup, use Spring AI’s advisor API instead of writing that logic from scratch. For example, to implement a conversational memory, Spring AI offers a `ChatMemory` store and advisor that can be attached so the model retains context across calls[[2]](https://piotrminkowski.com/2025/03/25/spring-ai-with-azure-openai/)[[2]](https://piotrminkowski.com/2025/03/25/spring-ai-with-azure-openai/). For Q&A over documents, the `QuestionAnswerAdvisor` can be used with a vector store to do retrieval augmented generation[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/). These ready-made components embody best practices for those patterns (e.g., how to inject retrieved text into the prompt, etc.).

- **Test with Mocked Providers:** During development, you might not always want to call the real Azure OpenAI (especially if you run tests frequently and want to avoid costs). Consider using Spring AI’s abstraction to swap in a mock or a smaller model. Since Spring AI decouples the implementation, you could configure a local model or use OpenAI’s free models (if available) in non-production profiles.

- **Stay Updated on Model Improvements:** Azure and Spring AI are evolving. New versions of Spring AI may add features or change property names. For example, earlier versions had slightly different property keys for model names. Keep an eye on Spring AI’s release notes. Similarly, Azure might release new model versions or require updates (like when GPT-4 is updated). Upgrading your deployment or adjusting parameters might be necessary over time.


Following these practices will help ensure your application is robust, secure, and maintainable.

---

## Common Pitfalls and How to Avoid Them

While developing your AI application, beware of these common pitfalls:

- **Incorrect Configuration Keys:** A minor mistake in configuration can cause major headaches. For instance, using `azure.openai.api.key` instead of `spring.ai.azure.openai.api-key` (note the dots and dashes) will mean the app doesn’t actually set the key. Always double-check property names against the Spring AI reference docs. The keys are hierarchical under `spring.ai.azure.openai.*` for Azure OpenAI[[4]](https://docs.spring.io/spring-ai/reference/api/chat/azure-openai-chat.html).

- **Missing Model Deployment:** Azure OpenAI requires you to deploy a model to your resource _before_ you can use it. Forgetting this step (or using the wrong deployment name) is a frequent issue. If you see errors like “The model does not exist” or “404 Not Found,” it often means the deployment name in your config doesn’t match an existing deployment. Make sure you have created a deployment of the model (e.g., "gpt-35-turbo") in the Azure Portal, and use that exact name in `deployment-name`[[2]](https://piotrminkowski.com/2025/03/25/spring-ai-with-azure-openai/).

- **Using OpenAI Keys with Azure Endpoint (or vice-versa):** Remember that Azure OpenAI uses **its own API keys** (provided by Azure) and endpoints. These are _not_ interchangeable with an OpenAI API key and `api.openai.com` endpoint. Ensure you’re using the Azure credentials in Spring AI config. If you accidentally use an OpenAI key with an Azure endpoint, you’ll get authentication errors. Spring AI’s config distinguishes these: if `spring.ai.azure.openai.api-key` is set, it goes to Azure; if you instead set `spring.ai.azure.openai.openai-api-key`, it would target OpenAI’s public API[[4]](https://docs.spring.io/spring-ai/reference/api/chat/azure-openai-chat.html). Use the correct property for your scenario.

- **Dependency Mismatch:** If you forgot to add the Spring AI starter or have the wrong version, you might encounter `NoSuchBeanDefinitionException` or ClassNotFound errors at runtime. For instance, if the `ChatClient` bean is not configured, the injection will fail. To avoid this:

    - Use Spring Initializr which brings consistent versions.
    - Ensure the Spring AI version is compatible with your Spring Boot version (Spring AI 1.0 works with Spring Boot 3.4 and above per docs).
    - If you see autoconfiguration reports indicating beans were not created, verify that the starter JAR is on the classpath.
- **Not Handling Rate Limits:** Azure OpenAI (like OpenAI) has rate limits. If your application sends too many requests too quickly, you may start getting HTTP 429 Too Many Requests responses. Plan for this by implementing exponential backoff retries or queueing requests if needed for high-throughput scenarios. Also consider request consolidation if possible (though often each user query is separate).

- **Large Prompt or Response Issues:** Sending very large prompts or asking for very long outputs can hit token limits (Azure enforces the same token limits as the model’s spec, e.g., ~4k or ~8k tokens for GPT-3.5, up to 32k for some GPT-4 deployments). If you exceed limits, the API will return an error. To avoid issues:

    - Break input text into summaries or chunks.
    - Use smaller models for summarizing long text before feeding into larger model.
    - Set `maxTokens` and use streaming for large outputs.

By being mindful of these pitfalls, you can troubleshoot issues faster. Always read error messages from the API – they often tell you exactly what’s wrong (e.g., missing permission, model not found, etc.) – and refer back to configuration and documentation when something isn’t working as expected.

---

## Deploying the Application

Deploying a Spring AI application is essentially the same as deploying any Spring Boot application, with the added step of managing your configuration (especially the API key) in production.

**1. Packaging the Application:** You can build your app into an Uber JAR with Maven or Gradle (`mvn clean package`) or into a Docker container if you prefer. Spring Boot will produce a runnable JAR (`java -jar myapp.jar`) which contains all dependencies, including Spring AI and Azure SDK.

**2. Configuring for Production:** Ensure you supply the Azure OpenAI credentials in the production environment. The best practice is to use environment variables or an external config file:

- If deploying to Azure App Service or Azure Spring Apps, you can set environment variables for `AZURE_OPENAI_API_KEY`, `AZURE_OPENAI_ENDPOINT`, etc., and your `application.properties` (which we set up earlier) will pick those up[[4]](https://docs.spring.io/spring-ai/reference/api/chat/azure-openai-chat.html).
- Double-check that no sensitive info is in your code or default config. For instance, do not include the real API key in the packaged `application.properties` if the code will be public.

**3. Choose a Deployment Target:** You have several options on Azure (since our scenario is Azure-focused):

- **Azure App Service:** You can deploy the JAR or containerize the app and deploy. App Service is a straightforward way to host Spring Boot apps. It allows setting application settings (environment variables) for your keys.
- **Azure Container Apps or AKS:** If your use case is microservices or you already use Kubernetes, you can containerize the Spring Boot app. Spring Boot and Spring AI don’t require any special container setup beyond any normal Java app. Make sure to provide env vars in your container spec or orchestrator settings.
- **Azure Spring Apps (ASA):** Azure has a service specifically for Spring Boot apps (formerly Azure Spring Cloud). This could also be used to deploy by just pointing it to your JAR or project, and it will handle scaling and management.
- **Virtual Machines or Other:** Of course, you can also deploy on a VM (Linux or Windows with Java installed) or any platform of your choice. The key is that the app must be able to reach the Azure OpenAI endpoint.

Azure’s flexibility means you can run the app on various compute services. Microsoft even highlights that you can **run Spring AI apps or agents on Azure App Service, Azure Kubernetes Service (AKS), Azure Container Apps, or Virtual Machines** – whichever fits your needs[[3]](https://techcommunity.microsoft.com/blog/appsonazureblog/spring-ai-1-0-ga-is-here---build-java-ai-apps-end-to-end-on-azure-today/4414763).

**4. Testing in Production:** Once deployed, test the endpoint (or whatever interface your app provides) in the production environment. Make sure the network connectivity to Azure OpenAI is working (if under corporate networks, ensure no firewall is blocking the Azure endpoint). Also monitor through Azure’s metrics how the model is being called.

**5. Scaling Considerations:** If your application needs to handle many concurrent users or heavy load:

- Scale up/out your application instances as needed (App Service and AKS can scale instances, ASA can auto-scale).
- Azure OpenAI itself has throughput limits per instance of the resource; if you anticipate very high load, you might need to request higher quota from Azure or distribute load across multiple OpenAI resource deployments.
- Ensure that adding more app instances doesn’t lead to excessive contention for the rate limit of the single Azure OpenAI resource.

Deployment is typically straightforward – as long as your configuration is correct, the app should run anywhere. Just be cautious to keep credentials safe and to tune your app for the production environment (time out settings, memory, etc., as you would for any app).

---

## Advanced Features and Next Steps

Once you have the basic application running, you might want to explore more advanced capabilities of Spring AI and Azure OpenAI:

- **Retrieval-Augmented Generation (RAG):** Spring AI can integrate with vector databases to enable RAG[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/). This means you can supply your model with external knowledge on the fly. For example, if you have a document store or FAQ database, you can use Spring AI’s Vector Store API to fetch relevant data based on the question and insert it into the prompt before the model answers. This helps in building apps that **ground their answers in specific data** (and is great for avoiding hallucinations). Azure offers services like Azure Cognitive Search with vector support or a managed Postgres with pgvector which Spring AI can utilize[[3]](https://techcommunity.microsoft.com/blog/appsonazureblog/spring-ai-1-0-ga-is-here---build-java-ai-apps-end-to-end-on-azure-today/4414763). Consider this if your app needs domain-specific knowledge.

- **Conversational Memory:** For multi-turn conversations, Spring AI provides **Chat Memory** support. You saw a hint of this in our single-turn Q&A (we just passed the user question). With memory, you can carry the dialogue context. Spring AI can store conversation history in a store like Cosmos DB or Redis, via a `ChatMemory` interface[[3]](https://techcommunity.microsoft.com/blog/appsonazureblog/spring-ai-1-0-ga-is-here---build-java-ai-apps-end-to-end-on-azure-today/4414763). By adding a memory advisor (`PromptChatMemoryAdvisor`), the chat history will be automatically included in each prompt[[2]](https://piotrminkowski.com/2025/03/25/spring-ai-with-azure-openai/). This is useful for chatbot applications where the AI should remember previous queries in the session.

- **Function Calling / Tools Integration:** Newer AI models can follow instructions to call external functions (for example, “Check weather for Tokyo” could trigger a weather API call). Spring AI supports this via its **Tool API and MCP (Model Context Protocol)**[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/). You can define methods in your Spring components and annotate them as tools that the AI can call. Spring AI will handle the orchestration. This enables creating AI "agents" that can perform actions like database queries or API calls based on model output. If your use case requires the AI to perform tasks or calculations, look into Spring AI’s tool calling support.

- **Multi-Modal AI**: Azure OpenAI now also offers image generation (DALL-E) and perhaps other modalities in the future. Spring AI, as noted, supports image generation models (like via stability.ai or others) and could integrate with Azure’s if available[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/). You might expand your application to generate images or analyze audio if needed, by adding the relevant Spring AI starters and using those client APIs.

- **Structured Output Mapping:** Spring AI can map AI responses to Java objects. For example, if you expect the model to output JSON, you can directly parse it into a POJO using Spring AI’s response mapping features[[2]](https://piotrminkowski.com/2025/03/25/spring-ai-with-azure-openai/). In the earlier example from Piotr’s blog, a prompt asked for a JSON list of persons and then mapped it to a `List<Person>` object[[2]](https://piotrminkowski.com/2025/03/25/spring-ai-with-azure-openai/). This strongly-typed approach is useful for ensuring the AI’s output fits a schema.

- **Fine-tuning and Custom Models:** Azure OpenAI supports fine-tuning certain models (like GPT-3). If you fine-tune a model for your domain, you can deploy that fine-tuned model on Azure and simply use its deployment name in Spring AI config. Spring AI will treat it like any other model. Fine-tuning can improve responses for specialized tasks.


Incorporating these advanced features can take your application to the next level – enabling more interactive, context-aware, and specialized AI behaviors. Each of these topics could be a tutorial on its own, so refer to Spring AI’s reference documentation and examples for guidance on implementing them.

---

## Troubleshooting Tips

Despite careful planning, you might encounter issues during development. Here are some troubleshooting tips for common problems:

- **Unable to Connect to Azure Endpoint:** If the app isn’t reaching Azure OpenAI at all (no logs of requests, immediate errors), check network issues. From your development machine or server, ensure you can ping or curl the Azure OpenAI endpoint. Corporate firewalls or proxies might block the connection. Also verify the endpoint URL is correct (should start with `https://` and end with `.openai.azure.com/` and no extra path; the Spring AI client will handle the rest path).

- **Authentication Errors (401 Unauthorized):** This means the API key was not accepted. Possible causes: using the wrong key (e.g., confusion between Key and Endpoint or using OpenAI key), or the key not being passed at all. Use Spring Boot’s actuator or debugging to print out effective configuration if needed. Ensure `spring.ai.azure.openai.api-key` is populated (you could temporarily log `System.getenv("AZURE_OPENAI_API_KEY")` if using env var to ensure it’s set). If using Azure AD (Entra ID) authentication for a managed identity, ensure the identity has access and you configured accordingly (which would differ from the API key approach).

- **Model Not Found (404 or 400 Bad Request):** As mentioned in pitfalls, this usually points to a mismatch in deployment name or model name. Verify in Azure Portal the exact deployment name. In some cases, it could also be a wrong API version – though Spring AI likely uses the correct API version under the hood. If you manually created an `AzureOpenAiClient` with an endpoint, ensure the endpoint has the resource name correctly (including the region, etc.).

- **Unexpected Responses or Poor Quality:** If the AI’s answers are not what you expect (nonsense or irrelevant), it might not be a code issue but a prompt engineering issue. Experiment with:

    - Adding a system message to guide the AI’s behavior (Spring AI `ChatClient` allows `.system("You are an assistant that answers about X")` before the `.user(...)` call).
    - Adjusting parameters like `temperature` (lower values for more focused responses) or `top_p`.
    - If using a smaller model (like GPT-3.5) and getting inadequate answers, consider using a more capable model (GPT-4) if available, though it has higher cost/latency.
    - Use Azure OpenAI’s playground to test prompts, then transfer successful approaches to your code.
- **Performance Issues:** If responses are very slow, note that network latency and model speed both affect total time. GPT-4 is slower than GPT-3.5, for example. If you need faster responses and can sacrifice some quality or length, try GPT-3.5 Turbo. Also ensure you’re not doing something in code that blocks unnecessarily. Because Spring AI can do synchronous calls (as we did with `.call()`), each request thread waits for the response. Consider using asynchronous or reactive approach (Spring WebFlux with Spring AI’s reactive support) if you need to handle many concurrent requests more efficiently. Spring AI provides a WebFlux SSE (Server-Sent Events) support that can stream results without tying up threads[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/).

- **Debugging Advisors and Tools:** If you employed advisors (memory, RAG) or tool calling and something isn’t working, you can often enable DEBUG logging for the Spring AI packages to see what prompt is being sent or how the tool calls are being decided. This can shed light on whether the advisor injected context or not, etc.

- **Resource Limits/Exceptions:** Keep an eye on the application logs and Azure’s service logs for any exceptions. Azure OpenAI might return specific error codes for rate limits or content filter triggers (if the model refuses to answer due to policy). Handle those gracefully – maybe inform the user that the query can’t be answered or implement a retry after a delay.


If all else fails, consulting the Spring AI reference documentation and the Azure OpenAI troubleshooting guides can provide further insights. Spring’s community (e.g., Stack Overflow, Spring forums) might also have Q&A for any issues you run into, since Spring AI is relatively new but growing in adoption. The key is to isolate whether a problem lies in the Spring AI integration (e.g., config/usage of the library) or on the Azure OpenAI side (e.g., service limitations or prompt issues). Logging and careful testing at each step will help pinpoint where things go wrong.

---

## Conclusion

In this detailed guide, we covered how to build an AI-powered Spring Boot application using **Spring AI 1.0** and **Azure OpenAI**. We started with the basics of what these technologies are: Spring AI brings first-class AI support to the Spring framework with a provider-agnostic approach, and Azure OpenAI offers OpenAI’s models on Azure with enterprise features. We then walked through setting up a project, including the necessary dependencies and configuration to connect to Azure’s OpenAI service. With a simple REST controller example, we demonstrated how to send a prompt to the GPT model and retrieve a response using only a few lines of code, thanks to Spring AI’s convenient abstractions[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/).

Along the way, we discussed best practices like secure key management, monitoring, and prompt optimization, and highlighted common pitfalls such as configuration mismatches and deployment issues – with guidance to avoid them. We also touched on deploying the application to Azure, ensuring that our integration works in a cloud environment just as in development. For those looking to go further, we introduced advanced Spring AI features like Retrieval Augmented Generation, chat memory, and tool calling, which can be leveraged to build more sophisticated AI solutions that interact with data and services beyond just the core model[[1]](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/).

**By integrating Spring AI with Azure OpenAI, Java developers can now easily imbue Spring Boot applications with powerful AI capabilities** – whether it’s building an intelligent chatbot, an assistant that helps users with queries, or an app that generates content. The framework handles much of the heavy lifting, letting you follow familiar Spring patterns to configure and call AI models. Azure’s reliable AI hosting ensures these capabilities run at scale and securely.

Now that you have the step-by-step process and code examples, you can start developing your own AI application. Experiment with different prompts and advisors, integrate your domain data for smarter responses, and create innovative features that were not possible before. With Spring AI and Azure OpenAI, the world of AI is accessible right from the Spring ecosystem – enabling you to build the next generation of intelligent applications.


References

[1] [Spring AI 1.0 Released, Streamlines AI Application Development ... - InfoQ](https://www.infoq.com/news/2025/05/spring-ai-1-0-streamlines-apps/)

[2] [Spring AI with Azure OpenAI - Piotr's TechBlog](https://piotrminkowski.com/2025/03/25/spring-ai-with-azure-openai/)

[3] [Spring AI 1.0 GA is Here - Build Java AI Apps End-to-End on Azure Today](https://techcommunity.microsoft.com/blog/appsonazureblog/spring-ai-1-0-ga-is-here---build-java-ai-apps-end-to-end-on-azure-today/4414763)

[4] [Azure OpenAI Chat :: Spring AI Reference](https://docs.spring.io/spring-ai/reference/api/chat/azure-openai-chat.html)

[5] [Source Code Examples](https://www.sourcecodeexamples.net/2024/05/integrating-spring-ai-with-azure-openai.html)
