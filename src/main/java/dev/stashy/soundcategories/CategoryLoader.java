package dev.stashy.soundcategories;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface CategoryLoader
{
    /**
     * Registers a SoundCategory and injects its reference to the field that has this annotation.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Register
    {
        /**
         * The ID of the sound category - if omitted, will be automatically set from the field name.
         */
        String id() default "";
    }
}
