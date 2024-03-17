package com.ivanzkyanto.qcv2.util;

import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;

public class LoggerString {

    public static String getLogGlobalCommand(GlobalSlashEvent event) {
        var builder = new StringBuilder("Request for \"%s\" (%s) by %s (%s)"
                .formatted(
                        event.getCommandString(),
                        event.getCommandId(),
                        event.getUser().getName(),
                        event.getUser().getId()
                ));

        if (event.getGuild() != null) {
            builder.append(" in guild \"%s\" (%s) channel \"%s\" (%s)"
                    .formatted(
                            event.getGuild().getName(),
                            event.getGuild().getId(),
                            event.getChannel().getName(),
                            event.getChannel().getId()
                    ));
        }

        return builder.toString();
    }

}
