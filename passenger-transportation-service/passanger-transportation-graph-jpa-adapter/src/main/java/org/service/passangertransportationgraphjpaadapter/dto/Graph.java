package org.service.passangertransportationgraphjpaadapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Graph {

    private Set<Map<String, String>> nodes;

    private  Set<Map<String, String>> edges;



}
