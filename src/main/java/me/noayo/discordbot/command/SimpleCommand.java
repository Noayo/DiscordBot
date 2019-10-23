package me.noayo.discordbot.command;

import java.lang.reflect.Method;

public final class SimpleCommand {

    private final String name, description;
    private final Command.ExecutorType executorType;
    private final Object object;
    private final Method method;


    public String getName() {
        return name;
    }

    public SimpleCommand(String name, String description, Command.ExecutorType executorType, Object object, Method method) {
        this.name = name;
        this.description = description;
        this.executorType = executorType;
        this.object = object;
        this.method = method;


    }

    public Command.ExecutorType getExecutorType() {
        return executorType;
    }

    public Method getMethod() {
        return method;
    }

    public Object getObject() {
        return object;
    }

    public String getDescription() {
        return description;
    }
}

