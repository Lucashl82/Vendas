package org.example;

import org.example.domain.entity.Cliente;
import org.example.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes) {
        return args -> {

            System.out.println("Salvando Clientes");
            clientes.save(new Cliente("Lucas"));
            clientes.save(new Cliente("Dougllas"));

            List<Cliente> result = clientes.encontrarPorNome("as");
            result.forEach(System.out::println);

        };
    }
    public static void main(String[] args) {

        SpringApplication.run(VendasApplication.class, args );

    }
}
