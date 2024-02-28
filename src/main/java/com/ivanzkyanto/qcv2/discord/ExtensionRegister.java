package com.ivanzkyanto.qcv2.discord;

import com.freya02.botcommands.api.builder.ExtensionsBuilder;
import com.ivanzkyanto.qcv2.service.AyahService;
import com.ivanzkyanto.qcv2.service.SurahService;
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
                .registerConstructorParameter(AyahService.class, t -> applicationContext.getBean(AyahService.class));
    }
}
