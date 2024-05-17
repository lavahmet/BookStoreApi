package com.example.bookapprestapi.services;

import com.example.bookapprestapi.models.ProfileImage;
import com.example.bookapprestapi.repositories.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileImageService {
    private final ProfileImageRepository imageRepository;
    private final ReaderService readerService;

    public ProfileImage findById(int id) {
        return imageRepository.findByReaderReaderId(id);
    }

    public void save(byte[] pdfContent, int bookId) {
        if (imageRepository.findByReaderReaderId(bookId) == null) {
            ProfileImage image = new ProfileImage();
            image.setReader(readerService.findById(bookId));
            image.setContent(pdfContent);
            imageRepository.save(image);
        }
    }

    public boolean checkBookId(int bookId) {
        if (imageRepository.findByReaderReaderId(bookId) == null) {
            return false;
        }
        return imageRepository.existsById(imageRepository.findByReaderReaderId(bookId).getImageId());
    }

    public void editPdf(int id, byte[] pdfContent) {
        ProfileImage existingImage = imageRepository.findByReaderReaderId(id);
        if (existingImage != null) {
            existingImage.setImageId(existingImage.getImageId());
            existingImage.setContent(pdfContent);
            imageRepository.save(existingImage);
        }
    }

    public void deleteById(int id) {
        imageRepository.deleteById(findById(id).getImageId());
    }
}
