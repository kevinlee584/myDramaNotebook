package com.example.demo.controller;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProvidersResponseTemplate {
     final private String provider;
     final private String favicon;
     final private Map<String, String> sorts;
}
