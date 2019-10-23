package me.noayo.discordbot.command;


import java.lang.annotation.ElementType;

import java.lang.annotation.Retention;

import java.lang.annotation.RetentionPolicy;

import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {


    String name();

    String description() default "Without description";

    ExecutorType type() default ExecutorType.ALL;

    enum ExecutorType {
        ALL, USER, CONSOLE
    }

    public class CommandMap {
        private static String tag;

        public static String getTag() {
            return tag;
        }
    }

    public class GetTag {
    }
}