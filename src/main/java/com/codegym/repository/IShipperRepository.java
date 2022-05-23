package com.codegym.repository;

import com.codegym.model.entity.Shipper;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IShipperRepository extends PagingAndSortingRepository<Shipper,Long> {

}
