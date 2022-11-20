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

    private static final def ERROR_MSG = 'assert error'

    @Test
    void isBooleanTest() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isTrue(Boolean.FALSE, ERROR_MSG)),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isFalse(Boolean.TRUE, ERROR_MSG)),
        )
    }

    @Test
    void isEmptyTest() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isEmpty('1111', ERROR_MSG)),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isEmpty(Arrays.asList(1, 2, 3), ERROR_MSG)),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isEmpty(Maps.newHashMap('key', 'value'), ERROR_MSG)),
        )
    }

    @Test
    void isNotEmptyTest() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isNotEmpty(null, ERROR_MSG)),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isNotEmpty('', ERROR_MSG)),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isNotEmpty(new ArrayList(), ERROR_MSG)),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Assert.isNotEmpty(new HashMap(), ERROR_MSG)),
        )
    }
}
