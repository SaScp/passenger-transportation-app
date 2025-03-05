package org.service.passangertransportationgraphjpaadapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Graph {

    private List<Map<String, String>> nodes;

    private  List<Map<String, String>> edges;



}
