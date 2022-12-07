package com.deploy.pipeline

import com.lesfurets.jenkins.unit.BasePipelineTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * SimpleTests
 *
 * @author YeLuo
 * @since 2022/11/20
 * */
class SimpleTests extends BasePipelineTest {

    @Override
    @BeforeEach
    void setUp() {
        super.baseScriptRoot = 'JenkinsSharedLibrary'
        super.scriptRoots += 'vars'
        super.setUp()

        helper.registerAllowedMethod('pipeline', [Closure.class], null)
        helper.registerAllowedMethod('agent', [Closure.class], null)
        helper.registerAllowedMethod('stages', [Closure.class], null)
        helper.registerAllowedMethod('steps', [Closure.class], null)
        binding.setVariable('none', {})
        binding.setVariable('any', {})
    }

    @Test
    void simpleTest() {
        def script = super.loadScript('simple.groovy')
        script.call()
        super.printCallStack()
        super.assertJobStatusSuccess()
    }

}
