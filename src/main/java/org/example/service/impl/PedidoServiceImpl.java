package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.domain.entity.Cliente;
import org.example.domain.entity.ItemPedido;
import org.example.domain.entity.Pedido;
import org.example.domain.entity.Produto;
import org.example.domain.enums.StatusPedido;
import org.example.domain.repository.Clientes;
import org.example.domain.repository.ItemsPedido;
import org.example.domain.repository.Pedidos;
import org.example.domain.repository.Produtos;
import org.example.exception.PedidoNaoEncontradoException;
import org.example.exception.RegraNegocioException;
import org.example.rest.dto.ItemPedidoDTO;
import org.example.rest.dto.PedidoDTO;
import org.example.service.PedidoService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedido itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(()-> new RegraNegocioException("Código de cliente inválido."));

        Pedido novoPedido = new Pedido();
        novoPedido.setTotal(dto.getTotal());
        novoPedido.setDataPedido(LocalDate.now());
        novoPedido.setCliente(cliente);
        novoPedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemPedidos = converterItems(novoPedido, dto.getItems());
        repository.save(novoPedido);
        itemsPedidoRepository.saveAll(itemPedidos);
        novoPedido.setItems(itemPedidos);

        return novoPedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository
                .findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow( () -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(()-> new RegraNegocioException("Código de Produto inválido: " + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
