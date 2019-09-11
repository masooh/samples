package com.example.spring.contexthierarchy.application;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.SetUtils;
import org.apache.commons.collections4.SetUtils.SetView;
import org.springframework.context.ApplicationContext;

public interface ContextHelper {

    ApplicationContext getApplicationContext();

    default void beanHierarchyStats() {
        ApplicationContext context = getApplicationContext();

        StringBuilder builder = new StringBuilder();

        do {
            builder.append(String
                    .format("%s(%s): %d, beans: %s, %n", context.getId(), context.getClass().getSimpleName(), context
                            .getBeanDefinitionCount(), Arrays.toString(context.getBeanDefinitionNames())));

            // compare with all parents
            ApplicationContext parent = context.getParent();
            while (parent != null) {
                SetView<String> intersection = SetUtils.intersection(beanNamesSet(context), beanNamesSet(parent));
                System.out.printf("----%n%s vs %s - both: %d%n%s%n", context.getId(), parent.getId(), intersection
                        .size(), sortedInLines(intersection));

                SetView<String> onlyContext = SetUtils.difference(beanNamesSet(context), beanNamesSet(parent));
                System.out.printf("----%n%s vs %s - onlyChild: %d%n%s%n", context.getId(), parent.getId(), onlyContext
                        .size(), sortedInLines(onlyContext));

                SetView<String> onlyParent = SetUtils.difference(beanNamesSet(parent), beanNamesSet(context));
                System.out.printf("----%n%s vs %s - onlyParent: %d%n%s%n", context.getId(), parent.getId(), onlyParent
                        .size(), sortedInLines(onlyParent));

                parent = parent.getParent();
            }

            context = context.getParent();
        } while (context != null);

        System.out.println(builder);
    }

    static String sortedInLines(SetUtils.SetView<String> onlyContext) {
        return onlyContext.stream().sorted().collect(Collectors
                .joining("\n"));
    }

    static Set<String> beanNamesSet(ApplicationContext context) {
        return new HashSet<>(Arrays.asList(context.getBeanDefinitionNames()));
    }

    default void beanCount(String bean) {
        final StringBuilder builder = new StringBuilder();
        builder.append("bean: " + bean + " -> ");
        beanCount(bean, getApplicationContext(), builder);
        System.out.println(builder);
    }

    static void beanCount(String bean, ApplicationContext applicationContext, StringBuilder builder) {
        long count = Arrays.stream(applicationContext.getBeanDefinitionNames()).filter(name -> name.equals(bean))
                .count();

        builder.append(String
                .format("%s(%s): %d", applicationContext.getId(), applicationContext.getClass().getSimpleName(), count));

        if (applicationContext.getParent() != null) {
            builder.append(", ");
            beanCount(bean, applicationContext.getParent(), builder);
        }
    }
}
