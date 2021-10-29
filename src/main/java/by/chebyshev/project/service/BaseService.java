package by.chebyshev.project.service;

import java.util.Optional;

public interface BaseService<T> {
    Optional<T> findById(Long id);
}
