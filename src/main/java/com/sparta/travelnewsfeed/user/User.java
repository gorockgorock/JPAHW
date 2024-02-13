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



    public User(String username, String email, String password, String phone_number) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;

    }
    public void update (UserRequestDto userRequestDto){
        this.username = userRequestDto.getUsername();
        this.email = userRequestDto.getEmail();
        this.phone_number = userRequestDto.getPhone_number();


    }

}


