package br.com.soupaulodev.forumhub.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "ForumHub API",
                version = "1.0",
                description = "ForumHub é uma rede social focada em criar um ambiente colaborativo para discussões e interação entre usuários. Este documento descreve a API RESTful do ForumHub."
        ),
        servers = {
                @Server(url = "/", description = "Default Server"),
                @Server(url = "https://api.example.com", description = "Production Server")
        }
)
public class SwaggerConfig {}
