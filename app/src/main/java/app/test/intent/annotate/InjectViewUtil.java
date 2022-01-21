package app.test.intent.annotate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 注解view，注册工具
 */
class InjectViewUtil {

    // 注册activity中， 持有 InjectView 注解的属性
    public static void injectViews(Activity activity) {

        if (activity == null || activity.isFinishing()) {
            return;
        }

        final Field[] fields = activity.getClass().getDeclaredFields();

        for (Field field :
                fields) {
            if (!field.isAnnotationPresent(InjectView.class)) {
                continue;
            }
            final InjectView annotation = field.getAnnotation(InjectView.class);
            final int viewId = annotation.value();
            try {
                final Method findViewById = activity.getClass().getMethod("findViewById", int.class);
                findViewById.setAccessible(true);
                final Object view = findViewById.invoke(activity, viewId);
                field.setAccessible(true);
                field.set(activity, view);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        }

    }

    public static void injectIntent(Activity activity) {

        final Intent intent = activity.getIntent();
        if (intent == null) {
            return;
        }
        final Bundle extras = intent.getExtras();
        if (extras == null) {
            return;
        }

        // 2 获取所有的属性
        // getDeclaredFields  和 getFields 的区别。
        final Field[] fields = activity.getClass().getDeclaredFields();
        // 3 遍历属性，拿到被目标注解标注的属性
        for (Field field :
                fields) {
            if (field.isAnnotationPresent(InjectIntent.class)) {

                final InjectIntent annotation = field.getAnnotation(InjectIntent.class);
                if (annotation == null) {
                    continue;
                }
                // 4 获取 extra map 中，携带的 key值。若指定了，则取value，否则取filed 名称
                String extraKey = TextUtils.isEmpty(annotation.value()) ? field.getName() : annotation.value();

                if (!extras.containsKey(extraKey)) {
                    continue;
                }
                Object extraValue = extras.get(extraKey);

                // 获取数组中，元素的类型
                final Class<?> componentType = field.getType().getComponentType();
                // 判断 属性是否为数组类型，且数组元素是 parcelable 类型的。
                // 如果是，则需要转换， 只有 parcelable 类型的数据，才需要这样处理。其他类型的数组都不需要这么处理。
                if (field.getType().isArray() && Parcelable.class.isAssignableFrom(componentType)) {

                    Object[] objects = (Object[]) extraValue;
                    Object[] objs = Arrays.copyOf(objects, objects.length, (Class<? extends Object[]>) field.getType());
                    extraValue = objs;
                }

                // 如果不是数组，直接设值。 否则，判断是否是数组，制定数组的值。
                field.setAccessible(true);
                try {
                    field.set(activity, extraValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
