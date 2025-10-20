package com.ethsoft.mydatn.service;

import java.util.List;

public interface BaseService<T, ID> {
    List<T> getAll();
    T getById(ID id);
    T create(T dto);
    T update(ID id, T dto);
    void delete(ID id);
}
