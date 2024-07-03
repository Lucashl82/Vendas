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
            clientes.salvar(new Cliente("Lucas"));
            clientes.salvar(new Cliente("Dougllas"));

            List<Cliente> todosClientes = clientes.obterTodos();
            todosClientes.forEach(System.out::println);
//
//            System.out.println("Atulizando clientes");
//            todosClientes.forEach(c -> {
//                c.setNome(c.getNome() + " atualizado.");
//                clientes.atualizar(c);
//            });
//            System.out.println("Pesquisa por nome em especifico");
//            clientes.buscarPorNome("las").forEach(System.out::println);
//
////            clientes.obterTodos().forEach(c->{
////                clientes.deletar(c);
////            });
////            todosClientes =clientes.obterTodos();
//            if (todosClientes.isEmpty()) {
//                System.out.println("Não há clientes a serem exibidos.");
//            }else{
//                System.out.println("Exibindo todos clientes");
//                todosClientes.forEach(System.out::println);
//            }
        };
    }
    public static void main(String[] args) {

        SpringApplication.run(VendasApplication.class, args );

    }
}
