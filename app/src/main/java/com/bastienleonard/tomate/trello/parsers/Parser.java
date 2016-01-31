package com.bastienleonard.tomate.trello.parsers;

import java.io.InputStream;

public interface Parser<T> {
    T parse(InputStream inputStream);
}
