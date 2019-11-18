package com.cloudsgen.system.core.authentication;

import java.lang.annotation.*;


@Documented
@Target({ElementType.TYPE, ElementType.METHOD })
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Iauthentication {
            Class AuthModel();

}
