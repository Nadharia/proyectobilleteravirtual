package com.example.demo.repository;



import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.entitys.Movimiento;


public interface MovimientoRepository extends JpaRepository<Movimiento, Long>{
	List<Movimiento> findByCuenta_id(Long id);
	int countByCuenta_Id(Long Id);
	

}
