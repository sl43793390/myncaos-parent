package com.mynacos.config;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.CollectionUtils;

import com.mynacos.annotation.MyNacosService;

/**
 * Bean 注册,通过BeanFactoryAware，进行手动注册bean到spring容器
 *
 */
//@Configuration
public class BeanFactorySupport implements BeanFactoryAware {
	
	
	private static final Logger log = LoggerFactory.getLogger(BeanFactorySupport.class);

    private static BeanFactory beanFactory;
    /**
     * 收集扫描到的类对象注入到 Spring 中时的命名
     */
    private static final Set<String> BEAN_NAMES = new HashSet<>(16);

    /**
     * 指定包名，多个包名时用英文逗号隔开
     */
//    @Value("${business.scan.path}")
    private String path;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        BeanFactorySupport.beanFactory = beanFactory;
    }
    public static BeanFactory getBeanFactory() {
        return BeanFactorySupport.beanFactory;
    }

    public static Set<String> getBeanNames() {
        return BEAN_NAMES;
    }

    /**
     * 将配置中的包名切分，
     *
     * @throws ClassNotFoundException 可能找不到类
     */
    @PostConstruct
    public void beforeInit() throws ClassNotFoundException {
        Objects.requireNonNull(path);
        String[] split = path.split(",");

        for (String packagePath : split) {
            if (!"".equals(packagePath)) {
                register(packagePath);
            }
        }
    }

    /**
     * 注册指定包路径下的包含 Business 注解的类到 Spring 容器内
     *
     * @param path 包路径
     * @throws ClassNotFoundException 可能找不到类
     */
    public static void register(String path) throws ClassNotFoundException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) getBeanFactory();
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        // 扫描带有自定义注解的类
        provider.addIncludeFilter(new AnnotationTypeFilter(MyNacosService.class));
        Set<BeanDefinition> scanList = provider.findCandidateComponents(path);
        if (CollectionUtils.isEmpty(scanList)) {
            log.error("未扫描到 Bean 资源....");
            return;
        }

        // 注册Bean
        for (BeanDefinition beanDefinition : scanList) {
        	MyNacosService business = Class.forName(beanDefinition.getBeanClassName()).getAnnotation(MyNacosService.class);
//            String prefix = business.prefix();
//            String beanName = String.join("-", prefix, beanDefinition.getBeanClassName(), business.business());
//            log.info("注册：{}", beanName);
//            BEAN_NAMES.add(beanName);
//            beanFactory.registerBeanDefinition(beanName, beanDefinition);
        }
    }

}
