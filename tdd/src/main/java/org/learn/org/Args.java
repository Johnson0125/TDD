package org.learn.org;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Args<T> {

    public static <T> T parse(Class<T> optionClass, String... args) {

        try {
            List<String> arguments = Arrays.asList(args);
            Constructor<?> constructor = optionClass.getDeclaredConstructors()[0];
            Parameter[] parameters = constructor.getParameters();

            Object[] values = Arrays.stream(parameters).map(e -> parseOption(arguments, e)).toArray();

            return (T) constructor.newInstance(values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Object parseOption(List<String> arguments, Parameter parameter) {
        Object value = null;
        Option option = parameter.getAnnotation(Option.class);

        if (parameter.getType() == boolean.class) {
            value = parseBoolean(arguments, option);
        }
        if (parameter.getType() == int.class) {
            value = parseInt(arguments, option);
        }
        if (parameter.getType() == String.class) {
            value = parseString(arguments, option);
        }

        return value;
    }

    private static Object parseString(List<String> arguments, Option option) {
        Object value;
        int index = arguments.indexOf("-" + option.value());
        value = arguments.get(index + 1);
        return value;
    }

    private static Object parseInt(List<String> arguments, Option option) {
        Object value;
        int index = arguments.indexOf("-" + option.value());
        value = Integer.parseInt(arguments.get(index + 1));
        return value;
    }

    private static Object parseBoolean(List<String> arguments, Option option) {
        Object value;
        value = arguments.contains("-" + option.value());
        return value;
    }

}
