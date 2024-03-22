/*
 * Copyright Oleg Cherednik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.olegcherednik.json.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @param <E> Type of the value object
 * @author Oleg Cherednik
 * @since 11.03.2024
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmptyAutoCloseableIterator<E> implements AutoCloseableIterator<E> {

    private static final EmptyAutoCloseableIterator<Object> INSTANCE = new EmptyAutoCloseableIterator<>();

    public static <E> AutoCloseableIterator<E> getInstance() {
        return (AutoCloseableIterator<E>) INSTANCE;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public E next() {
        throw new NoSuchElementException();
    }

    @Override
    public void remove() {
        throw new IllegalStateException();
    }

    @Override
    public void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
    }

    @Override
    public void close() throws Exception {
        // ignore
    }
}
