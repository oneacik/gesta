package com.ksidelta.library.gesta.processor;

import java.util.Optional;

/**
 * TODO: Document this class / interface here
 *
 * @since v7.4
 */
public interface Processor <T> {
    public Optional<T> process(T t);
}
