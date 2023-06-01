package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class Drama implements Serializable {

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Drama)) return false;

        Drama d = (Drama)obj;
        return d.name.equals(this.name) && d.providerName.equals(this.providerName);

    }
}
