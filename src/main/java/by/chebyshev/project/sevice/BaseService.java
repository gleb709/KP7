package by.chebyshev.project.sevice;

import java.util.Optional;

public interface BaseService<T> {
    Optional<T> findById(Long id);
}
