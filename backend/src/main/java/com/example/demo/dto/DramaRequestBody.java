package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DramaRequestBody {

    private String provider;
    private String name;

    @Override
    public String toString() {
        return "DramaRequestBody{" +
                "provider='" + provider + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
