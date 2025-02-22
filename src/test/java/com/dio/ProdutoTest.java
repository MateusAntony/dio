package com.dio;


import com.dio.entity.Produto;
import com.dio.exception.ProductPriceException;
import com.dio.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProdutoTest {

    @Autowired
    private ProdutoService produtoService;

    @Test
    public void verificarValorNegativoProduto() throws Exception{

        Produto produto = new Produto();

        produto.setNome("Teste");
        produto.setPreco(-10.00);

        assertThrows(ProductPriceException.class,() -> produtoService.save(produto));



    }


}
