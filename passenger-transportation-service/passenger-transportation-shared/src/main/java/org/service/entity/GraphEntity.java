package org.service.entity;

import java.util.Map;
import java.util.Set;

public record GraphEntity(Set<Map<String, String>> nodes, Set<Map<String, String>> edges) {
}
