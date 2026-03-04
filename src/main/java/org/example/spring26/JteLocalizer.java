package org.example.spring26;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class JteLocalizer implements gg.jte.support.LocalizationSupport {

    private final MessageSource messageSource;
    private final Locale locale;

    public JteLocalizer(MessageSource messageSource, Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public String lookup(String key) {
        return messageSource.getMessage(key, null,"translation not found", locale);
    }
}
