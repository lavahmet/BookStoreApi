package com.example.bookapprestapi.controllers;

import com.example.bookapprestapi.RequestDTO.RequestReaderDTO;
import com.example.bookapprestapi.ResponseDTO.ResponseBookDTO;
import com.example.bookapprestapi.ResponseDTO.ResponseReaderDTO;
import com.example.bookapprestapi.models.Favorites;
import com.example.bookapprestapi.models.Reader;
import com.example.bookapprestapi.services.FavoritesService;
import com.example.bookapprestapi.services.ReaderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class ReaderController {

    private final ReaderService readerService;
    private final FavoritesService favoritesService;
    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseReaderDTO findById(@PathVariable int id) {
        return convertModelToDTO(readerService.findById(id));
    }

    @PatchMapping("/edit/{id}")
    public ResponseReaderDTO editReader(@PathVariable int id, @RequestBody RequestReaderDTO readerDTO) {
        return convertModelToDTO(readerService.editUser(id, convertDTOtoModel(readerDTO)));
    }

    private ResponseReaderDTO convertModelToDTO(Reader reader) {
        ResponseReaderDTO readerDTO = modelMapper.map(reader, ResponseReaderDTO.class);
        readerDTO.setBookDTOList(favoritesService.findAll(reader.getReaderId())
                .stream()
                .map(this::convertFavoriteModelToBookDto)
                .collect(Collectors.toList()));
        return readerDTO;
    }

    private Reader convertDTOtoModel(RequestReaderDTO readerDTO) {
        return modelMapper.map(readerDTO, Reader.class);
    }

    private ResponseBookDTO convertFavoriteModelToBookDto(Favorites favorites) {
        return modelMapper.map(favorites.getBook(), ResponseBookDTO.class);
    }
}
