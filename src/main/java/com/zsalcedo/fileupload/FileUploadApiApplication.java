package com.zsalcedo.fileupload;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FileUploadApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileUploadApiApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info()
				.title("File Upload API")
				.version("1.0")
				.description("API que permite cargar y listar archivos."));
	}

}
