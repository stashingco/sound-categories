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

        /**
         * Sets the SoundCategory of a field to be the master category.
         * All categories defined after it will become grouped within the master,
         * and their volumes will be multiplied with the master category's volume level.
         */
        boolean master() default false;
    }
}
