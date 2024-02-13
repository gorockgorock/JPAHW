package com.sparta.travelnewsfeed.user;

import com.sparta.travelnewsfeed.dto.request.UserRequestDto;
import com.sparta.travelnewsfeed.entity.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
   private String password;

    @Column(nullable = true)
    private String phone_number;

    @Column(nullable = true)
    @Enumerated(value = EnumType.STRING)
    private Category category;

    public User(String username, String email, String password, String phone_number, Category category) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.category = category;
    }
    public void update (UserRequestDto userRequestDto){
        this.username = userRequestDto.getUsername();
        this.email = userRequestDto.getEmail();
        this.phone_number = userRequestDto.getPhone_number();
        this.category = userRequestDto.getCategory();

    }

}


