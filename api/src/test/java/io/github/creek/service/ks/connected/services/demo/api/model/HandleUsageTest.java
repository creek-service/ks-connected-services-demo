/*
 * Copyright 2021-2025 Creek Contributors (https://github.com/creek-service)
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

package io.github.creek.service.ks.connected.services.demo.api.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class HandleUsageTest {

    @Test
    void shouldCreateValidInstance() {
        // When:
        final HandleUsage usage = new HandleUsage("@handle", 1);

        // Then:
        assertThat(usage.handle(), is("@handle"));
        assertThat(usage.count(), is(1));
    }

    @Test
    void shouldThrowOnNullHandle() {
        // When:
        final Exception e =
                assertThrows(NullPointerException.class, () -> new HandleUsage(null, 1));

        // Then:
        assertThat(e.getMessage(), is("handle"));
    }

    @Test
    void shouldThrowOnEmptyHandle() {
        // When:
        final Exception e =
                assertThrows(IllegalArgumentException.class, () -> new HandleUsage("", 1));

        // Then:
        assertThat(e.getMessage(), is("handle cannot be empty"));
    }

    @Test
    void shouldThrowOnNonPositiveCount() {
        // When:
        final Exception e =
                assertThrows(IllegalArgumentException.class, () -> new HandleUsage("@handle", 0));

        // Then:
        assertThat(e.getMessage(), is("count must be greater than zero"));
    }
}
