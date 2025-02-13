package org.example.domain.repository;

import org.example.domain.entity.Cliente;
import org.example.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

    @Query(" select p from Pedido p left join fetch p.items where p.id = :id ")
    Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);
}
