package com.stayrascal.api.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ForceDependsOnPostProcessor implements BeanFactoryPostProcessor {
    private final Class<?> beanClass;
    private final String[] dependsOn;

    public ForceDependsOnPostProcessor(Class<?> beanClass, String[] dependsOn) {
        this.beanClass = beanClass;
        this.dependsOn = dependsOn;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanName : getBeanNames(beanFactory)) {
            BeanDefinition definition = getBeanDefinition(beanName, beanFactory);
            String[] dependencies = definition.getDependsOn();
            for (String bean : this.dependsOn) {
                dependencies = StringUtils.addStringToArray(dependencies, bean);
            }
            definition.setDependsOn(dependencies);
        }
    }

    private Iterable<? extends String> getBeanNames(ListableBeanFactory beanFactory) {
        Set<String> names = new HashSet<>();
        names.addAll(Arrays.asList(BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, this.beanClass, true, false)));
        return names;
    }

    private static BeanDefinition getBeanDefinition(String beanName, ConfigurableListableBeanFactory beanFactory) {
        try {
            return beanFactory.getBeanDefinition(beanName);
        } catch (NoSuchBeanDefinitionException ex) {
            BeanFactory parent = beanFactory.getParentBeanFactory();
            if (parent instanceof ConfigurableListableBeanFactory) {
                return getBeanDefinition(beanName, (ConfigurableListableBeanFactory) parent);
            }
            throw ex;
        }
    }
}
