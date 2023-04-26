package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Drama {

    private final String providerName;
    private final String name;
    private final String imageUrl;
    private final String videoUrl;

    @Override
    public String toString() {
        return "Drama{" +
                "providerName='" + providerName + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }
}
