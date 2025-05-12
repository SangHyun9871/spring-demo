package com.cmsoft.core.annotation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * ApiMethod 어노테이션의 중복 체크를 수행하는 클래스 (체크 시점은 컴파일 시점)
 */
@SupportedAnnotationTypes("com.cmsoft.core.annotation.ApiMethod")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ApiMethodUniqueChecker extends AbstractProcessor {
    
    private final Map<String, Element> methodMap = new HashMap<>();
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(ApiMethod.class)) {
            if (element instanceof ExecutableElement) {
                ApiMethod annotation = element.getAnnotation(ApiMethod.class);
                String methodCode = annotation.value();
                
                if (methodMap.containsKey(methodCode)) {
                    processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.ERROR,
                        "Duplicate method code: " + methodCode + 
                        "\nFirst occurrence: " + methodMap.get(methodCode).getSimpleName() +
                        "\nSecond occurrence: " + element.getSimpleName(),
                        element
                    );
                    return false;
                }
                
                methodMap.put(methodCode, element);
            }
        }
        return true;
    }
}