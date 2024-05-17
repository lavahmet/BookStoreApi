package com.example.bookapprestapi.repositories;

import com.example.bookapprestapi.models.BookPDF;
import com.example.bookapprestapi.models.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Integer> {
    ProfileImage findByReaderReaderId(int id);

}
