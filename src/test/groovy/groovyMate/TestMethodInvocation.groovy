package groovyMate

/**
 * Created by XizeTian on 2016/11/21.
 */
class TestMethodInvocation extends GroovyTestCase {
    /**
     * 拦截toString
     */
    void testInterceptedMethodCallPOJO() {
        def val = new Integer(3)
        Integer.metaClass.toString = { -> 'intercepted' }

        assertEquals "intercepted", val.toString()
    }

    /**
     * 实现GroovyInterceptable接口的类会被调用invokeMethod()
     */
    void testInterceptableCalled() {
        def obj = new AnInterceptable()

        assertEquals('intercepted', obj.existingMethod())
    }

    /**
     * 通过metaClass改写[Groovy class] existingMethod2 返回值
     * 原值 = existingMethod2
     * 修改后的值 = intercepted
     */
    void testInterceptedExistingMethodCalled() {
        AGroovyObject.metaClass.existingMethod2 = { -> 'intercepted' }
        def obj = new AGroovyObject()
        assertEquals('intercepted', obj.existingMethod2())
    }

    /**
     * 通过metaClass改写[Java class] existingMethod2 返回值
     */
    void testInterceptedExistingMethodCalledOnJava() {
        def obj = new AGroovyObject()
        obj.metaClass.existingMethod2 = { -> 'intercepted' }
        assertEquals('intercepted', obj.existingMethod2())
    }

    void testUnInterceptedExistingMethodCalled() {
        def obj = new AGroovyObject()
        assertEquals('existingMethod', obj.existingMethod())
    }

    /**
     * 获取闭包返回值
     */
    void testPropertyThatIsClosureCalled() {
        def obj = new AGroovyObject()
        assertEquals('closure called', obj.closureProp())
    }

    /**
     * 测试调用对象的目标方法不存在时返回字符串missing called
     */
    void testMethodMissingCalledOnlyForNonExistent() {
        def obj = new ClassWithInvokeAndMissingMethod()
        assertEquals('existingMethod', obj.existingMethod())
        assertEquals('missing called', obj.nonExistingMethod())
    }

    /**
     * 调用对象不存在的方法 shouldFail用于捕获异常
     */
    void testMethodFailsOnNonExistent() {
        def obj = new TestMethodInvocation()
        shouldFail(MissingMethodException) {
            obj.nonExistingMethod();
        }
    }
}