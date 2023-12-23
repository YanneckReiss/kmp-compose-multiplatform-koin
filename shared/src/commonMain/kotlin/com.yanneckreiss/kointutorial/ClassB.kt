package com.yanneckreiss.kointutorial

import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
class ClassB(
    @Named("ABC") private val sampleInterface: SampleInterface
) {

    fun calculateSomething(): Int = 2 * 3
}
