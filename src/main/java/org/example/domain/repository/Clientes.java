package org.example.domain.repository;

import org.example.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface Clientes extends JpaRepository<Cliente, Integer > {

    @Query(value = " select * from cliente c where c.nome like '%:nome%' ",nativeQuery = true)
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    boolean existsByNome(String nome);

    @Query(" select c from Cliente c left join fetch c.pedidos where c.id = :id ")
    Cliente findClienteFetchPedidos( @Param("id") Integer id );


}
