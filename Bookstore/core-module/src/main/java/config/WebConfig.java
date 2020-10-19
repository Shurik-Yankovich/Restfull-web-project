package config;

//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
//import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.view.JstlView;
//import org.springframework.web.servlet.view.UrlBasedViewResolver;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
//
//@Configuration
//@EnableWebMvc
//@ComponentScan("restcontroller")
//public class WebConfig implements WebMvcConfigurer {
////    @Bean
////    public UrlBasedViewResolver viewResolver() {
////        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
////        resolver.setPrefix("WEB-INF/pages");
////        resolver.setSuffix(".jsp");
////        resolver.setViewClass(JstlView.class);
////        return resolver;
////    }
//
//    @Bean
//    public FreeMarkerViewResolver freemarkerViewResolver() {
//        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
//        resolver.setCache(true);
//        resolver.setSuffix(".ftl");
//        return resolver;
//    }
//
//    @Bean
//    public FreeMarkerConfigurer freemarkerConfig() {
//        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
//        freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/pages/");
//        return freeMarkerConfigurer;
//    }
//
//
////    @Bean
////    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
////    webServerFactoryCustomizer() {
////        return factory -> factory.setContextPath("/Bookstore");
////    }
//
//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }
//}
