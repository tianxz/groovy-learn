package groovyMate

/**
 * Created by XizeTian on 2016/11/21.
 */
class AnInterceptable implements GroovyInterceptable {
    def existingMethod() {}

    def invokeMethod(String name, args) {
        'intercepted'
    }
}
