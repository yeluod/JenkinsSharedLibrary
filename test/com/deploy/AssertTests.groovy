package com.deploy

import com.deploy.utils.Assert
import org.assertj.core.util.Maps
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * AssertTests
 *
 * @author YeLuo
 * @since 2022/11/19
 * */
class AssertTests {

    @Test
    void isBooleanTest() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isTrue(Boolean.FALSE, 'error')),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isFalse(Boolean.TRUE, 'error')),
        )
    }

    @Test
    void isEmptyTest() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isEmpty('1111', 'error')),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isEmpty(Arrays.asList(1, 2, 3), 'error')),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isEmpty(Maps.newHashMap('key', 'value'), 'error')),
        )
    }

    @Test
    void isNotEmptyTest() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isNotEmpty(null, 'error')),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isNotEmpty('', 'error')),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isNotEmpty(new ArrayList(), 'error')),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isNotEmpty(new HashMap(), 'error')),
        )
    }
}
