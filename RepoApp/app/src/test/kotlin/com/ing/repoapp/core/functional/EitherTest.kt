package com.ing.repoapp.core.functional

import com.ing.repoapp.RepoAppUnitTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqualTo
import org.junit.Test

class EitherTest : RepoAppUnitTest() {

    @Test fun `Either Right should return correct type`() {

        val result = Either.Right("inginterview")

        result shouldBeInstanceOf Either::class.java
        result.isRight shouldBe true
        result.isLeft shouldBe false
        result.fold({},
                { right ->
                    right shouldBeInstanceOf String::class.java
                    right shouldEqualTo "inginterview"
                })
    }

    @Test fun `Either Left should return correct type`() {
        val result = Either.Left("inginterview")

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldBe true
        result.isRight shouldBe false
        result.fold(
                { left ->
                    left shouldBeInstanceOf String::class.java
                    left shouldEqualTo "inginterview"
                }, {})
    }

}