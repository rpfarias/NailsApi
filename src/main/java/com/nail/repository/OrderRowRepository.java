package com.nail.repository;

import com.nail.domain.model.OrderRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional // Coloquei nos métodos que precisam de transação. garante a integridade
public interface OrderRowRepository extends JpaRepository<OrderRow, Long> {

    @Modifying
    @Query(value = "DELETE FROM tbl_order_row o WHERE o.id = :id", nativeQuery = true)
    void deleteOrderRowById(@Param("id") Long id);

    //@Modifying - é usada para aprimorar a anotação @Query para executar não apenas consultas SELECT,
    // mas também INSERT , UPDATE , DELETE e até mesmo consultas DDL.
    @Modifying
    @Query(value = "UPDATE tbl_order_row o SET product_id = null WHERE o.id = :id", nativeQuery = true)
    void removeProductFromOrderRowById(@Param("id") Long id);
}
