package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
        JpaSpecificationExecutor<Restaurante> {

        @Query("from Restaurante r join fetch r.cozinha")
        List<Restaurante> findAll();
        public List<Restaurante> findBytaxaFreteBetween(Double taxaInicial, Double taxaFinal);

        //Utilizando JPQL para Querys personalizadas
        @Query("from Restaurante where nome like %:nome% order by taxa_frete asc") //Arquivos grandes -> Arquivo META-INF
        public List<Restaurante> buscarPorNome(String nome);
}
