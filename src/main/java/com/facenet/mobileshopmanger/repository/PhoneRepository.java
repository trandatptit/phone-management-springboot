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

    /**
     * Kiểm tra xem điện thoại có tồn tại với tên và model cụ thể, ngoại trừ bản ghi có ID nhất định.
     *
     * @param name tên của điện thoại
     * @param model model của điện thoại
     * @param id ID của bản ghi cần loại trừ
     * @return true nếu tồn tại, false nếu không
     */
    boolean existsByNameAndModelAndIdNot(String name, String model, Long id);

    /*     * Tìm kiếm điện thoại theo tên và model, bỏ qua các bản ghi đã xóa mềm.
     *
     * @param name tên của điện thoại
     * @param model model của điện thoại
     * @return Optional chứa điện thoại nếu tìm thấy, hoặc rỗng nếu không tìm thấy
     */
    @Query(value = "SELECT * FROM phones WHERE id = :id", nativeQuery = true)
    Optional<Phone> findByIdIgnoreSoftDeleted(@Param("id") Long id); // Tìm kiếm điện thoại theo ID, bỏ qua các bản ghi đã xóa mềm


    /** Tìm kiếm tất cả điện thoại đã xóa mềm với phân trang.
     *
     * @param pageable thông tin phân trang
     * @return Page chứa danh sách điện thoại đã xóa mềm
     */
    @Query(
            value = "SELECT * FROM phones WHERE is_deleted = true",
            countQuery = "SELECT COUNT(*) FROM phones WHERE is_deleted = true",
            nativeQuery = true
    )
    Page<Phone> findAllDeletedPhones(Pageable pageable);

}
