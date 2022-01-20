package app.test.intent.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * intent 注释类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface InjectIntent {

    String value();

}
