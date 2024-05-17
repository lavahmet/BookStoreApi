package com.example.bookapprestapi.services;

import com.example.bookapprestapi.models.Reader;
import com.example.bookapprestapi.repositories.RoleRepository;
import com.example.bookapprestapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReaderService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public Reader findById(int id) {
        Optional<Reader> founded = userRepository.findById(id);
        return founded.orElse(null);
    }


    public Optional<Reader> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void addUser(Reader reader) {
        reader.setRoles(List.of(roleRepository.findByName("User").orElseThrow()));
        reader.setPassword(passwordEncoder.encode(reader.getPassword()));
        userRepository.save(reader);
    }

    @Transactional
    public Reader editUser(int id, Reader reader) {
        Reader readerForEdit = findById(id);
        readerForEdit.setReaderId(id);
        readerForEdit.setUsername(reader.getUsername());
        readerForEdit.setEmail(reader.getEmail());
        readerForEdit.setProfileImage(reader.getProfileImage());
        userRepository.save(readerForEdit);
        return findById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Reader reader = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        ));

        return new User(
                reader.getUsername(),
                reader.getPassword(),
                reader.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }
}
