package net.javafaker.junit.extension.handlers;

import net.javafaker.junit.api.annotations.*;
import net.javafaker.junit.extension.exceptions.AnnotationMismatchException;

import java.lang.annotation.Annotation;
import java.util.List;

@SuppressWarnings("unused")
class FakeBaseAnnotationHandler extends AbstractAnnotationBasedHandler {
    @Override
    public boolean isDataTypeSupported(Class<?> type) {
        return String.class.equals(type)
                || char[].class.equals(type);
    }

    @Override
    public List<Class<? extends Annotation>> getSupportedAnnotations() {
        return List.of(FakeLetterify.class,
                FakeNumerify.class,
                FakeBothify.class,
                FakeRegexify.class,
                FakeExamplify.class,
                FakeTemplatify.class,
                FakeChars.class);
    }

    @Override
    public Object handleInternal(Class<?> type, Annotation annotation) {
        if (annotation instanceof FakeLetterify fakeLetterify)
            return getFaker().letterify(fakeLetterify.value(), fakeLetterify.upper());
        else if (annotation instanceof FakeNumerify fakeNumerify)
            return getFaker().numerify(fakeNumerify.value());
        else if (annotation instanceof FakeBothify fakeBothify)
            return getFaker().bothify(fakeBothify.value(), fakeBothify.upper());
        else if (annotation instanceof FakeRegexify fakeRegexify)
            return getFaker().regexify(fakeRegexify.value());
        else if (annotation instanceof FakeTemplatify fakeTemplatify)
            return getFaker().templatify(fakeTemplatify.value(), fakeTemplatify.placeholder(), fakeTemplatify.options());
        else if (annotation instanceof FakeExamplify fakeExamplify)
            return getFaker().examplify(fakeExamplify.value());
        else if (annotation instanceof FakeChars fakeChars)
            return getFaker().lorem().characters(fakeChars.length()).toCharArray();
        else
            throw new AnnotationMismatchException(getSupportedAnnotations(), annotation.annotationType());
    }
}
