package com.facenet.mobileshopmanger.repository;

import com.facenet.mobileshopmanger.entity.Category;
import com.facenet.mobileshopmanger.entity.Phone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    boolean existsByNameAndModel(String name, String model);

    boolean existsByNameAndModelAndIdNot(String name, String model, Long id);

    @Query(value = "SELECT * FROM phones WHERE id = :id", nativeQuery = true)
    Optional<Phone> findByIdIgnoreSoftDeleted(@Param("id") Long id); // Tìm kiếm điện thoại theo ID, bỏ qua các bản ghi đã xóa mềm


    @Query(
            value = "SELECT * FROM phones WHERE is_deleted = true",
            countQuery = "SELECT COUNT(*) FROM phones WHERE is_deleted = true",
            nativeQuery = true
    )
    Page<Phone> findAllDeletedPhones(Pageable pageable);

}
