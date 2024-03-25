package io.github.stackpan.qurancord.configuration;

import com.freya02.botcommands.api.builder.ExtensionsBuilder;
import io.github.stackpan.qurancord.service.AyahService;
import io.github.stackpan.qurancord.service.StorageService;
import io.github.stackpan.qurancord.service.SurahService;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Setter
@Component
public class ExtensionRegister implements Consumer<ExtensionsBuilder>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void accept(ExtensionsBuilder extensionsBuilder) {
        extensionsBuilder
                .registerConstructorParameter(SurahService.class, t -> applicationContext.getBean(SurahService.class))
                .registerConstructorParameter(AyahService.class, t -> applicationContext.getBean(AyahService.class))
                .registerConstructorParameter(StorageService.class, t -> applicationContext.getBean(StorageService.class));
    }
}
