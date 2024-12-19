package com.test.atos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AtosApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtosApplication.class, args);
	}

}

/**
 * Architectural Style vs. Protocol:
 *
 * REST (Representational State Transfer):
 * A style that uses standard HTTP methods (GET, POST, PUT, DELETE) and typically JSON or XML payloads. It's resource-oriented and designed to be lightweight, stateless, and cacheable.
 * SOAP (Simple Object Access Protocol):
 * A protocol that defines a strict messaging framework based on XML. It relies on a WSDL (Web Services Description Language) contract and typically uses POST requests over HTTP.
 * Implementation Approach in Spring Boot:
 *
 * REST:
 * Implemented using @RestController and request mapping annotations like @GetMapping, @PostMapping. You just return objects or JSON directly, and Spring Boot automatically converts them to JSON or XML as needed.
 * SOAP:
 * Implemented using Spring Web Services (@Endpoint, @PayloadRoot) and tied closely to schemas (XSDs). You generate Java classes from XML schemas and use message dispatchers. Communication follows the WSDL contract, and messages are always XML-based.
 * Contract and Tooling:
 *
 * REST:
 * There’s no strict contract enforced by the architecture. Endpoints and payloads can be documented using tools like OpenAPI/Swagger, but the service itself does not enforce a strict schema.
 * SOAP:
 * SOAP services enforce a contract defined by WSDL and XSDs. Clients often generate code from the WSDL, ensuring client and server agree strictly on message structure and types.
 * Message Format:
 *
 * REST:
 * Typically uses JSON (most common today) or XML. Less verbose and easier to read. The message format is flexible and can evolve without breaking the contract as long as backward compatibility is maintained.
 * SOAP:
 * Messages are always XML, more verbose, and follow a strict envelope/header/body structure as defined by the SOAP specification.
 * Tooling and Integration Complexity:
 *
 * REST:
 * Very simple to set up with Spring Boot. Just write controllers, return POJOs, and rely on Spring’s message converters.
 * SOAP:
 * Requires setting up endpoints, message dispatchers, marshaller/unmarshaller configurations, and typically code generation from XSDs. More boilerplate is involved.
 * In essence, REST in Spring Boot provides a quick and flexible way to build APIs, relying on HTTP verbs and JSON, while SOAP in Spring Boot enforces a strict XML contract and uses a more formal service description through WSDL.
 */
